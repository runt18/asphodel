package com.heisenberg.asphodel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MyActivity extends Activity {
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
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        GameData.doInitialisation();
        
        mView = new GLView(this);
        setContentView(mView);
        
        // Store
        curActivity = this;
    }
    
    public static MyActivity getInstance() {
        return curActivity;
    }
}
