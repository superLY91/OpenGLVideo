package com.example.liyang.data;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.glEnableVertexAttribArray;
import static com.example.liyang.Constants.BYTES_PER_FLOAT;
import static com.example.liyang.Constants.BYTES_PER_SHORT;

/**
 * Created by liyang on 2017/6/27.
 */

public class VertexShortArray {

    private final ShortBuffer shortBuffer;

    public VertexShortArray(short[] vertexData) {
        // First we allocated a block of native memory using ByteBuffer.allocateDirect();
        // this memory will not be managed by the garbage collector.
        // We need to tell the method how large the block of memory should be in bytes.
        // We then copy data from Dalvik’s memory to native memory by calling vertexData.put(tableVerticesWithTriangles).
        shortBuffer = ByteBuffer
                .allocateDirect(vertexData.length * BYTES_PER_SHORT)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(vertexData);
        shortBuffer.position(0);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        shortBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_SHORT, false, stride, shortBuffer);
        glEnableVertexAttribArray(attributeLocation);
        shortBuffer.position(0);
    }

    public ShortBuffer getShortBuffer() {
        return shortBuffer;
    }
}
