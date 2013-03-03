// Precision setting
precision mediump float;

// Interpolated color passed from vs
varying vec4 v_Color;

// Entry point
void main() {
  gl_FragColor = v_Color;
}
