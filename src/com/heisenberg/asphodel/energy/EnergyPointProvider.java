package com.heisenberg.asphodel.energy;

public interface EnergyPointProvider {
	// deducts wanted points from the stored total, if possible.
	// returns the number of points deducted up to wanted.
	public int tryTakePoints(int wanted);
	
	// adds points to total stored points.
	// to be used by location listener.
	public void addPoints(int points);
	
	//called by MainActivity,
	//saves the points using "Shared Preferences" when the app is shut down
	public void savePoints();
}
