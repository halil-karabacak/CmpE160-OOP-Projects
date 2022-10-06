package project.airport;

import project.airline.aircraft.Aircraft;

public class HubAirport extends Airport{	
	public HubAirport(int ID, double x, double y, double fuelCost, double operationFee, int aircraftCapacity){
		super(ID, x, y, fuelCost, operationFee, aircraftCapacity);
	}

	@Override
	public double landAircraft(Aircraft aircraft) {
		double fullnessCoef = 0.6 * Math.pow(Math.E, this.aircrafts.size()/(double)this.aircraftCapacity);
		double aircraftWeightRatio = aircraft.getWeightRatio();
		double landFee = fullnessCoef * aircraftWeightRatio * this.operationFee * 0.8;
		aircraft.setCurrentAirport(this);
		this.aircrafts.add(aircraft);
		return landFee;
	}
	
	@Override
	public double departAircraft(Aircraft aircraft) {
		double fullnessCoef = 0.6 * Math.pow(Math.E, this.aircrafts.size()/(double)this.aircraftCapacity);
		double aircraftWeightRatio = aircraft.getWeightRatio();
		double departureFee = this.operationFee * aircraftWeightRatio * fullnessCoef * 0.7;
		this.aircrafts.remove(aircraft);
		return departureFee;
	}
}
