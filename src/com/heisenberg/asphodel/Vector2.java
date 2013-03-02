package com.heisenberg.asphodel;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2(){
        this(0, 0);
    }

    public Vector2 add(Vector2 v){
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 multiply(float n){
        return new Vector2(x * n, y * n);
    }

    public Vector2 negate(){
        return this.multiply(-1);
    }

    public Vector2 sub(Vector2 v){
        return this.add(v.negate());
    }

    public Vector2 lerp(Vector2 v, float n){
        return new Vector2(x + n * (v.x - x), y + n * (v.y - y));
    }
}
