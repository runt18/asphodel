package com.heisenberg.asphodel;

import java.nio.IntBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.opengl.GLSurfaceView.Renderer;

public class GLRenderer implements Renderer {
    // Shaders
    private final String vertexShaderCode =
            "attribute vec3 vPosition;" +
            "attribute vec3 vNormal;" +
            "uniform vec4 vColor;" +
            "uniform mat4 mvp_matrix;" +
            "varying vec4 v_Color;" +
            "void main() {" +
            "  vec4 pos = vec4(vPosition[0], vPosition[1], vPosition[2], 1.0);" +
            "  gl_Position = mvp_matrix * pos;" +
            "  v_Color = vColor;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "varying vec4 v_Color;" +
            "void main() {" +
            "  gl_FragColor = v_Color;" +
            "}";
    
    private DrawHelper mDh;
    
    private Triangle tri;
    public float ox = 0.f;
    public float oy = 0.f;
    
    // View & projection matrices
    float[] matView, matProj;

    public GLRenderer() {
        matView = new float[16];
        matProj = new float[16];
        Matrix.setLookAtM(matView, 0, 0, 50, -120, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        // Blank background
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        
        /*tri.modelViewMatrix[12] = ox;
        tri.modelViewMatrix[13] = oy;
        tri.draw();*/
        
        // Let's just draw one actor, yeah?
        Actor actor = GameData.getActor(0);
        
        // Setup projection & view
//        Matrix.perspectiveM(matProj, 0, 45.0f, 1.0f, 0.1f, 1000.0f);
        /*Matrix.setLookAtM(  matView, 0,
                            0.0f, 0.0f, -5.0f,
                            0.0f, 0.0f, 0.0f,
                            0.0f, 1.0f, 0.0f);*/

        float[] matVP = new float[16];
        Matrix.multiplyMM(matVP, 0, matProj, 0, matView, 0);
        
        // Use our shaders
        GLES20.glUseProgram(mDh.program);
        
        // Enable data arrays
        GLES20.glEnableVertexAttribArray(mDh.vertexHandle);
        GLES20.glEnableVertexAttribArray(mDh.normalHandle);
        GLES20.glEnableVertexAttribArray(mDh.colorHandle);
        GLES20.glEnableVertexAttribArray(mDh.matrixHandle);
        
        /*matVP = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
            };*/
        
        // Set VP matrix
        mDh.matVP = matVP;
        
        // Actor draws its own triangles
        actor.draw(mDh);
        
        // Disable data arrays
        GLES20.glDisableVertexAttribArray(mDh.vertexHandle);
        GLES20.glDisableVertexAttribArray(mDh.normalHandle);
        GLES20.glDisableVertexAttribArray(mDh.colorHandle);
        GLES20.glDisableVertexAttribArray(mDh.matrixHandle);
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
            throw new Error("Shader compilation failed: "+str);
        }

        return shader;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float)width / (float)height;
        Matrix.perspectiveM(matProj, 0, 45.0f, ratio, 0.1f, 1000.0f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        mDh = new DrawHelper();
        
        // General rendering settings
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        tri = new Triangle();
        
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
        mDh.matrixHandle = GLES20.glGetUniformLocation(mDh.program, "mvp_matrix");
    }

}
