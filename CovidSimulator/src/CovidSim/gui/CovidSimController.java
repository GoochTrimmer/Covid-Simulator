package CovidSim.gui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import CovidSim.model.Heading;
import CovidSim.model.Person;
import CovidSim.model.Simulation;
import Virus.Covid;
import Virus.Flu;
import Virus.Multiple;
import Virus.Recovered;
import Virus.Susceptible;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
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
	
	//Charts 
	@FXML
	LineChart<Number, Number> popLineChart;
	
	@FXML
	CategoryAxis x;
	
	@FXML
	NumberAxis y;
	
	@FXML 
	PieChart popPieChart;
	
	int popSize = 500;
	ScheduledExecutorService scheduledExecutorService;	
	Simulation sim;
	
	//Line Chart - Series Data 
	XYChart.Series<Number, Number> seriesSus = new XYChart.Series<>();
	XYChart.Series<Number, Number> seriesRecovered = new XYChart.Series<>();
	XYChart.Series<Number, Number> seriesCovid = new XYChart.Series<>();
	XYChart.Series<Number, Number> seriesFlu = new XYChart.Series<>();
	XYChart.Series<Number, Number> seriesMultiple = new XYChart.Series<>();    
    
	//Pie Chart
	public ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
            new PieChart.Data("Susceptible", Susceptible.totalSusCount),
            new PieChart.Data("Covid", Covid.totalCovidCount),
            new PieChart.Data("Flu", Flu.totalFluCount),
            new PieChart.Data("Multiple", Multiple.totalMultipleCount),
            new PieChart.Data("Reovered", Recovered.totalRecoveredCount));

	

	
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

	
	@SuppressWarnings("unchecked")
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
		
		//Initialize Pie Chart
		updatePopPieChart();
		popPieChart.setData(pieChartData);
		popPieChart.setLegendVisible(false);
		
		//Update Line Chart Data
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	    scheduledExecutorService.scheduleAtFixedRate(() -> {
	    	
	    seriesSus.setName("Susceptible");	
	    seriesRecovered.setName("Recovered");
	    seriesCovid.setName("Covid");
	    seriesFlu.setName("Flu");
	    seriesMultiple.setName("Multiple");
	    
	         //Update Series Data in new Thread
	         Platform.runLater(() -> {
	             seriesSus.getData().add(new XYChart.Data<>(clock.getTicks() / 60, Susceptible.totalSusCount));
	             seriesRecovered.getData().add(new XYChart.Data<>(clock.getTicks() / 60, Recovered.totalRecoveredCount));
	             seriesCovid.getData().add(new XYChart.Data<>(clock.getTicks() / 60, Covid.totalCovidCount));
	             seriesFlu.getData().add(new XYChart.Data<>(clock.getTicks() / 60, Flu.totalFluCount));
	             seriesMultiple.getData().add(new XYChart.Data<>(clock.getTicks() / 60, Multiple.totalMultipleCount));
	         });
	     }, 0, 1, TimeUnit.SECONDS);
		
	    popLineChart.getData().addAll(seriesSus, seriesRecovered, seriesCovid, seriesFlu, seriesMultiple);

		//Read and Update values from Sliders
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
	
	//Update Charts
	public void updatePopPieChart() {
		addData("Susceptible", Susceptible.totalSusCount);
		addData("Covid", Covid.totalCovidCount);
		addData("Flu", Flu.totalFluCount);
		addData("Multiple", Multiple.totalMultipleCount);
		addData("Recovered", Recovered.totalRecoveredCount);
	}
	
	public void addData(String name, double value)
	{
	    for(Data d : pieChartData)
	    {
	        if(d.getName().equals(name))
	        {
	            d.setPieValue(value);
	            return;
	        }
	    }
	    naiveAddData(name, value);
	}
	
	public void naiveAddData(String name, double value)
	{
	    pieChartData.add(new Data(name, value));
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
		Covid.infectionRadius = (int) covidRadiusSlider.getValue() + Person.radius;
	}
	
	public void setFluInfectionRadius() {
		Flu.infectionRadius = (int) fluRadiusSlider.getValue() + Person.radius;
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
		clearSeries();
		setCovidSize();
		setFluSize();
		setSpeed();
		setCovidRecovery();
		setSocialDistanceFactor();
		setPopulationSize();
		resetCount();
		sim =  new Simulation(world, popSize);
		sim.draw();
		updatePopPieChart();
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
		
		//Update Pie Chart
		updatePopPieChart();
		
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
	
	public void clearSeries() {
		seriesSus.getData().clear();
		seriesRecovered.getData().clear();
		seriesCovid.getData().clear();
		seriesFlu.getData().clear();
		seriesMultiple.getData().clear();
	}
		
}
