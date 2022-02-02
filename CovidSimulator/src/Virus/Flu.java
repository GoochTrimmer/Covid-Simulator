package Virus;

import javafx.scene.paint.Color;

public class Flu extends Virus implements VirusInterface{
	
	public static int size;
	public static int infectionRadius;
	public static int recoveryDuration = 100;
	public static String virusType = "flu";
	public static Color virusColor = Color.YELLOW;
	
	@Override
	public int getSize() {
		return Flu.size;
	}
	
	@Override
	public int getInfectionRadius() {
		return Flu.infectionRadius;
	}
	
	@Override
	public int getRecoveryDuration() {
		return Flu.recoveryDuration;
	}
	
	@Override
	public String getVirusType() {
		return Flu.virusType;
	}
	
	@Override
	public Color getVirusColor() {
		return Flu.virusColor;
	}
	
	@Override
	public Virus spreadFlu(Virus virus) {
		virus.setSize(Flu.size);
		virus.setInfectionRadius(Flu.infectionRadius);
		virus.setVirusType(Flu.virusType);
		virus.setVirusColor(Flu.virusColor);
		return virus;
	}
	
	@Override
	public Virus spreadMultiple(Virus virus) {
		System.out.println("Flu");
		virus.setSize(Multiple.size);
		virus.setInfectionRadius(Multiple.infectionRadius);
		virus.setVirusType(Multiple.virusType);
		virus.setVirusColor(Multiple.virusColor);
		return virus;
	}
}
