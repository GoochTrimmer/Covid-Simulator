package Virus;

import javafx.scene.paint.Color;

public interface VirusInterface {
	
	//Virus Parameter Getter/Setters
	public int getSize();
	public void setSize(int s);
	public int getInfectionRadius();
	public void setInfectionRadius(int r);
	public int getRecoveryDuration();
	public void setRecoveryDuration(int r);
	public String getVirusType();
	public void setVirusType(String t);
	public Color getVirusColor();
	public void setVirusColor(Color c);
	
	//Spreading
	public Virus transformVirus(Virus virus);
}
