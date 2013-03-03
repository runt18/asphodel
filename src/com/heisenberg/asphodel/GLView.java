package com.heisenberg.asphodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.opengl.GLSurfaceView;

public class GLView extends GLSurfaceView {
    private float prevX = 0;
    private float prevY = 0;
    private GLRenderer renderer;
	
    public GLView(Context context) {
        super(context);
        
        // Use our custom renderer
        setEGLContextClientVersion(2);
        renderer = new GLRenderer();
        setRenderer(renderer);
        
        // Player input
        setOnTouchListener(GameData.player);
    }
    
    // Override UI methods here for input
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return true;
    }
    
    public static String getShader(int id) {
    	InputStream res = MyActivity.getInstance().getResources().openRawResource(id);
    	BufferedReader br = new BufferedReader(new InputStreamReader(res));
    	String str = "";
    	try{
    	    int worked = 1;
    		while(worked != -1) {
    			char[] cBuf = new char[1];
    			worked = br.read(cBuf, 0, 1);
    			
    			if (cBuf[0] != 0 && cBuf[0] != '\r')
    			    str = str+cBuf[0];
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
    	System.out.println(str);
    	return str.toString();
    }
}
