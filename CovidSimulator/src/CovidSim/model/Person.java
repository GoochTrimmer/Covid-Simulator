package CovidSim.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Person {
	
	public static int radius = 5;
	public static int infectionZone = radius + 5;
	public static int recoveryTime = 5*50;
	public static double speed = 1;
	public static int socialDistanceFactor = 200;
	
	private State state;
	private Heading heading;
	private Position pos;
	private Position origin;
	private Circle cPerson, cZone;
	private Pane world;
	private int infectedTime = 0;
	
	
	public Person(State state, Pane world) {
		this.state = state;
		this.world = world;
		this.heading =  new Heading();
		this.pos = new Position(world);
		this.origin = new Position(pos.getX(), pos.getY());
		this.cPerson = new Circle(radius, state.getColor());
		this.cZone =  new Circle(radius);
		cPerson.setStroke(Color.BLACK);
		world.getChildren().add(cPerson);
		world.getChildren().add(cZone);
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
		cPerson.setFill(state.getColor());
	}
	
	public void move() {
		pos.move(heading, world, speed, origin);
	}
	
	//Draw Person [Circle]
	public void draw() {
		cPerson.setRadius(radius);
		cPerson.setTranslateX(pos.getX());
		cPerson.setTranslateY(pos.getY());
	}
	
	//Draw Infection Zone [Ring]
	public void drawZone(Person p) {
			cZone.setRadius(infectionZone);
			cZone.setTranslateX(pos.getX());
			cZone.setTranslateY(pos.getY());
			cZone.setStroke(Color.PURPLE);
			
			//Make a circle without color for ring effect
			cZone.setFill(new Color(0,0,0,0));
			
			if(p.getState() == State.INFECTED)
				cZone.setVisible(true);
			else
				cZone.setVisible(false);
		} 
	
	//Check for Collision between Two Person
	public void checkCol(Person other) {
		if (pos.collision(other.pos)) {
			if (other.getState() == State.INFECTED && state == State.SUCEPTIBLE) {
				setState(State.INFECTED);
			}
		}
	}
	
	
	//Check if Person is within infected Person and Spread
	public void spread(Person other) {
		//Check for Collision with Infection Zone
		if(cPerson.getBoundsInParent().intersects(other.cZone.getBoundsInParent())) {
			if (other.getState() == State.INFECTED && state == State.SUCEPTIBLE) {
				//TODO: Add duration to be within to become infected
				setState(State.INFECTED);
				}
			}
		}
	
	
	public void checkRecovery(Pane world, Person p) {
		if(state == State.INFECTED) {
			infectedTime++;
			if(infectedTime > recoveryTime) {
				setState(State.RECOVERED);
			}
		}
	}
	
}
