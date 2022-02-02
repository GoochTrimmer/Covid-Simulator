package Virus;

import javafx.scene.paint.Color;

public class Virus implements VirusInterface{
	
	//Virus Parameters to be inherited by Virus child class
	public static int size = 3;
	public static int infectionRadius = 5;
	public static int recoveryDuration;
	public static String virusType = "virus";
	public static Color virusColor = Color.DARKBLUE;
	
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

	@Override
	public Virus spreadCovid(Virus virus) {
		virus.setSize(Covid.size);
		virus.setInfectionRadius(Covid.infectionRadius);
		virus.setVirusType(Covid.virusType);
		virus.setVirusColor(Covid.virusColor);
		return virus;
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
		System.out.println("Virus");
		virus.setSize(Multiple.size);
		virus.setInfectionRadius(Multiple.infectionRadius);
		virus.setVirusType(Multiple.virusType);
		virus.setVirusColor(Multiple.virusColor);
		return virus;
	}
	



		
}
