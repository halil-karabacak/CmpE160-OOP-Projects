package project.crewman.general.concrete;

import project.crewman.General;

public class Jedi extends General{
	protected int sanity;
	protected int intelligence;
	
	protected Jedi(int id, String name, int experience, int midichlorian, int intelligence) {
		super(id, name, experience, midichlorian);
		this.intelligence = intelligence;
		this.sanity = 100;
	}
	
	public int getIntelligence() {
		return this.intelligence;
	}
	
	public int getSanity() {
		return this.sanity;
	}
	
    public int getForcePower() {
    	return this.midichlorian * 3;
    }
    
    public int getCombatPower() {
    	return this.getForcePower() * this.experience * (this.sanity - 100) * this.intelligence;
    }
    
}
