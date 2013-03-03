package com.heisenberg.asphodel;

import java.util.ArrayList;

public class GameData {
    private static boolean initialised = false;
    
    // Data
    private static ArrayList<Mesh> meshes;
    private static ArrayList<Actor> actors;
    
    static void tryInitialisation() {
        if (!initialised) {
            doInitialisation();
            
            initialised = true;
        }
        else {
            System.out.println("Warning: Attempted re-initialisation!");
        }
    }
    
    /*
     * Game initialisation here
     */
    static void doInitialisation() {
        // Put initialisation code here
        TextureManager.init();
        meshes = new ArrayList<Mesh>();
        actors = new ArrayList<Actor>();
        
        // Try making an actor
        Actor a = new Actor(R.raw.shapes);
        a.translate(new float[] {20, 0, 30});
        
        Actor a1 = new RotateActor(R.raw.message);
    }

    public static void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }
    
    public static void addActor(Actor actor) {
        actors.add(actor);
    }

    public static Actor getActor(int i) {
        return actors.get(i);
    }

    public static ArrayList<Actor> getActors() {
        return actors;
    }

    public static void doUpdate() {
        for (Actor a : actors) {
            a.update();
        }
    }
}
