package Virus;

import javafx.scene.paint.Color;

public class Covid extends Virus implements VirusInterface{
	public static int size;
	public static int infectionRadius = 5;
	public static int recoveryDuration = 300;
	public static String virusType = "covid";
	public static Color virusColor = Color.RED;
	
	@Override
	public int getSize() {
		return Covid.size;
	}
	
	@Override
	public int getInfectionRadius() {
		return Covid.infectionRadius;
	}
	
	@Override
	public int getRecoveryDuration() {
		return Covid.recoveryDuration;
	}
	
	@Override
	public String getVirusType() {
		return Covid.virusType;
	}
	
	@Override
	public Color getVirusColor() {
		return Covid.virusColor;
	}
	
	@Override
	public Virus spreadCovid(Virus virus) {
		virus.setSize(Covid.size);
		virus.setInfectionRadius(Covid.infectionRadius);
		virus.setVirusType(Covid.virusType);
		virus.setVirusColor(Covid.virusColor);
		return virus;
	}
	
	@Override
	public Virus spreadMultiple(Virus virus) {
		System.out.println("HERE");
		virus.setSize(Multiple.size);
		System.out.println("Size: " + Multiple.size);
		virus.setInfectionRadius(Multiple.infectionRadius);
		virus.setVirusType(Multiple.virusType);
		virus.setVirusColor(Multiple.virusColor);
		return virus;
	}


} 
