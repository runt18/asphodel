package com.heisenberg.asphodel;

public class GameData {
    private static boolean initialised = false;
    
    static void doInitialisation() {
        if (!initialised) {
            // Put initialisation code here
            TextureManager.init();
            
            initialised = true;
        }
        else {
            System.out.println("Warning: Attempted re-initialisation!");
        }
    }
}
