package com.heisenberg.asphodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;

import com.heisenberg.asphodel.energy.AsphodelPointProvider;
import com.heisenberg.asphodel.energy.EnergyPointProvider;

public class MyActivity extends Activity {
    public static final String LOGTAG = "Asphodel";
    private static MyActivity curActivity;
    private static PowerManager.WakeLock wl;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Store
        curActivity = this;
        
        
        PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK, LOGTAG);
        wl.acquire();
        EnergyPointProvider epp = enablePP();
    }
    
    public void startGLStuff(View view) {
    	Intent intent = new Intent(this,RunGLView.class);
    	startActivity(intent);
    }
    
    private void stopApp(View view) {
    	wl.release();
    	this.finish();
    }
    
    private EnergyPointProvider enablePP() {
    	LocationManager locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    	return new AsphodelPointProvider(locationManager);
    }
    
    public static MyActivity getInstance() {
        return curActivity;
    }
}
