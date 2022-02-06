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
	public static int radius = Susceptible.size;

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
		this.world = world;
		this.heading =  new Heading();
		this.pos = new Position(world);
		this.origin = new Position(pos.getX(), pos.getY());
		this.cPerson = new Circle(virus.getSize(), virus.getVirusColor());
		this.cZone =  new Circle(virus.getInfectionRadius());
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
			System.out.println("Total Ppl: " + Person.totalPop);
			System.out.println("Total Covid: " + Covid.totalCovidCount);
			System.out.println("Total Flu: " + Flu.totalFluCount);
			System.out.println("Total Multiple: " + Multiple.totalMultipleCount);
			System.out.println("Total Sus:" + Susceptible.totalSusCount);
			System.out.println("Total Recovered: " + Recovered.totalRecoveredCount);
			
			//Person Size
			cPerson.setRadius(virus.getSize());
			cPerson.setFill(virus.getVirusColor());
			cPerson.setTranslateX(pos.getX());
			cPerson.setTranslateY(pos.getY());
			
			//Person Infection Radius
			cZone.setRadius(virus.getInfectionRadius());
			cZone.setTranslateX(pos.getX());
			cZone.setTranslateY(pos.getY());
			cZone.setStroke(Color.PURPLE);
			cZone.setFill(new Color(0,0,0,0));
			
			//Only show Infection Radius if Person is infected
			if(virus.getVirusType().equals("covid") || virus.getVirusType().equals("flu") || virus.getVirusType().equals("multiple"))
				cZone.setVisible(true);
			else
				cZone.setVisible(false);
		}
		
		
		//Check if Person is within the infection zone and update state
		public void spread(Person other) {
			
			///INFECTED_COVID and SUCEPTIBLE 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virus.getVirusType().equals("covid") && virus.getVirusType().equals("sus")) {
					virus = new Covid();
					Susceptible.totalSusCount--;
					}
				}
			
			///INFECTED_FLU and SUCEPTIBLE 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virus.getVirusType().equals("flu") && virus.getVirusType().equals("sus")) {
					virus = new Flu();
					Susceptible.totalSusCount--;
					}
				}
//			
			///INFECTED_FLU and INFECTED_COVID 
			if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
				if (other.virus.getVirusType().equals("flu") && virus.getVirusType().equals("covid")) {
					virus = new Multiple();
					other.virus = new Multiple();
					Covid.totalCovidCount--;
					Flu.totalFluCount--;
					}
				}
			}	
		
		//Update here
		public void checkRecovery(Pane world, Person p) {
			if(virus.getVirusType().equals("covid") || virus.getVirusType().equals("flu")  || virus.getVirusType().equals("multiple")) {
				infectedTime++;
				
				//Recover and Update Count
				if(infectedTime > virus.getRecoveryDuration() && virus.getVirusType().equals("covid")) {
					virus = new Recovered();
					Covid.totalCovidCount--;
				}
				
				if(infectedTime > virus.getRecoveryDuration() && virus.getVirusType().equals("flu")) {
					virus = new Recovered();
					Flu.totalFluCount--;
				}
				
				if(infectedTime > virus.getRecoveryDuration() && virus.getVirusType().equals("multiple")) {
					virus = new Recovered();
					Multiple.totalMultipleCount--;
				}
			}
		}		
	}	

