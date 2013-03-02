attribute vec4 vPosition;
attribute vec4 vColor;
varying vec4 v_Color;
void main() {
  gl_Position = vPosition;
  v_Color = vColor;
}