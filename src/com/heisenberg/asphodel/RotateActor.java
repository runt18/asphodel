package com.heisenberg.asphodel;

import android.opengl.Matrix;

/**
 * Example extension of actor. Rotates itself - yay
 * 
 * @author Ali
 *
 */
public class RotateActor extends Actor {
    
    public RotateActor(int meshID) {
        super(meshID);
    }

    public void update() {
        Matrix.rotateM(matRotate, 0, 1, 0, 1, 0);
        
        super.update();
    }
}
