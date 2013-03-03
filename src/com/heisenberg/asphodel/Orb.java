package com.heisenberg.asphodel;

import java.util.Random;

import android.content.Context;
import android.os.Vibrator;

public class Orb extends Actor {
    private float lifespan;
    private float start;
    private boolean display = true;

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
        
        color = new float[] {r.nextFloat(),r.nextFloat(),r.nextFloat(),1.0f};
    }

    public void update(){
        if (display) {
            float a, b;
            float[] pos;
            pos = new float[3];
            
            pos[0] = matTranslate[12];
            pos[1] = matTranslate[13];
            pos[2] = matTranslate[14];
            
            a = pos[0] - GameData.player.eye[0];
            b = pos[2] - GameData.player.eye[2];
            
            if(Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)) < 40.0f) {
                // Stop displaying
                display = false;
                
                Vibrator v = (Vibrator) MyActivity.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
            }
        }
        super.update();
    }
    
    public void draw(DrawHelper dh) {
        if (display) {
            super.draw(dh);
        }
    }
}
