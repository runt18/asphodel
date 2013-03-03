package com.heisenberg.asphodel;

public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(){
        this(0, 0, 0);
    }

    public Vector3 add(Vector3 v){
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }
}
