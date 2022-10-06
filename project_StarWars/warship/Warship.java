package project.warship;

import java.util.ArrayList;

import project.Affiliation;
import project.IWarship;
import project.Intrinsic;
import project.crewman.Crewman;
import project.crewman.General;
import project.crewman.Officer;
import project.crewman.general.concrete.Jedi;
import project.crewman.general.concrete.Sith;
import project.sector.Sector;

public abstract class Warship implements IWarship{
	int id;
	String name;
	Sector currentSector;
	int coordinate;
	int armamentPower;
	int shieldPower;
	int crewCapacity;
	General currentGeneral;
	protected ArrayList<Crewman> crew;
	protected Affiliation affiliation;
	
	public Warship(int id,String name, Sector currentSector, int coordinate,
			ArrayList<Crewman> crew) {
		this.id = id;
		this.name = name;
		this.currentSector = currentSector;
		this.coordinate = coordinate;
	}
	
	public void addCrewman(Crewman crewman) {
		this.crew.add(crewman);
	}
		
	public void removeCrewman(Crewman crewman) {
		this.crew.remove(crewman);
	}
	
	public void setGeneral(General newGeneral) {
		this.currentGeneral = newGeneral;
	}
	
	protected int getMaxIntrinsicLevels() {
		int[] ans = new int[5];
	    /* 
	     *  0 TACTICAL
	     *  1 PILOTING
	     *	2 GUNNERY
	     *	3 ENGINEERING
	     *	4 COMMAND
	     */

		for (int b = 0; b < this.crew.size(); b++) {
			if (this.crew.get(b).isAlive() == false)
				continue;
			if (this.crew.get(b).getClass() == Officer.class) {
				Officer officer = (Officer)this.crew.get(b);
				
				switch (officer.getIntrinsic()) {
				case TACTICAL: {
					ans[0] = Math.max(ans[0], officer.getIntrinsicLevel());
					break;
				}
				
				case PILOTING: {
					ans[1] = Math.max(ans[1], officer.getIntrinsicLevel());
					break;
				}
				
				case GUNNERY: {
					ans[2] = Math.max(ans[2], officer.getIntrinsicLevel());
					break;
				}
				
				case ENGINEERING: {
					ans[3] = Math.max(ans[3], officer.getIntrinsicLevel());
					break;
				}
				
				case COMMAND: {
					ans[4] = Math.max(ans[4], officer.getIntrinsicLevel());
					break;
				}
				
				default:
					throw new IllegalArgumentException("Unexpected value: " + officer.getIntrinsic());
				}
			}
		}
		return (ans[0] + 1) * (ans[1] + 1) * (ans[2] + 1) * (ans[3] + 1) * (ans[4] + 1);
	}
	
	public int findSectorBuff() {
		if (this.currentSector.getAffiliation() == this.affiliation)
			return 3;
		return 2;
	}
	

	public int getPowerOutput() {
		int powerOutput_int, contributionOFOfficers_int, sectorBuff_int;
		int contributonOFGenerals_int = 0;

		for (int b = 0; b < this.crew.size(); b++) {
			if (this.crew.get(b).isAlive() == false)
				continue;
			if (this.crew.get(b).getClass() == General.class) {
				General general = (General) this.crew.get(b);
				contributonOFGenerals_int += general.getCombatPower();
			}
		}
		
		contributionOFOfficers_int = this.getMaxIntrinsicLevels();
		sectorBuff_int = this.findSectorBuff();	
		
		powerOutput_int = sectorBuff_int * (this.armamentPower + this.shieldPower + contributonOFGenerals_int + contributionOFOfficers_int);
		
		return powerOutput_int;
	}
	
    public void upgradeArmament(int amount) {
    	this.armamentPower += amount;
    }
    
    public void upgradeShield(int amount) {
    	this.shieldPower += amount;
    }
    

	public void jumpToSector(Sector sector, int coordinate) {
		// TODO Auto-generated method stub
		
	}
	
	

}
