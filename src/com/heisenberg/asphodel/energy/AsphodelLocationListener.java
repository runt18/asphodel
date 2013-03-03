package com.heisenberg.asphodel.energy;

import java.util.LinkedList;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.heisenberg.asphodel.MyActivity;

public class AsphodelLocationListener implements LocationListener {
	private Location lastloc;
	private LinkedList<TravelInfo> travel = new LinkedList<TravelInfo>();
	@Override
	public void onLocationChanged(Location arg0) {
		Log.i(MyActivity.LOGTAG, arg0.toString());
		if(lastloc==null) {
			lastloc = arg0;
		}
		else {
			float distance = lastloc.distanceTo(arg0);
			long time = arg0.getTime() - lastloc.getTime();
			TravelInfo newtravel = new TravelInfo(distance, time);
			travel.add(newtravel);
			lastloc=arg0;
		}
	}
	
	public TravelInfo nextTravel() {
		return travel.pop();
	}
	
	@Override
	public void onProviderDisabled(String arg0) {
	}

	@Override
	public void onProviderEnabled(String arg0) {
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

}
