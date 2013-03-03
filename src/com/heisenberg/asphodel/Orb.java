package com.heisenberg.asphodel;

import java.util.Random;

public class Orb extends Actor {
    private float lifespan;
    private float start;

    public Orb(){
        super(R.raw.orb);
        this.lifespan = new Random().nextFloat() * 1000 * 10;
        this.start = System.currentTimeMillis();
    }

    public void update(){
        if(start + lifespan >= System.currentTimeMillis()){
            // die
        }
        super.update();
    }
}
