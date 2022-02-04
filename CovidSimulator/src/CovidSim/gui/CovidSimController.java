package CovidSim.gui;

import CovidSim.model.Heading;
import CovidSim.model.Person;
import CovidSim.model.Simulation;
import Virus.Covid;
import Virus.Flu;
import Virus.Multiple;
import Virus.Recovered;
import Virus.Susceptible;
import Virus.Virus;
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

public class CovidSimController{
	
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
	Slider covidSizeSlider;
	
	@FXML
	Slider fluSizeSlider;
	
	@FXML
	Slider speedSlider;
	
	@FXML
	Slider covidRecoverySlider;
	
	@FXML
	Slider fluRecoverySlider;
	
	@FXML
	Slider socialDistanceSlider;
	
	@FXML
	Slider covidRadiusSlider;
	
	@FXML 
	Slider fluRadiusSlider;
	
	@FXML
	Slider populationSlider;
	
	@FXML
	TextField tickField;
	
	int popSize = 500;
	
	public int getTest() {
		return (int) covidSizeSlider.getValue();
	}
	
	
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
		world.resize(935, 483);
		world.getChildren().clear();
		setCovidSize();
		setFluSize();
		setCovidInfectionRadius();
		setFluInfectionRadius();
		setSpeed();
		setCovidRecovery();
		setFluRecovery();
		setSocialDistanceFactor();
		setPopulationSize();
		sim =  new Simulation(world, popSize);
		sim.draw();
		tickField.setText("" + 0);
		
		
		//Read and Update values from Sliders
		
		//Common Parameters
		populationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setPopulationSize();
				sim.draw();
			}
			
		});
		
		socialDistanceSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setSocialDistanceFactor();
			}
			
		});
		
		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setSpeed();
				System.out.println(Person.speed);
				sim.draw();
			}
			
		});
		
		//Virus Parameters
		covidSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setCovidSize();
				sim.draw();
			}
			
		});
		
		fluSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setFluSize();
				sim.draw();
			}
			
		});
		
		
		covidRecoverySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setCovidRecovery();
			}
			
		});
		
		fluRecoverySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setFluRecovery();
			}
			
		});
		
		covidRadiusSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setCovidInfectionRadius();
				sim.draw();
			}
			
		});
		
		fluRadiusSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setFluInfectionRadius();
				sim.draw();
			}
			
		});
		
		//Start Clock and setup Canvas
		clock = new Movement();
		world.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	//Slider parameter functions
	public void setCovidSize() {
		Covid.size = (int) covidSizeSlider.getValue();
	}
	
	public void setFluSize() {
		Flu.size = (int) fluSizeSlider.getValue();
	}
	
	public void setCovidRecovery() {
		Covid.recoveryDuration = 60 * (int) covidRecoverySlider.getValue();
	}
	
	public void setFluRecovery() {
		Flu.recoveryDuration = 60 * (int) fluRecoverySlider.getValue();
	}
	
	public void setSpeed() {
		Heading.speed = speedSlider.getValue();
	}
	
	public void setSocialDistanceFactor() {
		Person.socialDistanceFactor = (int) socialDistanceSlider.getValue();
	}
	
	public void setCovidInfectionRadius() {
		Covid.infectionRadius = (int) covidRadiusSlider.getValue() + Person.virusSize;
	}
	
	public void setFluInfectionRadius() {
		Flu.infectionRadius = (int) fluRadiusSlider.getValue() + Person.virusSize;
	}
	
	
	public void setPopulationSize() {
		popSize = (int) populationSlider.getValue();
	}
	
	//Button Functions
	@FXML
	public void reset() {
		stop();
		clock.resetTicks();
		tickField.setText("" + clock.getTicks());
		world.getChildren().clear();
		setCovidSize();
		setFluSize();
		setSpeed();
		setCovidRecovery();
		setSocialDistanceFactor();
		setPopulationSize();
		resetCount();
		sim =  new Simulation(world, popSize);
		sim.draw();
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
	
	public void resetCount() {
		Person.totalPop = 0;
		Susceptible.totalSusCount = 0;
		Covid.totalCovidCount = 0;
		Flu.totalFluCount = 0;
		Multiple.totalMultipleCount = 0;
		Recovered.totalRecoveredCount = 0;
	}
	
		
}
