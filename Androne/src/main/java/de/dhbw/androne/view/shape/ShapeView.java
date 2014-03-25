package de.dhbw.androne.view.shape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import de.dhbw.androne.view.R;

public abstract class ShapeView extends View {

	protected Canvas canvas;
	protected Paint paint = new Paint();
	
	protected int width, height, lineDistance, midX, midY, verticalLineCount;
	protected int horizontalLineCount = 12;
	
	protected boolean directionRight = false;
	
	protected Bitmap droneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);		
	
	
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
		this.canvas = canvas;
		
		calculateDefaults();
		
		paint.setColor(Color.LTGRAY);
		paint.setStrokeWidth(3);

		drawGrid();
		
		paint.setColor(Color.rgb(51, 181, 229));
		paint.setStrokeWidth(6);
		paint.setStyle(Paint.Style.STROKE);
		
		drawShape();
		drawDrone();
		drawArrow();
		
		paint.setTextSize(lineDistance / 3 * 2);
		paint.setStyle(Paint.Style.FILL);
		drawValues();
	}
	
	private void calculateDefaults() {
		width = canvas.getWidth();
		height = canvas.getHeight();
		lineDistance = height / horizontalLineCount;
		verticalLineCount = width / lineDistance;
		midX = (verticalLineCount / 2) * lineDistance;
		midY = (horizontalLineCount / 2) * lineDistance;
	}
	
	
	private void drawGrid() {
		for(int x = 0; x < width; x += lineDistance) {
			canvas.drawLine(x, 0, x, (lineDistance * horizontalLineCount), paint);
		}
		
		for(int y = 0; y < height; y += lineDistance) {
			canvas.drawLine(0, y, (lineDistance * verticalLineCount), y, paint);
		}
	}
	
	
	public int getHorizontalLineCount() {
		return horizontalLineCount;
	}
	
	
	public int getVerticalLineCount() {
		return verticalLineCount;
	}

	
	public void setDirection(boolean directionRight) {
		this.directionRight = directionRight;
		this.invalidate();
	}
	
	
	protected abstract void drawShape();
	
	protected abstract void drawDrone();
	
	protected abstract void drawArrow();

	protected abstract void drawValues();

}
