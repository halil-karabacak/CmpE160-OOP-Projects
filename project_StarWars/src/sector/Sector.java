package project.sector;

import project.Affiliation;

public class Sector {
	int id;
	String name;
	Affiliation affiliation;
	
	public Sector(int id, String name, Affiliation affiliation) {
		this.id = id;
		this.name = name;
		this.affiliation = affiliation;
	}
	
	public Affiliation getAffiliation() {
		return this.affiliation;
	}
	
	public void assault() {
		
	}

}
