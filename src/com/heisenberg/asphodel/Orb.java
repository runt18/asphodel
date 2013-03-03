package com.heisenberg.asphodel;

import java.util.Random;

public class Orb extends Actor {
    private float lifespan;
    private float start;

    public Orb(){
        super(R.raw.orb);
        this.lifespan = new Random().nextFloat() * 1000 * 10;
        this.start = System.currentTimeMillis();
        
        Random r = new Random();
        float x, y, z;
        x = r.nextFloat() - 0.5f;
        y = r.nextFloat();
        z = r.nextFloat() - 0.5f;
        translate(new float[] {x * 600, y * 20, z * 600});
        scale(0.3f);
    }

    public void update(){
        if(start + lifespan >= System.currentTimeMillis()){
            // die
        }
        super.update();
    }
}
