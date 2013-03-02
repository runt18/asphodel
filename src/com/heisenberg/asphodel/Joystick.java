package com.heisenberg.asphodel;

public class Joystick {
    private Vector2 center;
    private Vector2 position;

    public Joystick(){
        Vector2 size = MyActivity.getScreenSize();
        this.center = size.multiply(0.75f);
        this.position = center;
    }

    public Vector2 getOffset(){
        return position.lerp(center, 1);
    }
}
