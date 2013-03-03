package com.heisenberg.asphodel.energy;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.util.Log;

import com.heisenberg.asphodel.MyActivity;

public class AsphodelPointProvider implements EnergyPointProvider {
	
	public static final int REQ_INTERVAL = 20*1000; // 20 secs
    public static final int MIN_DISTANCE = 10; // 10 metres
    public static final String PROV = LocationManager.GPS_PROVIDER;
    private static final String POINTS_KEY = "energyPoints";
    private int points = 0;
    private final AsphodelLocationListener loclistener;
    
    
	public AsphodelPointProvider(LocationManager lm) {
    	LocationProvider providerName = lm.getProvider(PROV);
        
    	if (providerName != null) {
        	loclistener = new AsphodelLocationListener();
        	lm.requestLocationUpdates(PROV,REQ_INTERVAL,MIN_DISTANCE, loclistener);
        }
        else {
        	loclistener = null;
        	Log.w(MyActivity.LOGTAG, "gps provider is disabled");
        }
	}
	
    private void convert() {
    	TravelInfo travel = loclistener.nextTravel();
    	double distance = (double) travel.distance / 1000;
    	double time = (double) travel.time / (60*60);
    	double aspeed = distance/time;
    	int x = 0;
		if (aspeed > 1.8 && aspeed <= 7) { 
			x = 1; 
		} 
		else if (aspeed > 7 && aspeed <= 11) { 
			x = 2; 
		}
		else if (aspeed > 11 && aspeed <= 17) {
			x = 3;
		}
		else if (aspeed > 17 && aspeed <= 40) {
			x = 4;
		}
		points += x;
	}
	
	@Override
	public int tryTakePoints(int wanted) {
		convert();
		if (wanted < points) {
			points -= wanted;
			return wanted;
		}
		else {
			int temp = points;
			points = 0;
			return temp;
		}
	}

	@Override
	public void addPoints(int points) {
		this.points += points;
	}

	@Override
	public void savePoints() {
		Activity current = MyActivity.getInstance();
		SharedPreferences store = current.getPreferences(Activity.MODE_PRIVATE);
		store.edit().putInt(POINTS_KEY, points).commit();
	}
}
