package de.dhbw.androne.view.shape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View {

	private Paint paint = new Paint();

	public ShapeView(Context context) {
		super(context);
	}
	
	public ShapeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShapeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	public void onDraw(Canvas canvas) {
		System.out.println("Height: " + canvas.getHeight() + " Width: " + canvas.getWidth());
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		canvas.drawRect(30, 30, 80, 80, paint);
	}
}
