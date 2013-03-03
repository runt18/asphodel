package com.heisenberg.asphodel;

/**
 * Represents an item in the main menu
 */
public class MenuItem {
    private Texture tex;
    
    public MenuItem(float x, float y, int texID) {
        tex = TextureManager.loadTexture(texID, "Button"+texID);
    }
    
    public void draw() {
        
    }
}
