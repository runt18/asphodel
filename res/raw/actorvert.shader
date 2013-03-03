// Buffers
attribute vec3 vPosition;
attribute vec3 vNormal;

// Parameters
uniform vec4 vColor;
uniform mat4 mvp_matrix;

// Output to pixel shader
varying vec4 v_Color;

void main() {
  vec4 pos = vec4(vPosition[0], vPosition[1], vPosition[2], 1.0);
  gl_Position = mvp_matrix * pos;
  v_Color = vColor;
}
