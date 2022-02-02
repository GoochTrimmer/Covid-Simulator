package Virus;

import javafx.scene.paint.Color;

public class Susceptible extends Virus implements VirusInterface{
	
	public static int size = 5;
	public static int infectionRadius = 5;
	public static int recoveryDuration;
	public static String virusType = "sus";
	public static Color virusColor = Color.LIGHTBLUE;
	
	@Override
	public int getSize() {
		return Susceptible.size;
	}
	
	public void setSize(int s) {
		Susceptible.size = s;
	}
	
	@Override
	public int getInfectionRadius() {
		return Susceptible.infectionRadius;
	}
	
	@Override
	public int getRecoveryDuration() {
		return Susceptible.recoveryDuration;
	}
	
	@Override
	public String getVirusType() {
		return Susceptible.virusType;
	}
	
	@Override
	public Color getVirusColor() {
		return Susceptible.virusColor;
	}
	
	@Override
	public Virus spreadCovid(Virus virus) {
		virus.setSize(Covid.size);
		virus.setInfectionRadius(Covid.infectionRadius);
		virus.setVirusType(Covid.virusType);
		virus.setVirusColor(Covid.virusColor);
		return virus;
	}
	
}
