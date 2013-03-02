package com.heisenberg.asphodel;

import com.heisenberg.asphodel.energy.AsphodelPointProvider;
import com.heisenberg.asphodel.energy.EnergyPointProvider;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MyActivity extends Activity {
    public static final String LOGTAG = "Asphodel";
    
	/**
     * Our OpenGL View object
     */
    private GLView mView;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EnergyPointProvider epp = enablePP();
        mView = new GLView(this);
        setContentView(mView);
        
        
    }
    
    private EnergyPointProvider enablePP() {
    	LocationManager locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    	return new AsphodelPointProvider(locationManager);
    }
}
