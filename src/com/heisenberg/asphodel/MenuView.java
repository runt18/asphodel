package com.heisenberg.asphodel;

import java.util.ArrayList;

import android.content.Context;

/*
 * Displays the main menu and handles user input for it
 */
public class MenuView extends android.opengl.GLSurfaceView {
    private ArrayList<MenuItem> items;

    public MenuView(Context context) {
        super(context);
        
        // Custom init
        items = new ArrayList<MenuItem>();
        
        items.add(new MenuItem(0.5f, 0.5f, R.drawable.button_play));
        
        // Renderer
        setEGLContextClientVersion(2);
        setRenderer(new MenuRenderer());
    }
    
}
