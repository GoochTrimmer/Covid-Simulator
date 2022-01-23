package CovidSim.model;

public class Heading {
	
	//Speed and Direction Initialization
	public static double speed = 1;
	private double dx, dy;
	
	public Heading(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public double getDx(double speed) { return dx; }
	public double getDy(double speed) { return dy; }
	
	public Heading() {
		double dir = Math.random() * 2 * Math.PI; //Random Angle in Radians
		dx = Math.sin(dir) * speed; //X angle in radians
		dy = Math.cos(dir) * speed; //Y angle in radians
	}
	
	//Bounce in the opposite direction when collided
	public void bounceX() {
		dx *= -1;
	}
	
	public void bounceY() {
		dy *= -1;
	}
	
}
