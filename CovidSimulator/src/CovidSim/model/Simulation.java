package CovidSim.model;

import java.util.HashMap;

import Virus.Covid;
import Virus.Flu;
import Virus.Susceptible;
import javafx.scene.layout.Pane;

public class Simulation {
	
	private HashMap<Person, Integer> population;
	
	public Simulation(Pane world, int popSize) {
		population =  new HashMap<Person, Integer>();
		
		for(int i=0; i<popSize; i++) {
			population.put(new Person(world, "sus", new Susceptible()), i);
		}
		
		population.put(new Person(world, "covid", new Covid()), popSize++);
		population.put(new Person(world, "flu", new Flu()), popSize++);
	}


	public HashMap<Person, Integer> getPopulation() {
		return population;
	}
	
	public void move() {
		population.forEach((key, value) -> {
			key.move();
		});
	}
	
	public void draw() {
		population.forEach((key, value) -> {
			key.draw();
		});
	}
	
	//Check if 2 Person are collided and try spreading infection
	public void checkCollisions() {
		population.forEach((key, value) -> {
			population.forEach((key2, value2) -> {
				if(key != key2) {
					key.spread(key2);
				}	
			});
		});
	}
	
	public void recover(Pane world) {
		population.forEach((key, value) -> {
			key.checkRecovery(world, key);
			});
		}
	}
	
