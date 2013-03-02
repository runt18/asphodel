package com.heisenberg.asphodel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.heisenberg.asphodel.Vector2;

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

        // Store
        curActivity = this;
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        GameData.doInitialisation();
        
        mView = new GLView(this);
        setContentView(mView);
    }
    
    public static MyActivity getInstance() {
        return curActivity;
    }

    public static Vector2 getScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new Vector2(size.x, size.y);
    }
}
