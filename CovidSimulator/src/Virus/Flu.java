package Virus;

import javafx.scene.paint.Color;

public class Flu extends Virus implements VirusInterface{
	
	public static int size;
	public static int infectionRadius;
	public static int recoveryDuration = 100;
	public static String virusType = "flu";
	public static Color virusColor = Color.YELLOW;
	public static int totalFluCount;
	
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
}
