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

public class MorphologyActivity extends Activity implements OnClickListener {
    private OpenCV mOpenCV;
    private Bitmap result;
    private Bitmap pic;
    private ImageView processed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.morphology);

	ImageView orig = (ImageView) findViewById(R.id.orig);
	processed = (ImageView) findViewById(R.id.after);
	Button openButton = (Button) findViewById(R.id.openButton);
	Button closeButton = (Button) findViewById(R.id.closeButton);
	Button gradientButton = (Button) findViewById(R.id.gradientButton);
	Button topHatButton = (Button) findViewById(R.id.topHatButton);
	Button blackHatButton = (Button) findViewById(R.id.blackHatButton);
	Button resetButton = (Button) findViewById(R.id.resetButton);

	pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
	orig.setImageBitmap(pic);

	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	pic.compress(CompressFormat.PNG, 100, bos);

	mOpenCV = new OpenCV();
	
	result = pic;
	
	openButton.setOnClickListener(this);
	closeButton.setOnClickListener(this);
	gradientButton.setOnClickListener(this);
	topHatButton.setOnClickListener(this);
	blackHatButton.setOnClickListener(this);
	
	processed.setImageBitmap(pic);
	
	resetButton.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		processed.setImageBitmap(pic);
		result = pic;
	    }
	});
    }

    @Override
    public void onClick(View v) {
	int[] pixels = new int[result.getWidth() * result.getHeight()];
	result.getPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());

	mOpenCV.setSourceImage(pixels, result.getWidth(), result.getHeight());
	byte[] resultByte = null;
	
	if(v.getId() == R.id.openButton){
	    resultByte = mOpenCV.morphologyexOpen();
	}else if(v.getId() == R.id.closeButton){
	    resultByte = mOpenCV.morphologyexClose();
	}else if(v.getId() == R.id.gradientButton){
	    resultByte = mOpenCV.morphologyexGradient();
	}else if(v.getId() == R.id.topHatButton){
	    resultByte = mOpenCV.morphologyexTopHat();
	}else if(v.getId() == R.id.blackHatButton){
	    resultByte = mOpenCV.morphologyexBlackHat();
	} 
	
	result = BitmapFactory.decodeByteArray(resultByte, 0, resultByte.length);
	processed.setImageBitmap(result);
    }

}
