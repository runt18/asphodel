package com.heisenberg.asphodel;

import android.opengl.GLES20;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.opengl.GLSurfaceView.Renderer;
//import com.heisenberg.asphodel.Triangle;

public class GLRenderer implements Renderer {
    private Triangle tri;

    @Override
    public void onDrawFrame(GL10 gl) {
        // Blank background
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        tri.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        // General rendering settings
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        tri = new Triangle();
    }

}
