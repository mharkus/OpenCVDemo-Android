package com.ezysnap.opencvdemo;

import java.io.File;
import java.util.concurrent.SynchronousQueue;

import org.siprop.opencv.OpenCV;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.ezysnap.opencvdemo.camera.CamLayer;

public class CopyOfMain extends Activity implements PreviewCallback {

    private CamLayer preview;
    private OpenCV mOpenCV;
    private SynchronousQueue<Bitmap> mSyncQueue;
    Handler handler;
    ImageView resultView;
    ImageView faceView;
    int[] reference;
    boolean isDetectionRunning = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	getWindow().setFormat(PixelFormat.TRANSLUCENT);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	handler = new Handler();
	preview = new CamLayer(this, this);
	mOpenCV = new OpenCV();
	mSyncQueue = new SynchronousQueue<Bitmap>();

	setContentView(preview);

	View resultLayer = getLayoutInflater().inflate(R.layout.result, null);
	resultView = (ImageView) resultLayer.findViewById(R.id.image);
	faceView = (ImageView) resultLayer.findViewById(R.id.face);
	LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	addContentView(resultLayer, params);
	
	//Bitmap refLogo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
	//reference = new int[refLogo.getWidth() * refLogo.getHeight()];
	//refLogo.getPixels(reference, 0, refLogo.getWidth(), 0, 0, refLogo.getWidth(), refLogo.getHeight());

    }

    @Override
    protected void onResume() {
	super.onResume();
	// addContentView(preview, new LayoutParams(LayoutParams.FILL_PARENT,
	// LayoutParams.FILL_PARENT));

	final File cascade_path = new File(Environment.getExternalStorageDirectory() + "/"
		+ "haarcascade_ipop_16_43.xml");
	Log.v(cascade_path.getAbsolutePath());

	if (!mOpenCV.initFaceDetection(cascade_path.getAbsolutePath())) {
	    Log.v("Failed to initialize face detection!");
	    return;
	}
	
	

	
    }

    @Override
    protected void onPause() {
	mOpenCV.releaseFaceDetection();
	super.onPause();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
	    preview.mCamera.autoFocus(new AutoFocusCallback() {
	        public void onAutoFocus(boolean success, Camera camera) {
	    	
	        }
	    });
        }
    
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
	
	//mOpenCV.setSourceImage(reference, CamLayer.WIDTH, CamLayer.HEIGHT);
	
	if(!isDetectionRunning){
	    
	isDetectionRunning = true;
	int[] rgb = new int[data.length];
	decodeYUV420SP(rgb, data, CamLayer.WIDTH, CamLayer.HEIGHT);

//	if (!mOpenCV.setSourceImage(rgb, CamLayer.WIDTH, CamLayer.HEIGHT)) {
//	    Log.v("Failed to set image");
//	}

//	data = mOpenCV.findContours(rgb, CamLayer.WIDTH, CamLayer.HEIGHT);
//	final Bitmap contour = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), 150, 150,
//		true);
	//byte[] response = mOpenCV.findAllFaces();
	//final Bitmap contour = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(response, 0, response.length), 150, 150, true);
	

	

	if (!mOpenCV.setSourceImage(rgb, CamLayer.WIDTH, CamLayer.HEIGHT)) {
	    Log.v("Failed to set image");
	}
	
	Rect[] faces = mOpenCV.findAllFaces();
	boolean faceDetected = false;
	if (faces != null && faces.length > 0) {
	    faceDetected = true;
	    for (int i = 0; i < faces.length; i++) {
		Log.v("Face #" + i + ": " + faces[i]);
		Rect face = faces[i];
		
		
	    }

	    
	    
//	    if (!mOpenCV.highlightFaces()) {
//		Log.v("Error occurred while highlighting the detected faces");
//	    }
	} else {
	    Log.v("No faces were detected");
	}

//	if (faceDetected) {
//	    byte[] faceData = mOpenCV.getSourceImage();
//	    final Bitmap face = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(faceData, 0, faceData.length), 150,
//		    150, true);
//
//	    handler.post(new Runnable() {
//		public void run() {
//		    faceView.setImageBitmap(face);
//		}
//	    });
//
//	}

	isDetectionRunning = false;
	
	}

    }

    public static int[] byte2int(byte[] src) {
	int dstLength = src.length >>> 2;
	int[] dst = new int[dstLength];

	for (int i = 0; i < dstLength; i++) {
	    int j = i << 2;
	    int x = 0;
	    x += (src[j++] & 0xff) << 0;
	    x += (src[j++] & 0xff) << 8;
	    x += (src[j++] & 0xff) << 16;
	    x += (src[j++] & 0xff) << 24;
	    dst[i] = x;
	}
	return dst;
    }

    public void decodeYUV420SP(int[] rgb, byte[] yuv420sp, int width, int height) {
	final int frameSize = width * height;

	for (int j = 0, yp = 0; j < height; j++) {
	    int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
	    for (int i = 0; i < width; i++, yp++) {
		int y = (0xff & ((int) yuv420sp[yp])) - 16;
		if (y < 0)
		    y = 0;
		if ((i & 1) == 0) {
		    v = (0xff & yuv420sp[uvp++]) - 128;
		    u = (0xff & yuv420sp[uvp++]) - 128;
		}

		int y1192 = 1192 * y;
		int r = (y1192 + 1634 * v);
		int g = (y1192 - 833 * v - 400 * u);
		int b = (y1192 + 2066 * u);

		if (r < 0)
		    r = 0;
		else if (r > 262143)
		    r = 262143;
		if (g < 0)
		    g = 0;
		else if (g > 262143)
		    g = 262143;
		if (b < 0)
		    b = 0;
		else if (b > 262143)
		    b = 262143;

		rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
	    }
	}
    }

}