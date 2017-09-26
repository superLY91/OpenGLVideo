package com.example.liyang.data;

import android.opengl.GLES20;

import com.example.liyang.program.MediaShaderProgram;

/**
 * Created by liyang on 2017/9/5.
 */

public class Video {

    private static float squareSize = 1.0f;
    private static float squareCoords[] = {
            -squareSize, squareSize,   // top left
            -squareSize, -squareSize,   // bottom left
            squareSize, -squareSize,    // bottom right
            squareSize, squareSize}; // top right

    private static short drawOrder[] = {0, 1, 2, 0, 2, 3};

    private float textureCoords[] = {
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f};

    private final VertexArray vertexBuffer;
    private final VertexShortArray drawListBuffer;
    private final VertexArray textureBuffer;

    public Video() {
        this.vertexBuffer = new VertexArray(squareCoords);
        this.textureBuffer = new VertexArray(textureCoords);
        this.drawListBuffer = new VertexShortArray(drawOrder);
    }


    public void bindData(MediaShaderProgram shaderProgram) {
        vertexBuffer.setVertexAttribPointer(0, shaderProgram.positionHandle, 2, 0);
        textureBuffer.setVertexAttribPointer(0, shaderProgram.textureCoordinateHandle, 4, 0);
    }

    public void draw(MediaShaderProgram shaderProgram) {
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer.getShortBuffer());
        GLES20.glDisableVertexAttribArray(shaderProgram.positionHandle);
        GLES20.glDisableVertexAttribArray(shaderProgram.textureCoordinateHandle);
    }
}
