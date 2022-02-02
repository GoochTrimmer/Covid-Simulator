package CovidSim.model;

import Virus.Covid;
import Virus.Flu;
import Virus.Multiple;
import Virus.Susceptible;
import Virus.Virus;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Person {
	
	//Person Class unique parameters
	public static double speed = 1;
	
	public static int socialDistanceFactor = 10;
	
	public static int virusSize = 5;
	public static int recoveryDuration;
	private String virusType = "covid";
	public static int virusInfectionRadius = 10;
	private Color virusColor = Color.PURPLE;
	
//	private State state;
	private Heading heading;
	private Position pos;
	private Position origin;
	private Circle cPerson, cZone;
	private Pane world;
	Virus virus = new Virus();
	private int infectedTime = 0;
	
	//Person Constructor
	public Person(Pane world, String virusType, Virus virus) {
		
		//Initialize Person Object
		this.virus = virus;
		setupVirus();
		this.virusType = virusType;
		this.world = world;
		this.heading =  new Heading();
		this.pos = new Position(world);
		this.origin = new Position(pos.getX(), pos.getY());
		this.cPerson = new Circle(virusSize, virusColor);
		this.cZone =  new Circle(virusInfectionRadius);
		cPerson.setStroke(Color.BLACK);
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
		
		
		public void move() {
			pos.move(heading, world, speed, origin);
		}
		
		//Draw Person [Circle]
		public void draw() {
			setupVirus();
			
			//Person Size
			cPerson.setRadius(virusSize);
			cPerson.setFill(virusColor);
			cPerson.setTranslateX(pos.getX());
			cPerson.setTranslateY(pos.getY());
			
			//Person Infection Radius
			cZone.setRadius(virusInfectionRadius);
			cZone.setTranslateX(pos.getX());
			cZone.setTranslateY(pos.getY());
			cZone.setStroke(Color.PURPLE);
			cZone.setFill(new Color(0,0,0,0));
			
			//Only show Infection Radius if Person is infected
			if(virusType.equals("covid") || virusType.equals("flu") || virusType.equals("multiple"))
				cZone.setVisible(true);
			else
				cZone.setVisible(false);
		}
		
		
		//Check if Person is within the infection zone and update state
		public void spread(Person other) {
			setupVirus();
			///INFECTED_COVID and SUCEPTIBLE 

			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virusType.equals("covid") && virusType.equals("sus")) {
					virusType = "covid";
					}
				}
			
			///INFECTED_FLU and SUCEPTIBLE 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virusType.equals("flu") && virusType.equals("sus")) {
					virusType = "flu";
					}
				}
//			
			///INFECTED_FLU and INFECTED_COVID 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virusType.equals("flu") && virusType.equals("covid")) {
					virusType = "multiple";
					}
				}
			}	
		
		public void checkRecovery(Pane world, Person p) {
			setupVirus();
			if(virusType.equals("covid") || virusType.equals("flu")  || virusType.equals("multiple")) {
				infectedTime++;
				if(infectedTime > recoveryDuration) {
					virusType = "recovered";
				}
			}
	}
		
		public void setupVirus() {
			switch(virusType) {
			case "covid":
				virusSize = Covid.size;
				virusInfectionRadius = Covid.infectionRadius;
				virusType =  Covid.virusType;
				virusColor = Covid.virusColor;
				recoveryDuration = Covid.recoveryDuration;
				break;
			case "flu":
				virusSize = Flu.size;
				virusInfectionRadius = Flu.infectionRadius;
				virusType = Flu.virusType;
				virusColor = Flu.virusColor;
				recoveryDuration = Flu.recoveryDuration;
				break;
			case "multiple":
				virusSize = Multiple.size;
				virusInfectionRadius = Multiple.infectionRadius;
				virusType = Multiple.virusType;
				virusColor = Multiple.virusColor;
				recoveryDuration = Multiple.recoveryDuration;
				break;
			case "sus":
				virusSize = Susceptible.size;
				virusInfectionRadius = Susceptible.infectionRadius;
				virusType = Susceptible.virusType;
				virusColor = Susceptible.virusColor;
				break;
			case "recovered":
				virusSize = Susceptible.size;
				virusType = "recovered";
				virusColor = Color.GREEN;
				break;
			default:
				
			}
		}
		
		public void updateVirus(Virus virus) {
				virusSize = virus.getSize();
				virusInfectionRadius = virus.getInfectionRadius();
				virusType =  virus.getVirusType();
				virusColor = virus.getVirusColor();
				recoveryDuration = virus.getRecoveryDuration();
			}
		}

