package com.heisenberg.asphodel;

public class Camera {
    Vector3 position;
    Vector3 velocity;
    Vector3 rotation;

    public Camera(){
        this.position = new Vector3();
        this.velocity = new Vector3();
        this.rotation = new Vector3();
    }

    public void update(){
        this.position = position.add(velocity);
    }
}
