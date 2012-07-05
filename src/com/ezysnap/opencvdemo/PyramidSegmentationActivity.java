package com.ezysnap.opencvdemo;

import java.io.ByteArrayOutputStream;

import org.siprop.opencv.OpenCV;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PyramidSegmentationActivity extends Activity {
    private OpenCV mOpenCV;
    private Bitmap result;
    private Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.pyramidsegmentation);

	ImageView orig = (ImageView) findViewById(R.id.orig);
	final ImageView processed = (ImageView) findViewById(R.id.after);
	Button button = (Button) findViewById(R.id.button);
	Button resetButton = (Button) findViewById(R.id.resetButton);

	pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic2);
	orig.setImageBitmap(pic);

	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	pic.compress(CompressFormat.PNG, 100, bos);

	mOpenCV = new OpenCV();
	result = pic;

	button.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		int[] pixels = new int[result.getWidth() * result.getHeight()];
		result.getPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
 
		mOpenCV.setSourceImage(pixels, result.getWidth(), result.getHeight());
		byte[] resultByte = mOpenCV.pyramidSegmentation();
		result = BitmapFactory.decodeByteArray(resultByte, 0, resultByte.length);

		processed.setImageBitmap(result);
	    }
	});

	resetButton.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		processed.setImageBitmap(pic);
		result = pic;
	    }
	});
    }
}
