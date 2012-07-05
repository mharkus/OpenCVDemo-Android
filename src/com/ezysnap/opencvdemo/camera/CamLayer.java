package com.ezysnap.opencvdemo.camera;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ezysnap.opencvdemo.Log;

public class CamLayer extends SurfaceView implements SurfaceHolder.Callback, PreviewCallback {
    public Camera mCamera;
    boolean isPreviewRunning = false;
    boolean isFocused;
    Camera.PreviewCallback callback;

    public static int WIDTH = 480;
    public static int HEIGHT = 320;

    int frameCounter;

    public CamLayer(Activity context, Camera.PreviewCallback callback) {
	super(context);
	this.callback = callback;

	SurfaceHolder mHolder = getHolder();
	mHolder.addCallback(this);
	mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
	synchronized (this) {
	    mCamera = Camera.open();

	}
    }

    

    public void surfaceDestroyed(SurfaceHolder holder) {
	// Surface will be destroyed when we return, so stop the preview.
	// Because the CameraDevice object is not a shared resource, it's very
	// important to release it when the activity is paused.
	synchronized (this) {
	    try {
		if (mCamera != null) {
		    mCamera.stopPreview();
		    isPreviewRunning = false;
		    mCamera.release();
		}
	    } catch (Exception e) {
		Log.v(e.getMessage());
	    }
	}
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	Camera.Parameters p = mCamera.getParameters();
	p.setPreviewSize(WIDTH, HEIGHT);
	//p.setPreviewFrameRate(25);
	mCamera.setParameters(p);

	try {
	    mCamera.setPreviewDisplay(holder);
	} catch (IOException e) {
	    Log.v("mCamera.setPreviewDisplay(holder);");
	}

	mCamera.startPreview();
	mCamera.setPreviewCallback(this);
	mCamera.autoFocus(new AutoFocusCallBackImpl());

    }

    public class AutoFocusCallBackImpl implements AutoFocusCallback {
	@Override
	public void onAutoFocus(boolean success, Camera camera) {
	    if (success) {
		isFocused = true;
	    } else {
		isFocused = false;
		camera.autoFocus(this);
	    }
	}
    }

    public void onPreviewFrame(byte[] arg0, Camera camera) {
	if (callback != null && isFocused) {
	    if (frameCounter % 2 == 0) {
		callback.onPreviewFrame(arg0, camera);
		if (frameCounter > 2 * 100) {
		    frameCounter = 0;
		}

	    }

	    frameCounter++;
	}
    }
}
