package CovidSim.model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.layout.Pane;

public class Simulation {
	
//	private ArrayList<Person> population;
	private HashMap<Person, Integer> population;
	
	public Simulation(Pane world, int popSize) {
		population =  new HashMap<Person, Integer>();
		
//		for(int i=0; i<popSize; i++) {
//			population.put(new Person(State.SUCEPTIBLE, world), i);
//		}
//		
//		population.put(new Person(State.INFECTED_BOTH, world), popSize++);
//		population.put(new Person(State.INFECTED_COVID, world), popSize++);
//		population.put(new Person(State.INFECTED_FLU, world), popSize++);
//		draw();
		
		for(int i=0; i<popSize; i++) {
			//Randomly Select a portion to be infected
			population.put(new Person(world, "sus"), i);
		}
		
		population.put(new Person(world, "covid"), popSize++);
		population.put(new Person(world, "flu"), popSize++);
	}


	public HashMap<Person, Integer> getPopulation() {
		return population;
	}
	
	public void move() {
		//Pass Lambda expression to forEach()
		population.forEach((key, value) -> {
			key.move();
		});

//		ArrayList
//		for(Person p : population) {
//			p.move();
		}
	
	public void draw() {
		//Pass Lambda expression to forEach()
		population.forEach((key, value) -> {
			key.draw();
			key.drawZone(key);
		});
		
//		ArrayList
//		for(Person p : population) {
//			p.draw();
//			//Draw Infection Zone around infected Person
//			p.drawZone(p);
//		}
	}
	
	//Check if 2 Person are collided and try spreading infection
	public void checkCollisions() {
		//Pass Lambda expression to forEach()
		population.forEach((key, value) -> {
			population.forEach((key2, value2) -> {
				//Spread
				if(key != key2) {
					key.spread(key2);
				}	
			});
		});
		
//		ArrayList
//		for (Person p: population) {
//			for(Person q: population) {
//				//Check if not the same person
//				if (p != q) {
//					p.spread(q);
//				}
//			}
//		}
	}
	
	public void recover(Pane world) {
		population.forEach((key, value) -> {
			key.checkRecovery(world, key);
		});
		
//		ArrayList
//		for (Person p: population) {
//			//Check if Person can recover
//			p.checkRecovery(world, p);
		}
	}
	
