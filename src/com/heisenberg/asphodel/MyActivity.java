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
import android.view.Display;
import android.graphics.Point;
import com.heisenberg.asphodel.Joystick;
import com.heisenberg.asphodel.Vector2;


public class MyActivity extends Activity {
    public static final String LOGTAG = "Asphodel";
    private static MyActivity curActivity;
    
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

        // Store
        curActivity = this;
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EnergyPointProvider epp = enablePP();

        GameData.tryInitialisation();

        mView = new GLView(this);
        setContentView(mView);
    }

    private Vector2 getScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new Vector2(size.x, size.y);
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
