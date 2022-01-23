package CovidSim.model;

import javafx.scene.paint.Color;

//Enum to set State and their respective Color 

public enum State {
	SUCEPTIBLE {
		public Color getColor() {
			return Color.LIGHTBLUE;
		}
	}, 
	
	INFECTED {
		public Color getColor() {
			return Color.RED;
		}
	}, 
	
	RECOVERED {
		public Color getColor() {
			return Color.GREEN;
		}
	}, 
	
	ASYMPTOMATIC {
		public Color getColor() {
			return Color.YELLOW;
		}
	};
	
	public abstract Color getColor();
}
