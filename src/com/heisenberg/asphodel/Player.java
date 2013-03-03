package com.heisenberg.asphodel;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Player implements OnTouchListener {

    Joystick lJoy, rJoy;
    public float[] eye;
    public float[] dir;
    public float[] targRot;
    
    private float xRot, yRot;
    
    // Constructor
    public Player() {
        lJoy = new Joystick();
        rJoy = new Joystick();
        
        eye = new float[] {0, 50, -200};
        dir = new float[] {0, -0.1f, 1};
        
        xRot = (float) Math.PI / 2;
        yRot = 0;
    }
    
    // Updates
    public void update() {
        updateCam();
    }
    
    public void updateCam() {
        // Get fake joystick input
        float[] ld = lJoy.getPos();
        float[] rd = rJoy.getPos();
        
        // Camera eye movement
        float[] up = new float[] {0, 1, 0};
        float[] dir2D = new float[] {dir[0], 0, dir[2]};
        float[] alt2D = new float[3];
        
        alt2D[0] = dir[2];
        alt2D[1] = 0;
        alt2D[2] = -dir[0];
        
        eye[0] -= alt2D[0] * (ld[0] / 100);
        eye[2] -= alt2D[2] * (ld[0] / 100);
        
        eye[0] -= dir2D[0] * (ld[1] / 100);
        eye[2] -= dir2D[2] * (ld[1] / 100);
      
        // Camera rotation
        xRot += rd[0] / 4000;
        yRot -= rd[1] / 4000;
        
        if (yRot >= (Math.PI / 2) - 0.001) {
            yRot = (float) ((Math.PI / 2) - 0.001);
        }
        else if (yRot <=  0.001 - (Math.PI / 2)) {
            yRot = (float) (0.001 - (Math.PI / 2));
        }
        
        float hx, hz;
        hx = (float) Math.cos(xRot);
        hz = (float) Math.sin(xRot);
        dir = new float[] {hx, 0, hz};
        
        float vx, vy;
        vx = (float) Math.cos(yRot);
        vy = (float) Math.sin(yRot);
        
        dir[0] *= vx;
        dir[2] *= vx;
        dir[1] = vy;
    }
    
    // User input
    @Override
    public boolean onTouch(View view, MotionEvent e) {
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            touchMove(e);
            break;
        case MotionEvent.ACTION_DOWN:
            touchDown(e);
            break;
        case MotionEvent.ACTION_UP:
            touchUp(e);
            break;
        }
        
        return true;
    }
    
    private void touchMove(MotionEvent e) {
        float x, y;
        
        for (int i = 0; i < e.getPointerCount(); i++) {
            x = e.getX(i);
            y = e.getY(i);
        
            if (e.getPointerId(i) == lJoy.pointerID) {
                lJoy.pos[0] = x;
                lJoy.pos[1] = y;
            }
            else if (e.getPointerId(i) == rJoy.pointerID ) {
                rJoy.pos[0] = x;
                rJoy.pos[1] = y;
            }
        }
    }
    
    private void touchDown(MotionEvent e) {
        float x, y;
        
        for (int i = 0; i < e.getPointerCount(); i++) {
            x = e.getX(i);
            y = e.getY(i);
            
            if (x < GameData.width / 2) {
                lJoy.start[0] = x;
                lJoy.start[1] = y;
                lJoy.pos[0] = x;
                lJoy.pos[1] = y;
                lJoy.on = true;
                lJoy.pointerID = e.getPointerId(i);
            }
            else
            {
                rJoy.start[0] = x;
                rJoy.start[1] = y;
                rJoy.pos[0] = x;
                rJoy.pos[1] = y;
                rJoy.on = true;
                rJoy.pointerID = e.getPointerId(i);
            }
        }
    }
    
    private void touchUp(MotionEvent e) {
        if (e.getPointerId(0) == lJoy.pointerID) {
            lJoy.on = false;
            lJoy.pointerID = -1;
        }
        else {
            rJoy.on = false;
            rJoy.pointerID = -1;
        }
        
        // Left joystick
        /*if (x < 50 && x < 50) {
            lJoy.pos = new float[] {x - lJoy.center[0], y - lJoy.center[1]};
        }
        // Right joystick
        else if (x > 125 && x < 175 && y < 50) {
            rJoy.pos = new float[] {x - rJoy.center[0], y - rJoy.center[1]};
        }
        
        lJoy.on = false;
        rJoy.on = false;*/
    }
}
