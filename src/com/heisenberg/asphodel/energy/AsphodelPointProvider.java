package com.heisenberg.asphodel.energy;

import com.heisenberg.asphodel.MyActivity;

import android.location.LocationManager;
import android.location.LocationProvider;
import android.util.Log;

public class AsphodelPointProvider implements EnergyPointProvider {
	
	public static final int REQ_INTERVAL = 10*1000; // 10 secs
    public static final int MIN_DISTANCE = 10; // 10 metres
    public static final String PROV = LocationManager.GPS_PROVIDER;
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
    	float dist = loclistener.drainDistance();
		
	}
	
	@Override
	public int tryTakePoints(int wanted) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addPoints(int points) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void savePoints() {
		// TODO Auto-generated method stub
		
	}
	

}
