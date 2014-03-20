package de.dhbw.androne.view.shape;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;
import de.dhbw.androne.view.R;

public class ShapeView extends View {

	private Canvas canvas;
	private Paint paint = new Paint();
	
	private static final int HORIZONTAL_LINE_COUNT = 12;
	private int verticalLineCount;
	
	private int width, height, lineDistance, midX, midY;

	private int rectWidth = 5, rectHeight = 5;
	
	
	public ShapeView(Context context) {
		super(context);
	}
	
	public ShapeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShapeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	public void calculateDefaults() {
		width = canvas.getWidth();
		height = canvas.getHeight();
		lineDistance = height / HORIZONTAL_LINE_COUNT;
		verticalLineCount = width / lineDistance;
		midX = (verticalLineCount / 2) * lineDistance;
		midY = (HORIZONTAL_LINE_COUNT / 2) * lineDistance;
	}
	
	
	@Override
	public void onDraw(Canvas canvas) {
		this.canvas = canvas;
		calculateDefaults();
		
		paint.setColor(Color.LTGRAY);
		paint.setStrokeWidth(3);

		for(int x = 0; x < width; x += lineDistance) {
			canvas.drawLine(x, 0, x, height, paint);
		}
		
		for(int y = 0; y < height; y += lineDistance) {
			canvas.drawLine(0, y, width, y, paint);
		}
		
		paint.setColor(Color.rgb(51, 181, 229));
		paint.setStrokeWidth(6);
		paint.setTextSize(lineDistance / 3 * 2);
		paint.setTextAlign(Align.CENTER);

		drawRectangle();
		drawDrone();
		drawArrow();
		drawWidth();
		drawHeight();
	}
	
	
	public void drawRectangle() {
		int startX = midX - ((rectWidth / 2) * lineDistance);
		int stopX = midX + ((rectWidth - (rectWidth / 2)) * lineDistance);
		int startY = midY - ((rectHeight / 2) * lineDistance);
		int stopY = midY + ((rectHeight - (rectHeight / 2)) * lineDistance);
		

		canvas.drawLine(startX, startY, stopX, startY, paint);
		canvas.drawLine(startX, stopY, stopX, stopY, paint);
		canvas.drawLine(startX, startY, startX, stopY, paint);
		canvas.drawLine(stopX, startY, stopX, stopY, paint);
	}
	
	
	private void drawDrone() {
		Resources res = getResources();
		Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.logo);		
		
		int x = midX + ((rectWidth - (rectWidth / 2)) * lineDistance) - (bitmap.getWidth() / 2);
		int y = midY + ((rectHeight - (rectHeight / 2)) * lineDistance) - (bitmap.getHeight() / 2);
		
		canvas.drawBitmap(bitmap, x, y, null);
	}
	
	
	private void drawArrow() {
		int p1x = midX + ((rectWidth - (rectWidth / 2)) * lineDistance);
		int p1y = midY + ((rectHeight - (rectHeight / 2)) * lineDistance) - (lineDistance * 5 / 3);
		
		int p2x = midX + ((rectWidth - (rectWidth / 2)) * lineDistance) - (lineDistance * 1 / 3);
		int p2y = midY + ((rectHeight - (rectHeight / 2)) * lineDistance) - lineDistance * 4 / 3;
		
		int p3x = midX + ((rectWidth - (rectWidth / 2)) * lineDistance) + (lineDistance * 1 / 3);
		
		canvas.drawLine(p1x, p1y, p2x, p2y, paint);
		canvas.drawLine(p1x, p1y, p3x, p2y, paint);
	}
	
	
	private void drawWidth() {
		int x = midX;
		int y = midY - ((rectHeight / 2) * lineDistance) - (lineDistance / 3);
		
		if((rectWidth % 2) == 1) {
			x = x + (lineDistance / 2);
		}
		
		String widthText = "Width: " + rectWidth + " m";
		
		canvas.drawText(widthText, x, y, paint);
	}
	
	
	private void drawHeight() {
		int x = midX - ((rectWidth / 2) * lineDistance) - (lineDistance / 3);
		int y = midY;
		
		if((rectHeight % 2) == 1) {
			y = y + (lineDistance / 2);
		}

		String heightText = "Height: " + rectHeight + " m";
		
		canvas.rotate(-90, x, y);
		canvas.drawText(heightText, x, y, paint);
	}
	
	
	public void setRectangle(int rectWidth, int rectHeight) {
		this.rectWidth = rectWidth;
		this.rectHeight = rectHeight;
		this.invalidate();
	}
	
	
	public int getVerticalLineCount() {
		return verticalLineCount;
	}
	
	
	public int getHorizontalLineCount() {
		return HORIZONTAL_LINE_COUNT;
	}
	
}
