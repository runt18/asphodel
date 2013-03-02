package com.heisenberg.asphodel;

import android.content.Context;

/*
 * Displays the main menu and handles user input for it
 */
public class MenuView extends android.opengl.GLSurfaceView {

    public MenuView(Context context) {
        super(context);
        
        setEGLContextClientVersion(2);
        setRenderer(new MenuRenderer());
    }
    
}
