package com.heisenberg.asphodel;

import android.opengl.GLES20;
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
        
        matRotate = new float[16];
        Matrix.setIdentityM(matRotate, 0);
        
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
        // Set the wvp matrix
        float[] wvp = new float[16];
        Matrix.rotateM(matRotate, 0, 5, 0, 1, 0);
        Matrix.multiplyMM(wvp, 0, mDh.matVP, 0, matRotate, 0);
        
        GLES20.glUniformMatrix4fv(mDh.matrixHandle, 1, false, wvp, 0);
        
        mMesh.draw(mDh);
    }

}
