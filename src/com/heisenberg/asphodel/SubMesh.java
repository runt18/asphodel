package com.heisenberg.asphodel;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class SubMesh {
    
    private int indexCount;
    
    // Buffers
    private FloatBuffer vb;
    private FloatBuffer nb;
    private ShortBuffer ib;
    
    public void setVertexBuffer(FloatBuffer vb) {
        this.vb = vb;
    }
    
    public void setIndexBuffer(ShortBuffer ib) {
        this.ib = ib;
    }
    
    public void setNormalBuffer(FloatBuffer nb) {
        this.nb = nb;
    }
    
    public void setIndexCount(int count) {
        indexCount = count;
    }

    public void draw(DrawHelper dh) {
        // Set the color
        GLES20.glUniform4f(dh.colorHandle, 1.0f, 0.5f, 0.5f, 1.0f);
        
        // Send in vertex positions
        vb.position(0);
        GLES20.glVertexAttribPointer(dh.vertexHandle, 3, GLES20.GL_FLOAT, false, 0, vb);

        // Send in normals
        nb.position(0);
        GLES20.glVertexAttribPointer(dh.normalHandle, 3, GLES20.GL_FLOAT, false, 0, nb);
        
        ib.position(0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexCount, GLES20.GL_UNSIGNED_SHORT, ib);
    }
    
}
