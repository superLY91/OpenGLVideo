package com.example.liyang.program;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.example.liyang.R;

/**
 * Created by liyang on 2017/9/5.
 */

public class MediaShaderProgram extends ShaderProgram {

    public final int textureParamHandle;
    public final int textureCoordinateHandle;
    public final int positionHandle;
    public final int textureTransformHandle;

    public MediaShaderProgram(Context context) {
        super(context, R.raw.vetext_sharder_my, R.raw.fragment_sharder_my);
        textureParamHandle = GLES20.glGetUniformLocation(program, U_TEXTURE);
        textureCoordinateHandle = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
        positionHandle = GLES20.glGetAttribLocation(program, A_POSITION);
        textureTransformHandle = GLES20.glGetUniformLocation(program, U_TEXTURE_TRANSFORM);
    }

    public void setUniforms(float[] matrix, int[] textures) {

        // Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(textureTransformHandle, 1, false, matrix, 0);

        // Generate the actual texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//        GLES20.glGenTextures(1, textures, 0);
        checkGlError("Texture generate");
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);

        GLES20.glUniform1i(textureParamHandle, 0);
    }

    public void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("SurfaceTest", op + ": glError " + GLUtils.getEGLErrorString(error));
        }
    }
}
