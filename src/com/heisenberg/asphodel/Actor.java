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
    public float[] matScale;
    
    public float[] matToWorld;
    
    // Color
    public float[] color = new float[] {1.0f, 0.5f, 0.5f, 1.0f};
    
    public Actor(int meshID) {
        mMesh = new Mesh(meshID);
        id = "Actor" + meshID;
        
        matRotate = new float[16];
        matTranslate = new float[16];
        matScale = new float[16];
        matToWorld = new float[16];
        
        Matrix.setIdentityM(matRotate, 0);
        Matrix.setIdentityM(matTranslate, 0);
        Matrix.setIdentityM(matScale, 0);
        
        GameData.addActor(this);
    }
    
    /*
     * Calculates the full to world matrix transformation from stored components
     */
    public void calcToWorld() {
        float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, matRotate, 0, matScale, 0);
        Matrix.multiplyMM(matToWorld, 0, matTranslate, 0, temp, 0);
    }
    
    /*
     * Moves the actor. Won't take effect until a  call to calcToWorld()
     */
    public void translate(float[] vec3) {
        if (vec3.length != 3) throw new Error("Wrong number of fields in vector");
        Matrix.translateM(matTranslate, 0, vec3[0], vec3[1], vec3[2]);
    }
    
    /*
     * Rotates the actor, won't take effect until calcToWorld()
     */
    public void rotate(float angle, float[] axis) {
        if (axis.length != 3) throw new Error("Wrong number of fields in vector");
        Matrix.rotateM(matRotate, 0, angle, axis[0], axis[1], axis[2]);
    }
    
    /*
     * Scales the actor, won't take effect till calcToWorld()
     */
    public void scale(float sf) {
        Matrix.scaleM(matScale, 0, sf, sf, sf);
    }
    
    /*
     * Do game stuff. Fill with awesome game code, maybe in subclasses?
     */
    public void update() {
        // Does nothing but calcToWorld
        calcToWorld();
    }
    
    /*
     * Displays the actor. Called by GLRenderer
     */
    public void draw(DrawHelper mDh) {
        // Set the wvp matrix
        float[] wvp = new float[16];
        Matrix.multiplyMM(wvp, 0, mDh.matVP, 0, matToWorld, 0);
        GLES20.glUniformMatrix4fv(mDh.wvpHandle, 1, false, wvp, 0);
        
        // Set world-inverse-transpose matrix
        // Identity is fine for now
        float[] wi = new float[16];
        float[] wit = new float[16];
        Matrix.invertM(wi, 0, matToWorld, 0);
        Matrix.transposeM(wit, 0, wi, 0);
        GLES20.glUniformMatrix4fv(mDh.witHandle, 1, false, wit, 0);
        
        // Set drawing color
        GLES20.glUniform4f(mDh.colorHandle, color[0], color[1], color[2], color[3]);
        
        // Draw the actor's mesh
        mMesh.draw(mDh);
    }

}
