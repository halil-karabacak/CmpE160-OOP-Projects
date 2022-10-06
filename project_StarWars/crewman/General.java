package project.crewman;

import project.IForceUser;

public abstract class General extends Crewman implements IForceUser{	
	protected int experience;
	protected int midichlorian;
	
	public General(int id, String name, int experience, int midichlorian) {
		super(id, name);
		this.experience = experience;
	}
	
	
}
