package com.heisenberg.asphodel;

public class Joystick {
<<<<<<< HEAD
    private Vector2 center;
    private Vector2 position;

    public Joystick(Vector2 size){
        this.center = size.multiply(0.75f);
        this.position = center;
    }

    public Vector2 getOffset(){
        return position.lerp(center, 1);
    }

    public void update(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }
=======
    
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
    
>>>>>>> e9643108a6c52ab47f922c0eb47216920383e5de
}
