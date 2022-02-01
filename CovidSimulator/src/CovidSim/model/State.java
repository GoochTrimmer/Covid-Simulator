package CovidSim.model;

import javafx.scene.paint.Color;

//Enum to set State and their respective Color 

public enum State {

	SUCEPTIBLE {
		public Color getColor() {
			return Color.LIGHTBLUE;
		}
		
		public int getInfectionZone() {
			return 0;
		}
	}, 
	
	INFECTED_COVID {
		public Color getColor() {
			return Color.RED;
		}
		
		public int getInfectionZone() {
			return 15;
		}
	}, 
	
	INFECTED_FLU {
		public Color getColor() {
			return Color.YELLOW;
		}
		
		public int getInfectionZone() {
			return 10;
		}
	},
	
	INFECTED_BOTH {
		public Color getColor() {
			return Color.ORANGE;
		}
		
		public int getInfectionZone() {
			return 10;
		}
	},
	
	RECOVERED {
		public Color getColor() {
			return Color.GREEN;
		}
		
		public int getInfectionZone() {
			return 0;
		}
	};
	
	int infectionZone;
	
	//Abstract Functions
	public abstract Color getColor();
	public abstract int getInfectionZone();
}
