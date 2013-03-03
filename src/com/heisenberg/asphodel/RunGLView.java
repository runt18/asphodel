package com.heisenberg.asphodel;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class RunGLView extends Activity {
    /**
     * Our OpenGL View object
     */
    private GLView mView;
    private static RunGLView current;
    
    /**
     * Called when the activity is first created.
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		current=this;
		
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GameData.doInitialisation();
        mView = new GLView(this);
        setContentView(mView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.run_glview, menu);
		return true;
	}
	
	public static RunGLView getInstance() {
        return current;
    }

}
