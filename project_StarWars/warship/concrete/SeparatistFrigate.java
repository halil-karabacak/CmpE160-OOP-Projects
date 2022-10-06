package project.warship.concrete;

import java.util.ArrayList;

import project.crewman.Crewman;
import project.sector.Sector;

public class SeparatistFrigate extends SeparatistDestroyer{
	
	public SeparatistFrigate(int id, String name, Sector currentSector, int
			coordinate, ArrayList<Crewman> crew) {
		super(id, name, currentSector, coordinate, crew);
		this.escapePods = 2;
		
	}
}
