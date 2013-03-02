package com.heisenberg.asphodel;

import android.opengl.Matrix;

/**
 * Actors make up most of the game world
 * Subclass this to create more diverse behaviour, e.g. player objects
 * 
 * @author Ali
 *
 */
public class Actor {
    
    // Data
    private Mesh mMesh;
    private String id;
    
    // Transformation
    public float[] matTranslate;
    public float[] matRotate;
    
    public float[] matToWorld;
    
    public Actor(int meshID) {
        mMesh = new Mesh(meshID);
        id = "Actor" + meshID;
        
        GameData.addActor(this);
    }
    
    /*
     * Calculates the full to world matrix transformation from stored components
     */
    public void calcToWorld() {
        Matrix.multiplyMM(matToWorld, 0, matTranslate, 0, matRotate, 0);
    }
    
    /*
     * Moves the actor. Won't take effect until a  call to calcToWorld()
     */
    public void translate(float[] vec3) {
        if (vec3.length != 3) throw new Error("Wrong number of fields in vector");
        Matrix.translateM(matTranslate, 0, vec3[0], vec3[1], vec3[2]);
    }
    
    public void draw(DrawHelper mDh) {
        mMesh.draw(mDh);
    }

}
