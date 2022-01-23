package CovidSim.gui;

import CovidSim.model.Heading;
import CovidSim.model.Person;
import CovidSim.model.Simulation;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CovidSimController {
	
	//GUI Elements
	
	@FXML
	Pane world;
	
	@FXML
	Button startButton;
	
	@FXML
	Button stopButton;
	
	@FXML
	Button stepButton;
	
	@FXML
	Slider sizeSlider;
	
	@FXML
	Slider speedSlider;
	
	@FXML
	Slider recoverySlider;
	
	@FXML
	Slider socialDistanceSlider;
	
	@FXML
	TextField tickField;
	
	
	Simulation sim;
	
	private Movement clock; //=  new Movement();
	
	private class Movement extends AnimationTimer {
		
		private long FPS = 60L;
		private long INTERVAL = 1000000L/FPS;
		private long last = 0;
		private int ticks = 0;
		
		@Override
		public void handle(long now) {
			if(now - last > INTERVAL) {
				step();
				last = now;
				tick();
			}
			
		}
		
		public int getTicks() {
			return ticks;
		}
		
		public void resetTicks() {
			ticks = 0;
		}
		
		public void tick() {
			ticks++;
		}
	}
	
	@FXML
	public void initialize() {
		
		//Setup Canvas and Draw
		System.out.println("Init");
		world.resize(973, 561);
		world.getChildren().clear();
		sim =  new Simulation(world, 700);
		tickField.setText("" + 0);
		
		
		//Read and Update values from Sliders
		sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setSize();
				sim.draw();
			}
			
		});
		
		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setSpeed();
				System.out.println(Person.speed);
			}
			
		});
		
		recoverySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setRecovery();
			}
			
		});
		
		socialDistanceSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setSocialDistanceFactor();
			}
			
		});
		
		//Start Clock and setup Canvas
		clock = new Movement();
		world.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	//Slider parameter functions
	public void setSize() {
		Person.radius = (int) sizeSlider.getValue();
	}
	
	public void setRecovery() {
		Person.recoveryTime = 60 * (int) recoverySlider.getValue();
	}
	
	public void setSpeed() {
		Person.speed = speedSlider.getValue();
	}
	
	public void setSocialDistanceFactor() {
		Person.socialDistanceFactor = (int) socialDistanceSlider.getValue();
	}
	
	
	//Button Functions
	@FXML
	public void reset() {
		stop();
		clock.resetTicks();
		tickField.setText("" + clock.getTicks());
		world.getChildren().clear();
		sim =  new Simulation(world, 700);
	}
	
	@FXML 
	public void start() {
		clock.start();
		disableButtons(true, false, true);
	}
	
	@FXML 
	public void stop() {
		clock.stop();
		disableButtons(false, true, false);
	}
	
	@FXML
	public void step() {
		sim.move();
		sim.recover(world);
		sim.checkCollisions();
		sim.draw();
		clock.tick();
		tickField.setText("" + clock.getTicks());
	}
	
	public void disableButtons(boolean start, boolean stop, boolean step) {
		startButton.setDisable(start);
		stopButton.setDisable(stop);
		stepButton.setDisable(step);
	}

	
}
