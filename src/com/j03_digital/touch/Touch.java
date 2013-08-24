package com.j03_digital.touch;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;


public class Touch extends Activity implements OnTouchListener{
	//--These matrices will be used to move and zoom image--
	Matrix matrix=new Matrix();
	Matrix savedMatrix=new Matrix();
	
	//--3 states--
	static final int NONE=0;
	static final int DRAG=1;
	static final int ZOOM=2;
	int mode=NONE;
	
	//--zooming data--
	PointF start=new PointF();
	PointF mid=new PointF();
	float oldDist=1f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);
		
		ImageView view=(ImageView)findViewById(R.id.imageView);
		view.setOnTouchListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event){
		ImageView view=(ImageView)v;
		
		//--handle touch events--
		switch(event.getAction()&MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(),event.getY());
			mode=DRAG;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode=NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if(mode==DRAG){
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX()-start.x, event.getY()-start.y);
			}
			break;
		}
		
		view.setImageMatrix(matrix);
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.touch, menu);
		return true;
	}

}
