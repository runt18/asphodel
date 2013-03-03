package com.heisenberg.asphodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameData {
    private static boolean initialised = false;
    
    // Data
    private static HashMap<String, Mesh> meshes;
    private static ArrayList<Actor> actors;
    private static ArrayList<Actor> orbs;
    public static Player player;
    public static float width, height;
    
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
        width = height = 0;
        
        TextureManager.init();
        meshes = new HashMap<String, Mesh>();
        actors = new ArrayList<Actor>();
        orbs = new ArrayList<Actor>();
        
        player = new Player();
        
        // Crappy terrain
        Actor terrain = new Actor(R.raw.terrain);
        terrain.color = new float[] {0.5f, 1, 0.5f, 1};
        
        // Rocks
        for (int i = 0; i < 15; i++) {
            Actor rock = new Actor(R.raw.rock);
            
            rock.color = new float[] {0.3f,0.3f,0.3f,1.0f};
            
            Random r = new Random();
            float x, y, z;
            x = r.nextFloat() - 0.5f;
            y = r.nextFloat() - 0.5f;
            z = r.nextFloat() - 0.5f;
            
            rock.translate(new float[] {x*600, y*20, z*600});
        }
        
        // Grass
        for (int i = 0; i < 40; i++) {
            Actor grass = new Actor(R.raw.grass);
            
            grass.color = new float[] {0.4f,1.0f,0.4f,1.0f};
            
            Random r = new Random();
            float x, y, z;
            x = r.nextFloat() - 0.5f;
            y = r.nextFloat() - 0.5f;
            z = r.nextFloat() - 0.5f;
            
            grass.translate(new float[] {x*500, y*10, z*500});
        }

        for(int i = 0; i < 10; i++){
            orbs.add(new Orb());
        }
        
        // Buildings...?
        Actor a = new Actor(R.raw.shapes);
        a.translate(new float[] {100, 0, 300});
        
        Actor a1 = new RotateActor(R.raw.message);
    }

    public static void addMesh(String key, Mesh mesh) {
        meshes.put(key, mesh);
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
    
    public static Mesh getMesh(String key) {
        return meshes.get(key);
    }

    public static void doUpdate() {
        player.update();

        if(new Random().nextFloat() > 0.99f){
            orbs.add(new Orb());
        }

        for (Actor a : actors) {
            a.update();
        }
        for(Actor o : orbs){
            o.update();
        }
    }
}
