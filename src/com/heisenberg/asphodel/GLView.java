package com.heisenberg.asphodel;

import android.content.Context;

public class GLView extends android.opengl.GLSurfaceView {

    public GLView(Context context) {
        super(context);
        
        // Use our custom renderer

        setEGLContextClientVersion(2);
        setRenderer(new GLRenderer());
    }
    
    // Override UI methods here for input!!
}
