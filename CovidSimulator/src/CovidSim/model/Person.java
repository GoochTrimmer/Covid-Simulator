package CovidSim.model;

import Virus.Covid;
import Virus.Flu;
import Virus.Multiple;
import Virus.Recovered;
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
	public static int totalPop;
	
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
		updateVirus(virus);
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
		totalPop++;
	}
		
		
		public void move() {
			pos.move(heading, world, speed, origin);
		}
		
		//Draw Person [Circle]
		public void draw() {
			updateVirus(virus);
			System.out.println("Total Ppl: " + Person.totalPop);
			System.out.println("Total Covid: " + Covid.totalCovidCount);
			System.out.println("Total Flu: " + Flu.totalFluCount);
			System.out.println("Total Multiple: " + Multiple.totalMultipleCount);
			System.out.println("Total Sus:" + Susceptible.totalSusCount);
			System.out.println("Total Recovered: " + Recovered.totalRecoveredCount);
			
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
//			setupVirus();
			updateVirus(virus);
			///INFECTED_COVID and SUCEPTIBLE 

			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virusType.equals("covid") && virusType.equals("sus")) {
//					virusType = "covid";
					virus = virus.transformVirus(new Covid());
					Susceptible.totalSusCount--;
					Covid.totalCovidCount++;
					updateVirus(virus);
//					System.out.println(virus.getVirusType());
					}
				}
			
			///INFECTED_FLU and SUCEPTIBLE 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virusType.equals("flu") && virusType.equals("sus")) {
//					virusType = "flu";
					virus = virus.transformVirus(new Flu());
					updateVirus(virus);
					Susceptible.totalSusCount--;
					Flu.totalFluCount++;
//					System.out.println(virus.getVirusType());
					}
				}
//			
			///INFECTED_FLU and INFECTED_COVID 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virusType.equals("flu") && virusType.equals("covid")) {
//					virusType = "multiple";
					virus = virus.transformVirus(new Multiple());
					updateVirus(virus);
					Covid.totalCovidCount--;
					Flu.totalFluCount--;
					Multiple.totalMultipleCount += 2;
//					System.out.println(virus.getVirusType());
					}
				}
			}	
		
		//Update here
		public void checkRecovery(Pane world, Person p) {
			updateVirus(virus);
			if(virusType.equals("covid") || virusType.equals("flu")  || virusType.equals("multiple")) {
				infectedTime++;
				
				//Recover and Update Count
				if(infectedTime > recoveryDuration && virusType.equals("covid")) {
					virus = virus.transformVirus(new Recovered());
					Recovered.totalRecoveredCount++;
					Covid.totalCovidCount--;
				}
				
				if(infectedTime > recoveryDuration && virusType.equals("flu")) {
					virus = virus.transformVirus(new Recovered());
					Recovered.totalRecoveredCount++;
					Flu.totalFluCount--;
				}
				
				if(infectedTime > recoveryDuration && virusType.equals("multiple")) {
					virus = virus.transformVirus(new Recovered());
					Recovered.totalRecoveredCount++;
					Multiple.totalMultipleCount--;
				}
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

