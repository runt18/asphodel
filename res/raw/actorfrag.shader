// Precision setting
precision mediump float;

// Interpolated color passed from vs
varying vec4 v_Color;
varying vec3 v_Normal;

uniform vec4 lDifCol;
uniform vec4 lAmbCol;
uniform vec3 lDir;

// Entry point
void main() {
    vec3 norm = normalize(v_Normal);
    float d = max(dot(lDir, norm), 0.0);
    vec4 litCol = (d * v_Color * lDifCol) + (lAmbCol * v_Color);
    gl_FragColor = litCol;//vec4(v_Normal, 1.0);
}
