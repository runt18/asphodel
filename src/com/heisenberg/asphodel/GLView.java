package com.heisenberg.asphodel;

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
}
