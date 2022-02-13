package VSim.virus;

import javafx.scene.paint.Color;

public class Covid extends Virus implements VirusInterface{
	public static int size = 30;
	public static int infectionRadius = 5;
	public static int recoveryDuration = 300;
	public static String virusType = "covid";
	public static Color virusColor = Color.RED;
	public static int totalCovidCount;
	
	public Covid() {
		totalCovidCount++;
	}
	
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
	


} 
