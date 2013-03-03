package com.heisenberg.asphodel;

import java.nio.IntBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;
import android.opengl.Matrix;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.opengl.GLSurfaceView.Renderer;

public class GLRenderer implements Renderer {
    // Shaders
    private final String vertexShaderCode = GLView.getShader(R.raw.actorvert);
    private final String fragmentShaderCode = GLView.getShader(R.raw.actorfrag);
    
    private DrawHelper mDh;
    
    private Triangle tri;
    public float ox = 0.f;
    public float oy = 0.f;
    
    // View & projection matrices
    float[] matView, matProj;
    
    // Lighting
    DirLight sun;

    public GLRenderer() {
        matView = new float[16];
        matProj = new float[16];
<<<<<<< HEAD
        Matrix.setLookAtM(matView, 0, 0, 50, -120, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
=======
        
        sun = new DirLight();
        sun.diffuseCol = new float[] {1.0f, 1.0f, 0.9f, 1.0f};
        sun.ambientCol = new float[] {0.2f, 0.2f, 0.2f, 1.0f};
        sun.dir = new float[] {1.0f, 0.5f, -1.0f};
>>>>>>> e9643108a6c52ab47f922c0eb47216920383e5de
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        // Update the game
        GameData.doUpdate();
        
        // Blank background
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        
        /*tri.modelViewMatrix[12] = ox;
        tri.modelViewMatrix[13] = oy;
        tri.draw();*/
        
        // Draw all the actors
        
<<<<<<< HEAD
        // Setup projection & view
//        Matrix.perspectiveM(matProj, 0, 45.0f, 1.0f, 0.1f, 1000.0f);
        /*Matrix.setLookAtM(  matView, 0,
                            0.0f, 0.0f, -5.0f,
                            0.0f, 0.0f, 0.0f,
                            0.0f, 1.0f, 0.0f);*/

=======
        // Setup view from player
        float[] eye = GameData.player.eye;
        float[] dir = GameData.player.dir;
        
        float[] targ = new float[] {eye[0]+dir[0],eye[1]+dir[1],eye[2]+dir[2]};
        
        Matrix.setLookAtM(matView, 0, eye[0], eye[1], eye[2], targ[0], targ[1], targ[2], 0f, 1.0f, 0.0f);
>>>>>>> e9643108a6c52ab47f922c0eb47216920383e5de
        float[] matVP = new float[16];
        Matrix.multiplyMM(matVP, 0, matProj, 0, matView, 0);
        
        // Use our shaders
        GLES20.glUseProgram(mDh.program);
        
        // Enable data arrays
        GLES20.glEnableVertexAttribArray(mDh.vertexHandle);
        GLES20.glEnableVertexAttribArray(mDh.normalHandle);
        
        /*matVP = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
            };*/
        
        // Set VP matrix
        mDh.matVP = matVP;
        
        // Set lighting
        GLES20.glUniform4f( mDh.lDifColHandle,
                            sun.diffuseCol[0],
                            sun.diffuseCol[1],
                            sun.diffuseCol[2],
                            sun.diffuseCol[3]);
        GLES20.glUniform4f( mDh.lAmbColHandle,
                            sun.ambientCol[0],
                            sun.ambientCol[1],
                            sun.ambientCol[2],
                            sun.ambientCol[3]);
        GLES20.glUniform3f( mDh.lDirHandle,
                            sun.dir[0],
                            sun.dir[1],
                            sun.dir[2]);
        
        // Actors draw their own triangles
        ArrayList<Actor> actors = GameData.getActors();
        
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).draw(mDh);
        }
        
        // Disable data arrays
        GLES20.glDisableVertexAttribArray(mDh.vertexHandle);
        GLES20.glDisableVertexAttribArray(mDh.normalHandle);
    }
    
    /**
     * Giles' shader loader
     * 
     * @param type
     * @param shaderCode
     * @return
     */
    private int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        IntBuffer result = IntBuffer.allocate(1);
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, result);
        
        if (result.get(0) == GLES20.GL_FALSE) {
            // Dump information and stop
            String str = GLES20.glGetShaderInfoLog(shader);
            String err = "Shader Compilation FAILED\n"+str+"\n"+shaderCode;
            throw new Error(err);
        }

        return shader;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        
        GameData.width = width;
        GameData.height = height;

        float ratio = (float)width / (float)height;
        Matrix.perspectiveM(matProj, 0, 45.0f, ratio, 0.1f, 1000.0f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        mDh = new DrawHelper();
        
        // General rendering settings
        GLES20.glClearColor(0.8f, 0.8f, 1.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        
        GLES20.glEnable( GLES20.GL_DEPTH_TEST );
        GLES20.glDepthFunc( GLES20.GL_LEQUAL );
        GLES20.glDepthMask( true );
        
        // Load the vertex and fragment shaders
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        
        // Create an empty OpenGL ES Program
        mDh.program = GLES20.glCreateProgram();
        
        // Add the shaders to the program
        GLES20.glAttachShader(mDh.program, vertexShader);
        GLES20.glAttachShader(mDh.program, fragmentShader);
        
        // Create the OpenGL ES program executables
        GLES20.glLinkProgram(mDh.program);
        
        // Get handles for shader data
        mDh.vertexHandle = GLES20.glGetAttribLocation(mDh.program, "vPosition");
        mDh.normalHandle = GLES20.glGetAttribLocation(mDh.program, "vNormal");
        
        mDh.colorHandle = GLES20.glGetUniformLocation(mDh.program, "vColor");
        mDh.wvpHandle = GLES20.glGetUniformLocation(mDh.program, "matWVP");
        mDh.witHandle = GLES20.glGetUniformLocation(mDh.program, "matWIT");
        
        mDh.lDifColHandle = GLES20.glGetUniformLocation(mDh.program, "lDifCol");
        mDh.lAmbColHandle= GLES20.glGetUniformLocation(mDh.program, "lAmbCol");
        mDh.lDirHandle = GLES20.glGetUniformLocation(mDh.program, "lDir");
    }

}
