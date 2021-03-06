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

public class DilateActivity extends Activity {
    private OpenCV mOpenCV;
    private Bitmap result;
    private Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dilate);

	ImageView orig = (ImageView) findViewById(R.id.orig);
	final ImageView processed = (ImageView) findViewById(R.id.after);
	Button blurButton = (Button) findViewById(R.id.blurButton);
	Button resetButton = (Button) findViewById(R.id.resetButton);

	pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
	orig.setImageBitmap(pic);

	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	pic.compress(CompressFormat.PNG, 100, bos);

	mOpenCV = new OpenCV();
	int[] pixels = new int[pic.getWidth() * pic.getHeight()];
	pic.getPixels(pixels, 0, pic.getWidth(), 0, 0, pic.getWidth(), pic.getHeight());
	mOpenCV.setSourceImage(pixels, pic.getWidth(), pic.getHeight());
	byte[] resultByte = mOpenCV.dilate();

	result = BitmapFactory.decodeByteArray(resultByte, 0, resultByte.length);
	processed.setImageBitmap(result);

	blurButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		int[] pixels = new int[result.getWidth() * result.getHeight()];
		result.getPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());

		mOpenCV.setSourceImage(pixels, result.getWidth(), result.getHeight());
		byte[] resultByte = mOpenCV.dilate();
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
