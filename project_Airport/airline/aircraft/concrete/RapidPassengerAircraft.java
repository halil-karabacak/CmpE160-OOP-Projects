package project.airline.aircraft.concrete;

import project.airline.aircraft.PassengerAircraft;
import project.airport.Airport;


public class RapidPassengerAircraft extends PassengerAircraft{
	protected double fuelConsumption = 5.3;
	
	public RapidPassengerAircraft(Airport currentAirport, double operationalCost) {
		super(currentAirport, operationalCost);
		this.setWeight(80000);
		this.maxWeight = 185000;
		this.floorArea = 120; // seat area
		this.fuelCapacity = 120000; // fuel volume
		this.aircraftTypeMultiplier = 1.9;
	}
	
	public double getFlightCost(Airport toAirport) {
		double distance = this.currentAirport.getDistance(toAirport);
		double departureCost = this.currentAirport.departAircraft(this);
		double landingCost = toAirport.landAircraft(this);
		double fullness = this.getFullness();		
		double flightOperationCost = fullness * distance * 0.2;
		return landingCost + departureCost + flightOperationCost;
	}
	
	public double getFuelConsumption(double distance) {
		double distanceParameter = distance / (double)7000;
		double bathtubCoef = 25.9324 * Math.pow(distanceParameter, 4) - 50.5633 * Math.pow(distanceParameter, 3)
				+ 35.0554 *  Math.pow(distanceParameter, 2) - 9.90346 * distanceParameter + 1.97413;
		double takeOffCons = this.getWeight() * 0.1 / this.fuelWeight;
		double cruiseCons = this.fuelConsumption * bathtubCoef * distance;
		return takeOffCons + cruiseCons;
	}
}
