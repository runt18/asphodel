package com.heisenberg.asphodel;

public class RenderConstants {
    static final int BYTES_PER_FLOAT = 4;
    static final int FLOATS_PER_VERTEX = 3;
    static final int FLOATS_PER_COLOR = 4;
    static final int VERTICES_PER_TRIANGLE = 3;
    static final int TRIANGLES_PER_QUAD = 2;
    static final int WORLD_SIZE = 10;

    static final int NUM_QUADS = WORLD_SIZE * WORLD_SIZE;
    static final int NUM_TRIANGLES = TRIANGLES_PER_QUAD * NUM_QUADS;

    static final int VERTICES_PER_QUAD = VERTICES_PER_TRIANGLE * TRIANGLES_PER_QUAD;
    static final int FLOATS_PER_TRIANGLE = FLOATS_PER_VERTEX * VERTICES_PER_TRIANGLE;
    static final int FLOATS_PER_QUAD = FLOATS_PER_TRIANGLE * TRIANGLES_PER_QUAD;
    static final int NUM_VERTICES = NUM_TRIANGLES * VERTICES_PER_TRIANGLE;

    static final int NUM_FLOATS = FLOATS_PER_TRIANGLE * NUM_TRIANGLES;
    static final int NUM_COLOR_FLOATS = FLOATS_PER_COLOR * NUM_VERTICES;
}
