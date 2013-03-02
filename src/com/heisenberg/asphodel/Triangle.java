package com.heisenberg.asphodel;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import android.opengl.GLES20;
import static com.heisenberg.asphodel.RenderConstants.*;
import android.opengl.Matrix;

public class Triangle {
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;

    public float[] rotationMatrix;
    public float[] translationMatrix;
    public float[] modelViewMatrix;

    private static final float coords[] = {
        0, 0, 0, 0, 1, 0, 1, 0, 0,
        0, 1, 0, 1, 0, 0, 1, 1, 1
    };

    // TODO: load from external files
    private final String vertexShaderCode =
        "attribute vec4 vPosition;" +
        "attribute vec4 vColor;" +
        "varying vec4 v_Color;" +
        "uniform mat4 mvp_matrix;" +
        "void main() {" +
        "  gl_Position = mvp_matrix * vPosition;" +
        "  v_Color = vColor;" +
        "}";

    private final String fragmentShaderCode =
        "precision mediump float;" +
        "varying vec4 v_Color;" +
        "void main() {" +
        "  gl_FragColor = v_Color;" +
        "}";

    int program;

    static float vertices[] = new float[NUM_FLOATS];
    static float colors[] = new float[NUM_COLOR_FLOATS];

    public Triangle() {
        Random r = new Random();
        for(int i = 0; i < WORLD_SIZE; i++){
            for(int j = 0; j < WORLD_SIZE; j++){
                for(int k = 0; k < FLOATS_PER_QUAD; k++){
                    float coord = coords[k];
                    if(k % 3 == 1){
                        coord += i;
                    }
//                    else if(k % 3 == 0){
//                        coord += j;
//                    }
                    vertices[i * FLOATS_PER_QUAD * WORLD_SIZE + j + FLOATS_PER_QUAD + k] = coord;
                }
            }
        }

        int k = 0;
        for(int i = 0; i < NUM_QUADS; i++){
            float[] color = new float[]{r.nextFloat(), r.nextFloat(), r.nextFloat()};
            for(int j = 0; j < VERTICES_PER_QUAD; j++){
                colors[k] = color[0];
                colors[k + 1] = color[1];
                colors[k + 2] = color[2];
                colors[k + 3] = 1.0f;
                k += 4;
            }
        }

        vertexBuffer = allocateBuffer(vertices, NUM_FLOATS);
        colorBuffer = allocateBuffer(colors, NUM_COLOR_FLOATS);
        modelViewMatrix = new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        };

//        Matrix.setRotateM(modelViewMatrix, 0, 3.14f / 4, 1, 0, 0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        program = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(program, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(program, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(program);                  // creates OpenGL ES program executables
    }

    private FloatBuffer allocateBuffer(float[] rawData, int size){
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb2 = ByteBuffer.allocateDirect(size * BYTES_PER_FLOAT);
        // use the device hardware's native byte order
        bb2.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        FloatBuffer buffer = bb2.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        buffer.put(rawData);
        // set the buffer to read the first coordinate
        buffer.position(0);

        return buffer;
    }

    private int loadShader(int type, String shaderCode){
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

//        Matrix.multiplyMM(modelViewMatrix, 0, translationMatrix, 0, modelViewMatrix, 0);

        // Get handles for shader data
        int vertexHandle = GLES20.glGetAttribLocation(program, "vPosition");
        int colorHandle = GLES20.glGetAttribLocation(program, "vColor");
        int matrixHandle = GLES20.glGetUniformLocation(program, "mvp_matrix");

        // Enable data arrays
        GLES20.glEnableVertexAttribArray(vertexHandle);
        GLES20.glEnableVertexAttribArray(colorHandle);
        GLES20.glEnableVertexAttribArray(matrixHandle);

        // Prepare the triangle data
        GLES20.glVertexAttribPointer(vertexHandle, FLOATS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(colorHandle, FLOATS_PER_COLOR, GLES20.GL_FLOAT, false, 0, colorBuffer);
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, modelViewMatrix, 0);

        // Draw the triangles
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, NUM_VERTICES);

        // Disable data arrays
        GLES20.glDisableVertexAttribArray(vertexHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
        GLES20.glDisableVertexAttribArray(matrixHandle);
    }
}
