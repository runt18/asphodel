package com.heisenberg.asphodel;

import java.util.ArrayList;

public class GameData {
    private static boolean initialised = false;
    
    // Data
    private static ArrayList<Mesh> meshes;
    
    static void doInitialisation() {
        if (!initialised) {
            // Put initialisation code here
            TextureManager.init();
            meshes = new ArrayList<Mesh>();
            
            // Try making a mesh
            Mesh m = new Mesh(R.raw.test_mesh);
            meshes.add(m);
            
            initialised = true;
        }
        else {
            System.out.println("Warning: Attempted re-initialisation!");
        }
    }
}
