package com.heisenberg.asphodel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.Point;
import com.heisenberg.asphodel.Joystick;
import com.heisenberg.asphodel.Vector2;


public class MyActivity extends Activity {
    public static MyActivity curActivity;
    static Joystick stick;
    
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
        stick = new Joystick(getScreenSize());
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        GameData.doInitialisation();
        
        mView = new GLView(this);
        setContentView(mView);
    }

    private Vector2 getScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new Vector2(size.x, size.y);
    }
}
