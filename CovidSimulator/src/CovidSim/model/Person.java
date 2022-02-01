package CovidSim.model;

import CovidSim.gui.CovidSimController;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Person implements VirusInterface{
	
	//Person Class unique parameters
	public static int radius = 5;
	public static int infectionZone;
	public int recoveryTime = 10000;
	public static double speed = 1;
	
	public static int socialDistanceFactor = 10;
	
	// Virus Interface
	public static int virusSize = 5;
	private String virusType = "covid";
	private int virusInfectionRadius = 10;
	private Color virusColor = Color.PURPLE;
	
//	private State state;
	private Heading heading;
	private Position pos;
	private Position origin;
	private Circle cPerson, cZone;
	private Pane world;
	private int infectedTime = 0;
	CovidSimController controller;
	
	//Person Constructor
	public Person(Pane world, String virusType) {
//		this.state = state;
		//Initialize Person Object
		this.virusType = virusType;
		this.world = world;
		this.heading =  new Heading();
		this.pos = new Position(world);
		this.origin = new Position(pos.getX(), pos.getY());
		this.cPerson = new Circle(getVirusSize(), getVirusColor());
		this.cZone =  new Circle(getVirusInfectionRadius());
		
		cPerson.setStroke(Color.BLACK);
//		cZone.setStroke(Color.PURPLE);
		cZone.setFill(new Color(0,0,0,0));
		
		//Set Person Position on World
		this.cPerson.setTranslateX(pos.getX());
		this.cPerson.setTranslateY(pos.getY());
		this.cZone.setTranslateX(pos.getX());
		this.cZone.setTranslateY(pos.getY());
		
		//Add Person Objects into world
		world.getChildren().add(cPerson);
		world.getChildren().add(cZone);
	}
		
		//State
//		public State getState() {
//			return state;
//		}
//		
//		public void setState(State state) {
//			this.state = state;
//			cPerson.setFill(state.getColor());
//		}
	
		
		public void move() {
			pos.move(heading, world, speed, origin);
		}
		
		public void setRecovery(int r) {
			this.recoveryTime = r;
		}
		
		//Draw Person [Circle]
		public void draw() {
			//Update size, color
			cPerson.setRadius(getVirusSize());
			cPerson.setFill(getVirusColor());
			cPerson.setTranslateX(pos.getX());
			cPerson.setTranslateY(pos.getY());
		}
		
		//Draw Infection Zone [Ring]
		public void drawZone(Person p) {
				
				//Use state to change Person's characteristics based on user parameters
//				switch(p.getState()) {
//				case INFECTED_BOTH:
//					break;
//				case INFECTED_COVID:
//					cPerson.setRadius(sim.covidSize);
//					cZone.setRadius(sim.covidInfectionRadius);
//					setRecovery(sim.covidRecovery);
//					break;
//				case INFECTED_FLU:
//					cPerson.setRadius(sim.fluSize);
//					cZone.setRadius(sim.fluInfectionRadius);
//					setRecovery(sim.fluRecovery);
//					break;
//				case RECOVERED:
//					break;
//				case SUCEPTIBLE:
//					break;
//				default:
//					cZone.setRadius(10);
//					break;
			
//				cZone.setRadius(5);
				cZone.setTranslateX(pos.getX());
				cZone.setTranslateY(pos.getY());
				
				//Make a circle without color for ring effect
				cZone.setStroke(Color.PURPLE);
				cZone.setFill(new Color(0,0,0,0));
				
//				if(p.getState() == State.INFECTED_COVID || state == State.INFECTED_FLU || state == State.INFECTED_BOTH)
//					cZone.setVisible(true);
//				else
//					cZone.setVisible(false);
				
				if(p.getVirusType().equals("covid") || p.getVirusType().equals("flu") || p.getVirusType().equals("multiple"))
					cZone.setVisible(true);
				else
					cZone.setVisible(false);
			}
		
		
		//Check if Person is within the infection zone and update state
		public void spread(Person other) {
			
			///INFECTED_COVID and SUCEPTIBLE 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.getVirusType().equals("covid") && virusType.equals("sus")) {
					setVirusType("covid");

					}
				}
			
			///INFECTED_FLU and SUCEPTIBLE 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.getVirusType().equals("flu") && getVirusType().equals("sus")) {
					setVirusType("flu");
					}
				}
//			
			///INFECTED_FLU and INFECTED_COVID 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.getVirusType().equals("flu") && getVirusType().equals("covid")) {
					setVirusType("multiple");
					}
				}
			}	
		
		public void checkRecovery(Pane world, Person p) {
			if(getVirusType().equals("covid") || getVirusType().equals("flu")  || getVirusType().equals("multiple")) {
				infectedTime++;
				if(infectedTime > recoveryTime) {
					setVirusType("recovered");
				}
			}
	}
		
		public void setVirusSize(int s) {
			Person.virusSize = s;
		}
		
		//Virus Interface Methods
		@Override
		public String getVirusType() {
			return this.virusType;
		}

		@Override
		public void setVirusType(String t) {
			this.virusType = t;
		}

		@Override
		public Color getVirusColor() {
			
			switch(virusType) {
			case "covid":
				virusColor = Color.RED;
				break;
			case "flu":
				virusColor = Color.YELLOW;
				break;
			case "multiple":
				virusColor = Color.ORANGE;
				break;
			case "sus":
				virusColor = Color.LIGHTBLUE;
				break;
			case "recovered":
				virusColor = Color.GREEN;
				break;
			default:
				virusColor = Color.PURPLE;
			}
			
			return this.virusColor;
		}

		@Override
		public int getVirusInfectionRadius() {
			return this.virusInfectionRadius;
		}

		@Override
		public int getVirusSize() {
			
			switch(virusType) {
			case "covid":
				virusSize = 7;
				break;
			case "flu":
				virusSize = 5;
				break;
			case "multiple":
				virusSize = 10;
				break;
			case "sus":
				virusSize = 3;
				break;
			case "recovered":
				virusSize = 3;
				break;
			default:
				virusSize = 3;
			}
			
			return Person.virusSize;
		}		
		
		public int getTest() {
			return CovidSimController.covidSize;
		}
	
}
