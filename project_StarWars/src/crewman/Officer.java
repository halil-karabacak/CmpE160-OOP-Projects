package project.crewman;

import project.Intrinsic;

public class Officer extends Crewman{
	protected Intrinsic intrinsic;
	protected int intrinsicLevel;
	
	public Officer(int id, String name, Intrinsic intrinsic, int intrinsicLevel) {
		super(id, name);
		this.intrinsic = intrinsic;
		this.intrinsicLevel = intrinsicLevel;
	}
	
	public Intrinsic getIntrinsic() {
		return this.intrinsic;
	}
	
	public int getIntrinsicLevel() {
		return this.intrinsicLevel;
	}
	
	public void train() {
		
	}
	
}
