package com.heisenberg.asphodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

public class GLView extends android.opengl.GLSurfaceView {
	
    public GLView(Context context) {
        super(context);
        // Use our custom renderer

        setEGLContextClientVersion(2);
        setRenderer(new GLRenderer());
    }
    
    // Override UI methods here for input!!
    public boolean onTouchEvent(MotionEvent event) {
        MyActivity act = MyActivity.getInstance();
        act.setContentView(new MenuView(act));
        return super.onTouchEvent(event);
    }
    
    public static String getShader(int id) {
    	InputStream res = MyActivity.getInstance().getResources().openRawResource(id);
    	BufferedReader br = new BufferedReader(new InputStreamReader(res));
    	StringBuilder sb = new StringBuilder();
    	try{
    		while(br.ready()) {
    			sb.append(br.readLine());
    		}
    	} catch(IOException e) {
    		Log.e("Asphodel","Failed to load shader with id:"+id);
    	} finally {
    			try {
					br.close();
				} catch (IOException e) {
					Log.e("Asphodel","Failed to close shader with id:"+id);
				}
    	}
    	return sb.toString();
    }
}
