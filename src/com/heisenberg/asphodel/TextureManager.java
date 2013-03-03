package com.heisenberg.asphodel;

import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

/**
 * Texture manager - responsible for loading and removing textures for the game and/or menu.
 * Stores with a hash map for easy access.
 * 
 * @author Ali
 *
 */
public class TextureManager {
    private static HashMap<String, Texture> textures;
    
    /**
     * Must be called once for initialisation
     */
    public static void init() {
        textures = new HashMap<String, Texture>();
    }
    
    /**
     * Loads a new texture from resources
     */
    public static Texture loadTexture(int resID, String name) {
        int[] handlePtr = new int[1];
        GLES20.glGenTextures(1, handlePtr, 0);
        
        if (handlePtr[0] == 0) {
            throw new RuntimeException("Error generating texture");
        }
        
        // Setup for bmp load
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        
        // Load the bitmap from resources
        MyActivity act = MyActivity.curActivity;
        Resources res = act.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, resID);
        
        // GL bind the texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handlePtr[0]);
        
        // Set filtering mode
        GLES20.glTexParameteri(handlePtr[0], GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(handlePtr[0], GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        
        // Load the bitmap
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
        bmp.recycle();
        
        if (handlePtr[0] == 0) {
            throw new RuntimeException("Error loading texture");
        }
        
        // Store for management
        Texture tex = new Texture(name, handlePtr[0]);
        textures.put(name, tex);
        
        return tex;
    }
}
