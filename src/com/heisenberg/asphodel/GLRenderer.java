package com.heisenberg.asphodel;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class GLRenderer implements Renderer {

    @Override
    public void onDrawFrame(GL10 gl) {
        // Blank background
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int arg1, int arg2) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        // General rendering settings
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

}
