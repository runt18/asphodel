package com.heisenberg.asphodel;

public class Joystick {
    public float[] start = new float[2];
    public boolean on = false;
    public float radius = 25;
    public int pointerID = -1;
    
    public float[] pos = new float[2];
    
    public float[] getPos() {
        if (on) {
            float[] r = new float[2];
            r[0] = pos[0] - start[0];
            r[1] = pos[1] - start[1];
            return r;
        }
        else {
            return new float[] {0, 0};
        }
    }
}
