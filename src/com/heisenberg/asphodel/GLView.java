package com.heisenberg.asphodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.opengl.GLSurfaceView;

public class GLView extends GLSurfaceView {
    private GLRenderer renderer;

    public GLView(Context context) {
        super(context);
        
        // Use our custom renderer
        setEGLContextClientVersion(2);
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        renderer = new GLRenderer();
        setRenderer(renderer);
        
        // Player input
        setOnTouchListener(GameData.player);
    }
    
    // Override UI methods here for input
    @Override
    public boolean onTouchEvent(MotionEvent e) {
<<<<<<< HEAD
        float x = e.getX();
        float y = e.getY();

//        Log.i("POSITION", "" + x);

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Matrix.translateM(renderer.matView, 0, y + 0.001f, 0, x + 0.001f);
                break;
        }

=======
>>>>>>> e9643108a6c52ab47f922c0eb47216920383e5de
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
