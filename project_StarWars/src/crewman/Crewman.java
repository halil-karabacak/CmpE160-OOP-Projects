package project.crewman;

public abstract class Crewman {
	int id;
	String name;
	boolean isAlive;
	
	public Crewman(int id, String name) {
		this.id = id;
		this.name = name;
		this.isAlive = true;
	}
	
	
	
	public boolean isAlive() { // Not Padme ...
		return isAlive;
	}
	
	public void officerDead() {
		this.isAlive = false;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
