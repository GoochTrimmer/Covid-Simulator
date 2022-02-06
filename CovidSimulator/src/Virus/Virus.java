package Virus;

import javafx.scene.paint.Color;

public class Virus implements VirusInterface{
	
	//Virus Parameters to be inherited by Virus child class
	public static int size = 3;
	public static int infectionRadius = 5;
	public static int recoveryDuration;
	public static String virusType = "virus";
	public static Color virusColor = Color.DARKBLUE;
	public static int totalVirusCount;
	
	public Virus() {
		totalVirusCount++;
	}
	
	@Override
	public int getSize() {
		return Virus.size;
	}
	
	@Override
	public void setSize(int s) {
		Virus.size = s;
	}
	
	@Override
	public int getInfectionRadius() {
		return Virus.infectionRadius;
	}
	
	@Override
	public void setInfectionRadius(int r) {
		Virus.infectionRadius = r;
	}
	
	@Override
	public int getRecoveryDuration() {
		return Virus.recoveryDuration;
	}
	
	@Override
	public void setRecoveryDuration(int r) {
		Virus.recoveryDuration = r;
	}

	@Override
	public String getVirusType() {
		return Virus.virusType;
	}

	@Override
	public void setVirusType(String t) {
		Virus.virusType = t;
	}
	
	@Override
	public Color getVirusColor() {
		return Virus.virusColor;
	}
	
	@Override
	public void setVirusColor(Color c) {
		Virus.virusColor = c;
	}

		
}
