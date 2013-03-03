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
    }
    
    // Override UI methods here for input
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

//        Log.i("POSITION", "" + x);

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Matrix.translateM(renderer.matView, 0, y + 0.001f, 0, x + 0.001f);
                break;
        }

        return true;
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
