package com.example.dahae.myandroiice.Actions;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

class ActionForCameraSurface extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera = null;

    public ActionForCameraSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if(mCamera == null) {
                mCamera = Camera.open();
                mCamera.setPreviewDisplay(mHolder);
            }
        } catch (Exception e) {

        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }
}