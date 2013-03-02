package com.heisenberg.asphodel;

import android.content.Context;
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
}
