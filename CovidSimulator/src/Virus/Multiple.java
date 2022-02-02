package Virus;

import javafx.scene.paint.Color;

public class Multiple extends Virus implements VirusInterface{
	public static int size = 10;
	public static int infectionRadius = 15;
	public static int recoveryDuration = 10 * 60;
	public static String virusType = "multiple";
	public static Color virusColor = Color.ORANGE;
	
	@Override
	public int getSize() {
		return Multiple.size;
	}
	
	@Override
	public void setSize(int s) {
		Multiple.size = s;
	}
	
	@Override
	public int getInfectionRadius() {
		return Multiple.infectionRadius;
	}
	
	@Override
	public void setInfectionRadius(int r) {
		Multiple.infectionRadius = r;
	}
	
	@Override
	public int getRecoveryDuration() {
		return Multiple.recoveryDuration;
	}
	
	@Override
	public void setRecoveryDuration(int r) {
		Multiple.recoveryDuration = r;
	}

	@Override
	public String getVirusType() {
		return Multiple.virusType;
	}

	@Override
	public void setVirusType(String t) {
		Multiple.virusType = t;
	}
	
	@Override
	public Color getVirusColor() {
		return Multiple.virusColor;
	}
	
	@Override
	public void setVirusColor(Color c) {
		Multiple.virusColor = c;
	}
	
	@Override
	public Virus spreadMultiple(Virus virus) {
		System.out.println("RUNNING MULTIPLE");
		virus.setSize(Multiple.size);
		virus.setInfectionRadius(Multiple.infectionRadius);
		virus.setVirusType(Multiple.virusType);
		virus.setVirusColor(Multiple.virusColor);
		return virus;
	}
}
