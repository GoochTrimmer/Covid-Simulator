package CovidSim.model;

import javafx.scene.paint.Color;

public interface VirusInterface {		
	String getVirusType();
	void setVirusType(String t);
	Color getVirusColor();
	int getVirusInfectionRadius();
	int getVirusSize();
	void setVirusSize(int s);	
}