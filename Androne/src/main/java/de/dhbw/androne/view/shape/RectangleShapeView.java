package de.dhbw.androne.view.shape;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.util.AttributeSet;

public class RectangleShapeView extends ShapeView {

	private int rectangleWidth = 5;
	private int rectangleHeight = 5;
	
	
	public RectangleShapeView(Context context) {
		super(context);
	}
	
	public RectangleShapeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public RectangleShapeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	protected void drawShape() {
		int startX = midX - ((rectangleWidth / 2) * lineDistance);
		int stopX = midX + ((rectangleWidth - (rectangleWidth / 2)) * lineDistance);
		int startY = midY - ((rectangleHeight / 2) * lineDistance);
		int stopY = midY + ((rectangleHeight - (rectangleHeight / 2)) * lineDistance);
		
		canvas.drawRect(new Rect(startX, startY, stopX, stopY), paint);
	}

	
	@Override
	protected void drawDrone() {
		int x = midX + ((rectangleWidth - (rectangleWidth / 2)) * lineDistance) - (droneBitmap.getWidth() / 2);
		int y = midY + ((rectangleHeight - (rectangleHeight / 2)) * lineDistance) - (droneBitmap.getHeight() / 2);
		
		canvas.drawBitmap(droneBitmap, x, y, null);
	}

	
	@Override
	protected void drawValues() {
		paint.setTextAlign(Align.CENTER);
		
		int x = midX;
		int y = midY - ((rectangleHeight / 2) * lineDistance) - (lineDistance / 4);
		
		if((rectangleWidth % 2) == 1) {
			x = x + (lineDistance / 2);
		}
		
		if(rectangleWidth == 2 && rectangleHeight <= 3) {
			x = x + lineDistance;
		}
		
		String widthText = "Width: " + rectangleWidth + " m";
		
		canvas.drawText(widthText, x, y, paint);
		
		x = midX - ((rectangleWidth / 2) * lineDistance) - (lineDistance / 4);
		y = midY;
		
		if((rectangleHeight % 2) == 1) {
			y = y + (lineDistance / 2);
		}

		String heightText = "Height: " + rectangleHeight + " m";
		
		canvas.rotate(-90, x, y);
		canvas.drawText(heightText, x, y, paint);
	}

	
	@Override
	protected void drawArrow() {
		int p1x = midX + ((rectangleWidth - (rectangleWidth / 2)) * lineDistance);
		int p1y = midY + ((rectangleHeight - (rectangleHeight / 2)) * lineDistance) - (lineDistance * 5 / 3);
		
		int p2x = midX + ((rectangleWidth - (rectangleWidth / 2)) * lineDistance) - (lineDistance * 1 / 3);
		int p2y = midY + ((rectangleHeight - (rectangleHeight / 2)) * lineDistance) - lineDistance * 4 / 3;
		
		int p3x = midX + ((rectangleWidth - (rectangleWidth / 2)) * lineDistance) + (lineDistance * 1 / 3);
		
		if(directionRight) {
			p1x = p1x - (lineDistance * 5 / 3);
			p1y = midY + ((rectangleHeight - (rectangleHeight / 2)) * lineDistance);
			
			p2x = p1x + (lineDistance / 3);
			p2y = p1y - (lineDistance / 3);
			
			int p3y = p1y + (lineDistance / 3);
			
			canvas.drawLine(p1x, p1y, p2x, p2y, paint);
			canvas.drawLine(p1x, p1y, p2x, p3y, paint);
		} else {
			canvas.drawLine(p1x, p1y, p2x, p2y, paint);
			canvas.drawLine(p1x, p1y, p3x, p2y, paint);
		}
		
	}

	
	public int getRectangleWidth() {
		return rectangleWidth;
	}
	
	public int getRectangleHeight() {
		return rectangleHeight;
	}
	
	public void setRectangle(int width, int height) {
		this.rectangleWidth = width;
		this.rectangleHeight = height;
		this.invalidate();
	}
}
