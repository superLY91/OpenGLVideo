package com.example.liyang;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.liyang.recorder.GameRecorder;

/**
 * Created by liyang on 2017/6/20.
 */

public class MainActivity extends Activity {

    private GLRenderer mRenderer;
    private MyGLSurface mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new MyGLSurface(this);
        setContentView(mView);
        GameRecorder.getInstance().prepareEncoder(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mView.onPause();
    }

    class MyGLSurface extends GLSurfaceView {

        public MyGLSurface(Context context) {
            super(context);
            setEGLContextClientVersion(2);
            mRenderer = new GLRenderer(MainActivity.this);
            setRenderer(mRenderer);
        }
    }
}
