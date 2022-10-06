package project.warship.concrete;

import java.util.ArrayList;

import project.Affiliation;
import project.crewman.Crewman;
import project.sector.Sector;

public class SeparatistBattleship extends SeparatistDestroyer{
	
	public SeparatistBattleship(int id, String name, Sector currentSector, int
			coordinate, ArrayList<Crewman> crew) {
		super(id, name, currentSector, coordinate, crew);
		this.escapePods = 3;
	}
}
