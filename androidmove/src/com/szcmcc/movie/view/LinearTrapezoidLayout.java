package com.szcmcc.movie.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.szcmcc.movie.util.Rotate3dAnimation;
import com.szcmcc.movie.util.Transition3d;

public class LinearTrapezoidLayout extends LinearLayout {

	private static final String TAG = "LinearTrapezoidLayout";
	Rotate3dAnimation rotate3d;
    public LinearTrapezoidLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		 int width = getWidth()/2;
		 int height = getHeight()/2;
//		 Log.e(TAG,"width: "+width+"height:"+height);
//		rotate3d = new Rotate3dAnimation(-85, -15, 40, 60, 310.0f, false);
//		rotate3d.setFillAfter(true);
//		rotate3d.setDuration(2000);
//		startAnimation(rotate3d);
//		Transition3d.executeRotation(this,this, true, null);
		 Transition3d.executeTrapezoidLayout(this);
    }

	public LinearTrapezoidLayout(Context context) {
		super(context);
	}

	private int degree = 20;

	public void rotate(int degreeNum) {
		degree = degreeNum;
		invalidate();
	}

	private int height = 60;

	public void translate(int heightNum) {
		height = heightNum;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
//	    Log.e(TAG," onDraw height:"+height+", degree:"+degree);
//		matrix.postSkew(0.0f, 0.2f);
//		canvas.setMatrix(matrix);

//	    canvas.scale(sx, sy);//比例缩放
//		canvas.scale(sx, sy, px, py);
//		canvas.skew(0.0f, -0.2f);//倾斜
//		canvas.translate(5, 3.3f);//没起作用
//		canvas.rotate(5);//旋转
//		canvas.rotate(degrees, px, py);
//		canvas.setDensity(density);
		
		super.onDraw(canvas);

	}
}
