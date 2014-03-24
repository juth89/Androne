package de.dhbw.androne.view.shape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public abstract class ShapeView extends View {

	protected Canvas canvas;
	protected Paint paint;
	
	protected int width, height, lineDistance, midX, midY, verticalLineCount;
	protected int horizontalLineCount = 12;
	
	
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
		
		drawGrid();
		drawShape();
		drawValues();
		drawArrow();
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
		paint.setColor(Color.LTGRAY);
		paint.setStrokeWidth(3);

		for(int x = 0; x < width; x += lineDistance) {
			canvas.drawLine(x, 0, x, height, paint);
		}
		
		for(int y = 0; y < height; y += lineDistance) {
			canvas.drawLine(0, y, width, y, paint);
		}
	}
	
	
	protected abstract void drawShape();
	
	protected abstract void drawDrone();
	
	protected abstract void drawValues();
	
	protected abstract void drawArrow();
	
}
