package project.warship.concrete;

import java.util.ArrayList;

import project.Affiliation;
import project.warship.Warship;

import project.crewman.*;
import project.crewman.general.concrete.Jedi;
import project.crewman.general.concrete.Sith;
import project.sector.Sector;

public class RepublicCruiser extends Warship{
	ArrayList<Crewman> captives;
	
	public RepublicCruiser(int id, String name, Sector currentSector, int coordinate
			, ArrayList<Crewman> crew) {
		super(id, name, currentSector, coordinate, crew);	
		this.affiliation = Affiliation.REPUBLIC;
	}
	
	public Jedi getCommander() {
		ArrayList<Jedi> jedisOnCruiser = new ArrayList<>();
		
		for (int b = 0; b < this.crew.size(); b++) {
			if (this.crew.get(b).getClass() == Jedi.class && this.crew.get(b).isAlive() == true) {
				jedisOnCruiser.add((Jedi) this.crew.get(b));
			}
		}
		Jedi generalJedi = jedisOnCruiser.get(0);
		
		for (int b = 1; b < jedisOnCruiser.size(); b++) {
			if (jedisOnCruiser.get(b).getIntelligence() > generalJedi.getIntelligence()) 
				generalJedi = jedisOnCruiser.get(b);
			else if (jedisOnCruiser.get(b).getIntelligence() == generalJedi.getIntelligence()) {
				if (jedisOnCruiser.get(b).getID() < generalJedi.getID())
					generalJedi = jedisOnCruiser.get(b);
			}
		}
		return generalJedi;
	}
	

	public void visitCoruscan() {
		
	}	
}
