package Virus;

import javafx.scene.paint.Color;

public class Recovered extends Virus implements VirusInterface{
	public static int size = 5;
	public static int infectionRadius = 5;
	public static int recoveryDuration;
	public static String virusType = "recovered";
	public static Color virusColor = Color.GREEN;
	public static int totalRecoveredCount;
	
	public Recovered() {
		totalRecoveredCount++;
	}
	
	@Override
	public int getSize() {
		return Recovered.size;
	}
	
	@Override
	public void setSize(int s) {
		Recovered.size = s;
	}
	
	@Override
	public int getInfectionRadius() {
		return Recovered.infectionRadius;
	}
	
	@Override
	public void setInfectionRadius(int r) {
		Recovered.infectionRadius = r;
	}
	
	@Override
	public int getRecoveryDuration() {
		return Recovered.recoveryDuration;
	}
	
	@Override
	public void setRecoveryDuration(int r) {
		Recovered.recoveryDuration = r;
	}

	@Override
	public String getVirusType() {
		return Recovered.virusType;
	}

	@Override
	public void setVirusType(String t) {
		Recovered.virusType = t;
	}
	
	@Override
	public Color getVirusColor() {
		return Recovered.virusColor;
	}
	
	@Override
	public void setVirusColor(Color c) {
		Recovered.virusColor = c;
	}
}
