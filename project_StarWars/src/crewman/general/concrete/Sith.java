package project.crewman.general.concrete;

import project.crewman.General;

public class Sith extends General{
	int persuasion;
	
	public Sith(int id, String name, int experience, int midichlorian, int persuasion) {
		super(id, name, experience, midichlorian);
		this.persuasion = persuasion;
	}
	
	
    public int getForcePower() {
    	return this.midichlorian * 4;
    }
    
    public int getCombatPower() {
    	return this.getCombatPower() * this.experience * this.persuasion;
    }
}
