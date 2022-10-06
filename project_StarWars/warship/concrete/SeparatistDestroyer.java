package project.warship.concrete;

import java.util.ArrayList;

import javax.management.remote.SubjectDelegationPermission;

import project.Affiliation;
import project.crewman.Crewman;
import project.crewman.General;
import project.crewman.general.concrete.Jedi;
import project.crewman.general.concrete.Sith;
import project.sector.Sector;
import project.warship.Warship;

public class SeparatistDestroyer extends Warship{
	int escapePods;
	
	public SeparatistDestroyer(int id, String name, Sector currentSector, int
			coordinate, ArrayList<Crewman> crew) {
		super(id, name, currentSector, coordinate, crew);
		this.affiliation = Affiliation.SEPARATISTS;
		this.escapePods = 1;
	}
	
	public Sith getCommander() {
		ArrayList<Sith> sithsOnCruiser = new ArrayList<>();
		
		for (int b = 0; b < this.crew.size(); b++) {
			if (this.crew.get(b).getClass() == Sith.class && this.crew.get(b).isAlive() == true) {
				sithsOnCruiser.add((Sith) this.crew.get(b));
			}
		}
		Sith generalSith = sithsOnCruiser.get(0);
		for (int b = 1; b < sithsOnCruiser.size(); b++) {
			if (sithsOnCruiser.get(b).getCombatPower() > generalSith.getCombatPower())
				generalSith = sithsOnCruiser.get(b);
			
			else if (sithsOnCruiser.get(b).getCombatPower() == generalSith.getCombatPower()) {
				if (sithsOnCruiser.get(b).getID() < generalSith.getID())
					generalSith = sithsOnCruiser.get(b);	
			}	
		}
		return generalSith;
	}
	
	
}
