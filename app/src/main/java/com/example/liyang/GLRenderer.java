package com.example.liyang;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Environment;
import android.view.Surface;

import com.example.liyang.data.Mallet;
import com.example.liyang.data.Video;
import com.example.liyang.program.ColorShaderProgram;
import com.example.liyang.program.MediaShaderProgram;
import com.example.liyang.utils.MatrixHelper;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.translateM;

/**
 * Created by liyang on 2017/6/20.
 */

public class GLRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

//    public static final String videoPath = Environment.getExternalStorageDirectory().getPath()+"/DCIM/Video/2017_06_05_10_45_15-0.mp4";
    public static final String videoPath = Environment.getExternalStorageDirectory().getPath()+"/DCIM/Video/2017_06_05_10_45_15-0.mp4";

    private boolean frameAvailable = false;

    private Context context;
    private int[] textures = new int[1];

    private int width, height;

    private float[] videoTextureTransform = new float[16];
    private SurfaceTexture videoTexture;
    private MediaPlayer mediaPlayer;

    private MediaShaderProgram mMediaShaderProgram;
    private Video mVideo;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private ColorShaderProgram colorProgram;
    private Mallet mallet;


    public GLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        mVideo = new Video();
        mMediaShaderProgram = new MediaShaderProgram(context);

        mallet = new Mallet();
        colorProgram = new ColorShaderProgram(context);
        setupTexture();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);

        this.width = width;
        this.height = height;

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);

        Matrix.setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, 0f, 0f, -3f);
        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

        playVideo();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        synchronized (this) {
            if (frameAvailable) {
                videoTexture.updateTexImage();
                videoTexture.getTransformMatrix(videoTextureTransform);
                frameAvailable = false;
            }
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glViewport(0, 0, width, height);
        this.drawTexture();
    }

    private void playVideo() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            Surface surface = new Surface(videoTexture);
            mediaPlayer.setSurface(surface);
            surface.release();
            try {
                mediaPlayer.setDataSource(videoPath);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mediaPlayer.start();
        }
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (this) {
            frameAvailable = true;
        }
    }

    private void setupTexture() {
        videoTexture = new SurfaceTexture(textures[0]);
        videoTexture.setOnFrameAvailableListener(this);
    }

    private void drawTexture() {
        // Draw texture
        mMediaShaderProgram.useProgram();
        mMediaShaderProgram.setUniforms(videoTextureTransform, textures);
        mVideo.bindData(mMediaShaderProgram);
        mVideo.draw(mMediaShaderProgram);

        // Draw the mallets
        colorProgram.useProgram();
        colorProgram.setUniforms(projectionMatrix);
        mallet.bindData(colorProgram);
        mallet.draw();
    }
}
