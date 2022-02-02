package CovidSim.model;

import javafx.scene.layout.Pane;

public class Position {
	
	private double x, y;
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//Random Value * Width and Height an Pane
	//Add min radius so dosen't spawn on left side edge | 2*radius so it dosen't spawn on the right side edge
	
	public Position(Pane world) {
		this(Person.virusSize + Math.random() * (world.getWidth() - 2 * Person.virusSize),
				Person.virusSize + Math.random() * (world.getHeight() - 2 * Person.virusSize));
	}
	
	//Euclidean Distance formula to get distance between two Points
	
	public double distance(Position other) {
		return Math.sqrt(Math.pow(this.x - other.x , 2) + 
				Math.pow(this.y - other.y, 2));
	}
	
	
	//Movement Function
	public void move(Heading heading, Pane world, double speed, Position origin) {
		x += heading.getDx();
		y += heading.getDy();
		
		//Check if Collided with edge of Pane and Bounce || Obey social distance factor
		if(x < Person.virusSize || x > world.getWidth() - Person.virusSize  ||
				distance(origin) > Person.socialDistanceFactor) {
			heading.bounceX();
			x += heading.getDx();
		}
		
		if(y < Person.virusSize || y > world.getHeight() - Person.virusSize ||
				distance(origin) > Person.socialDistanceFactor) {
			heading.bounceY();
			y += heading.getDy();
		}
		
	}
	
}
