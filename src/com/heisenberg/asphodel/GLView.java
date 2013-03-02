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
    }
    
    // Override UI methods here for input
    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        MyActivity act = MyActivity.getInstance();
//        act.setContentView(new MenuView(act));

        float x = e.getX();
        float y = e.getY();

//        Log.i("POSITION", "" + x);

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - prevX;
                float dy = y - prevY;

//                Log.i(" POSITION", "" + dx);

                if(x >= 400){
                    renderer.ox += dx * 0.01f;
                    renderer.oy += -dy * 0.01f;
                }

//                // reverse direction of rotation above the mid-line
//                if (y > getHeight() / 2) {
//                    dx = dx * -1 ;
//                }
//
//                // reverse direction of rotation to left of the mid-line
//                if (x < getWidth() / 2) {
//                    dy = dy * -1 ;
//                }
//
//                mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR;  // = 180.0f / 320
//                requestRender();
                break;
        }

        prevX = x;
        prevY = y;
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
