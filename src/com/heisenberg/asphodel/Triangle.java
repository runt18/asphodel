package com.heisenberg.asphodel;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import android.opengl.GLES20;

public class Triangle {
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;

    private final float triCoords[] = {
        0, 0, 0, 0, 1, 0, 1, 0, 0,
        0, 1, 0, 1, 0, 0, 1, 1, 1
    };

    private final String vertexShaderCode =
        "attribute vec4 vPosition;" +
        "attribute vec4 vColor;" +
        "varying vec4 v_Color;" +
        "void main() {" +
        "  gl_Position = vPosition;" +
        "  v_Color = vColor;" +
        "}";

    private final String fragmentShaderCode =
        "precision mediump float;" +
        "varying vec4 v_Color;" +
        "void main() {" +
        "  gl_FragColor = v_Color;" +
        "}";

    int program;

    // number of coordinates per vertex in this array
    static final int BYTES_PER_FLOAT = 4;
    static final int FLOATS_PER_VERTEX = 3;
    static final int FLOATS_PER_COLOR = 4;
    static final int VERTICES_PER_TRIANGLE = 3;
    static final int TRIANGLES_PER_QUAD = 2;
    static final int NUM_QUADS = 10;

    static final int NUM_TRIANGLES = TRIANGLES_PER_QUAD * NUM_QUADS;
    static final int FLOATS_PER_TRIANGLE = FLOATS_PER_VERTEX * VERTICES_PER_TRIANGLE;
    static final int FLOATS_PER_QUAD = FLOATS_PER_TRIANGLE * TRIANGLES_PER_QUAD;
    static final int NUM_VERTICES = NUM_TRIANGLES * VERTICES_PER_TRIANGLE;

    static final int NUM_FLOATS = FLOATS_PER_TRIANGLE * NUM_TRIANGLES;
    static final int NUM_COLOR_FLOATS = FLOATS_PER_COLOR * NUM_VERTICES;

    static float triangleCoords[] = new float[NUM_FLOATS];
    static float colors[] = new float[NUM_COLOR_FLOATS];

    // Set color with red, green, blue and alpha (opacity) values
//    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public Triangle() {
        Random r = new Random();
        for(int i = 0; i < NUM_QUADS; i++){
            for(int j = 0; j < FLOATS_PER_QUAD; j++){
                float coord = triCoords[j];
                if(j % 3 == 0){
                    coord += i * 0.1f;
                }
                triangleCoords[i * FLOATS_PER_QUAD + j] = coord;
            }
        }

        for(int i = 0; i < NUM_COLOR_FLOATS; i++){
            if(i % 4 == 3){
                colors[i] = 1.0f;
            }
            else {
                colors[i] = r.nextFloat();
            }
        }

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(NUM_FLOATS * BYTES_PER_FLOAT);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb2 = ByteBuffer.allocateDirect(NUM_COLOR_FLOATS * BYTES_PER_FLOAT);
        // use the device hardware's native byte order
        bb2.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        colorBuffer = bb2.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        colorBuffer.put(colors);
        // set the buffer to read the first coordinate
        colorBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        program = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(program, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(program, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(program);                  // creates OpenGL ES program executables
    }

    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        // get handle to vertex shader's vPosition member
        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        int colorHandle = GLES20.glGetAttribLocation(program, "vColor");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glEnableVertexAttribArray(colorHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, FLOATS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(colorHandle, FLOATS_PER_COLOR, GLES20.GL_FLOAT, false, 0, colorBuffer);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, NUM_VERTICES);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
    }
}
