package com.heisenberg.asphodel.energy;

import com.heisenberg.asphodel.MyActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class AsphodelLocationListener implements LocationListener {
	private Location lastloc;
	private float distance=0;
	@Override
	public void onLocationChanged(Location arg0) {
		Log.i(MyActivity.LOGTAG, arg0.toString());
		if(lastloc==null)
		{
			lastloc = arg0;
		}
		else
		{
			distance+=lastloc.distanceTo(arg0);
			lastloc=arg0;
		}
	}
	
	public float drainDistance() {
		float ret = distance;
		distance=0;
		return ret;
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
