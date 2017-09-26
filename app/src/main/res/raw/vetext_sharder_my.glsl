attribute vec4 a_Position;
attribute vec4 a_TextureCoordinates;
uniform mat4 u_TextureTransform;
varying vec2 v_TexCoordinate;

void main () {
    v_TexCoordinate = (u_TextureTransform * a_TextureCoordinates).xy;
    gl_Position = a_Position;
}