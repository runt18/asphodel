// Buffers
attribute vec3 vPosition;
attribute vec3 vNormal;

// Parameters
uniform vec4 vColor;

uniform mat4 matWVP;
uniform mat4 matWIT;
//uniform mat4 matWorld;


// Output to pixel shader
varying vec4 v_Color;
varying vec3 v_Normal;

void main() {
    vec4 pos = vec4(vPosition, 1.0);
    gl_Position = matWVP * pos;
  
     vec3 norm = (matWIT * vec4(vNormal, 0.0)).xyz;
  
    // Output to frag shader
    v_Color = vColor;
    v_Normal = norm;
}
