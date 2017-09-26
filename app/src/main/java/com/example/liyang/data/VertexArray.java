package com.example.liyang.data;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.glEnableVertexAttribArray;
import static com.example.liyang.Constants.BYTES_PER_FLOAT;

/**
 * Created by liyang on 2017/6/27.
 */

public class VertexArray {

    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData) {
        // First we allocated a block of native memory using ByteBuffer.allocateDirect();
        // this memory will not be managed by the garbage collector.
        // We need to tell the method how large the block of memory should be in bytes.
        // We then copy data from Dalvik’s memory to native memory by calling vertexData.put(tableVerticesWithTriangles).
        floatBuffer = ByteBuffer
                .allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        floatBuffer.position(dataOffset);
        glEnableVertexAttribArray(attributeLocation);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, floatBuffer);
        floatBuffer.position(0);
    }
}
