package de.dhbw.androne.view.shape;

import android.content.Context;
import android.util.AttributeSet;

public class TriangleShapeView extends ShapeView {
	
	private int triangleWidth = 5;
	private int triangleHeight = 5;
	

	public TriangleShapeView(Context context) {
		super(context);
	}

	public TriangleShapeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public TriangleShapeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	protected void drawShape() {
		int startX = midX - ((triangleWidth / 2) * lineDistance);
		int startY = midY + ((triangleHeight - (triangleHeight / 2)) * lineDistance);
		int stopX = midX + ((triangleWidth - (triangleWidth / 2)) * lineDistance);
		int stopY = startY;
		
		canvas.drawLine(startX, startY, stopX, stopY, paint);
		
		stopX = (startX + stopX) / 2;
		stopY = stopY - (triangleHeight * lineDistance);
		
		canvas.drawLine(startX, startY, stopX, stopY, paint);

		startX = midX + ((triangleWidth - (triangleWidth / 2)) * lineDistance);
		
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	
	@Override
	protected void drawDrone() {
		int x = midX - ((triangleWidth / 2) * lineDistance) - (droneBitmap.getWidth() / 2);
		int y = midY + ((triangleHeight - (triangleHeight / 2)) * lineDistance) - (droneBitmap.getHeight() / 2);
		
		if(directionRight) {
			x = midX + ((triangleWidth - (triangleWidth / 2)) * lineDistance) - (droneBitmap.getWidth() / 2);
		}
		
		canvas.drawBitmap(droneBitmap, x, y, null);
	}

	
	@Override
	protected void drawValues() {
		String widthText = "Width: " + triangleWidth + " m";
		String heightText = "Height: " + triangleHeight + " m";
		
		int x = lineDistance * 5 / 4;
		int y1 = lineDistance * 2 - (lineDistance / 4);
		int y2 = lineDistance * 4 - (lineDistance / 4);
		
		canvas.drawText(widthText, x, y1, paint);
		canvas.drawText(heightText, x, y2, paint);
	}

	
	@Override
	protected void drawArrow() {
		int p1x = midX - ((triangleWidth / 2) * lineDistance) + (lineDistance * 5 / 3);
		int p1y = midY + ((triangleHeight - (triangleHeight / 2)) * lineDistance);
		
		int p2x = p1x - (lineDistance / 3);
		int p2y = p1y - (lineDistance / 3);

		int p3y = p1y + (lineDistance / 3);
		
		if(directionRight) {
			p1x = midX + ((triangleWidth - (triangleWidth / 2)) * lineDistance) - (lineDistance * 5 / 3);
			p2x = p1x + (lineDistance / 3);
		}
		
		canvas.drawLine(p1x, p1y, p2x, p2y, paint);
		canvas.drawLine(p1x, p1y, p2x, p3y, paint);
	}

	
	public int getTriangleWidth() {
		return triangleWidth;
	}
	
	public int getTriangleHeight() {
		return triangleHeight;
	}
	
	public void setTriangle(int width, int height) {
		this.triangleWidth = width;
		this.triangleHeight = height;
		this.invalidate();
	}
	
}
