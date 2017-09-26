#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES u_Texture;
varying vec2 v_TexCoordinate;

void main () {
    vec4 color = texture2D(u_Texture, v_TexCoordinate);
    gl_FragColor = color;
}