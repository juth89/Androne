package de.dhbw.androne.view.shape;

import de.dhbw.androne.model.Shape;
import android.content.Context;
import android.util.AttributeSet;

public class CircleShapeView extends ShapeView {
	
	private int circleRadius = 2;
	
	
	public CircleShapeView(Context context) {
		super(context);
	}
	
	public CircleShapeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleShapeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	protected void drawShape() {
		int x = midX;
		int y = midY;
		
		canvas.drawCircle(x, y, (circleRadius * lineDistance), paint);
	}

	@Override
	protected void drawDrone() {
		int x = midX + (circleRadius * lineDistance) - (droneBitmap.getWidth() / 2);
		int y = midY - (droneBitmap.getHeight() / 2);
		
		canvas.drawBitmap(droneBitmap, x, y, null);
	}

	@Override
	protected void drawValues() {
		String text = "Radius: " + circleRadius + " m";
		
		int x = lineDistance * 5 / 4;
		int y = lineDistance * 2 - (lineDistance / 4);
		
		canvas.drawText(text, x, y, paint);
	}

	@Override
	protected void drawArrow() {
		int p1x = midX;
		int p1y = midY - (circleRadius * lineDistance);
		
		if(directionRight) {
			p1y = midY + (circleRadius * lineDistance);
		}
		
		int p2x = p1x + (lineDistance * 1 / 3);
		int p2y = p1y - (lineDistance * 1 / 3);
		
		int p3y = p1y + (lineDistance * 1 / 3);
		
		canvas.drawLine(p1x, p1y, p2x, p2y, paint);
		canvas.drawLine(p1x, p1y, p2x, p3y, paint);
	}
	
	
	public int getCircleRadius() {
		return circleRadius;
	}

	
	public void setCircleRadius(int circleRadius) {
		this.circleRadius = circleRadius;
		this.invalidate();
	}

	
	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
