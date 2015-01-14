package com.szcmcc.movie.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class RoteTextView extends TextView {
	public RoteTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RoteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public RoteTextView(Context context) {
		super(context);

	}

	private int degree = 0;

	public void rotate(int degreeNum) {
		degree = degreeNum;
		invalidate();
	}

	private int height = 0;

	public void translate(int heightNum) {
		height = heightNum;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(0, height);
		canvas.rotate(degree);
		canvas.save();
		super.onDraw(canvas);
		canvas.restore();
	}
}
