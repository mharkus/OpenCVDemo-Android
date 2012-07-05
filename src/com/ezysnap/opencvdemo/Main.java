package com.ezysnap.opencvdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	ListView menu = (ListView) findViewById(R.id.menu);
	menu
		.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,
			new String[] { "ImageROI", "Draw Line", "Draw Rectangle", "Draw Circle",
				"Pyramid Segmentation", "Blur", "Erode", "Dilate", "Morphology", "Threshold",
				"Adaptive Threshold", "Sobel", "Laplace" }));
	menu.setOnItemClickListener(new OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0: {
		    startActivity(new Intent(Main.this, ImageROIActivity.class));
		    break;
		}
		case 1: {
		    startActivity(new Intent(Main.this, DrawLineActivity.class));
		    break;
		}
		case 2: {
		    startActivity(new Intent(Main.this, DrawRectangleActivity.class));
		    break;
		}
		case 3: {
		    startActivity(new Intent(Main.this, DrawCircleActivity.class));
		    break;
		}
		case 4: {
		    startActivity(new Intent(Main.this, PyramidSegmentationActivity.class));
		    break;
		}
		case 5: {
		    startActivity(new Intent(Main.this, BlurActivity.class));
		    break;
		}
		case 6: {
		    startActivity(new Intent(Main.this, ErodeActivity.class));
		    break;
		}
		case 7: {
		    startActivity(new Intent(Main.this, DilateActivity.class));
		    break;
		}
		case 8: {
		    startActivity(new Intent(Main.this, MorphologyActivity.class));
		    break;
		}
		case 9: {
		    startActivity(new Intent(Main.this, ThresholdActivity.class));
		    break;
		}
		case 10: {
		    startActivity(new Intent(Main.this, AdaptiveThresholdActivity.class));
		    break;
		}
		case 11: {
		    startActivity(new Intent(Main.this, SobelActivity.class));
		    break;
		}
		case 12: {
		    startActivity(new Intent(Main.this, LaplaceActivity.class));
		    break;
		}
		}

	    }
	});

    }
}