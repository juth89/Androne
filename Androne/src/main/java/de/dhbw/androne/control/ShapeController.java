package de.dhbw.androne.control;

import java.io.IOException;

import android.util.Log;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.NavData;
import com.codeminders.ardrone.NavDataListener;

import de.dhbw.androne.model.CircleShape;
import de.dhbw.androne.model.RectangleShape;
import de.dhbw.androne.model.Shape;
import de.dhbw.androne.model.TriangleShape;

public class ShapeController implements NavDataListener {

	private static final String TAG = "ShapeDroneControl";
	
	private ARDrone drone;
	private boolean isFlyingShape = false;
	
	private long time;
	private float vX, vZ;

	private static final double NINTY_DEGREES = 90.0;
	
	
	public ShapeController(ARDrone drone) {
		this.drone = drone;
	}
	
	
	public void flyShape(Shape shape) {
		isFlyingShape = true;
		if(shape instanceof RectangleShape) {
			flyRectangle((RectangleShape) shape);
			return;
		} else if(shape instanceof TriangleShape) {
			flyTriangle((TriangleShape) shape);
			 return;
		} else if(shape instanceof CircleShape) {
			flyCircle((CircleShape) shape);
		}
		isFlyingShape = false;
	}
	
	
	private void flyRectangle(RectangleShape rectangleShape) {
		double width = rectangleShape.getWidth();
		double height = rectangleShape.getHeight();
		boolean flyRight = rectangleShape.flyRight();
		
		for(int i = 0; i < 2; i++) {
			if(flyRight) {
				forward(width);
				rotateRight(NINTY_DEGREES);
				
				forward(height);
				rotateRight(NINTY_DEGREES);
			} else {
				forward(height);
				rotateLeft(NINTY_DEGREES);
				
				forward(width);
				rotateLeft(NINTY_DEGREES);
			}
		}
	}
	
	
	private void flyTriangle(TriangleShape triangleShape) {
		double width = triangleShape.getWidth();
		boolean flyRight = triangleShape.flyRight();
		double alpha = triangleShape.getAlpha();
		double beta = triangleShape.getBeta();
		double sideLength = triangleShape.getSideLength();
				
		if(flyRight) {
			forward(width);
			rotateRight(180.0 - alpha);
			
			forward(sideLength);
			rotateRight(180.0 - beta);
			
			forward(sideLength);
			rotateRight(180.0 - alpha);
		} else {
			forward(width);
			rotateLeft(180.0 - alpha);
			
			forward(sideLength);
			rotateLeft(180.0 - beta);
			
			forward(sideLength);
			rotateLeft(180.0 - alpha);
		}
	}
	
	
	private void flyCircle(CircleShape circleShape) {
		boolean flyRight = circleShape.flyRight();
		int approximationSteps = circleShape.getApproximationSteps();
		double circumference = circleShape.getCircumference();

		double forwardDistance = circumference / approximationSteps;
		double angle = 360.0 / approximationSteps;
		
		for(int i = 0; i < approximationSteps; i++) {
			forward(forwardDistance);
			if(flyRight) {
				rotateLeft(angle);
			} else {
				rotateRight(angle);
			}
		}
	}
	
	
	private void forward(double distance) {
		double completeDistance = 0.0;
		
		while(completeDistance <= distance) {
			
			double flyedDistance = vX * (System.currentTimeMillis() - time);  
			completeDistance += flyedDistance;

			try {
				Thread.sleep(5);
			} catch(Exception e) {
				Log.e(TAG, e.getMessage());
			}
		}
		
		try {
			drone.hover();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	
	private void rotateLeft(double degree) {
		
	}

	
	private void rotateRight(double degree) {
		
	}


	@Override
	public void navDataReceived(NavData nd) {
		time = System.currentTimeMillis();
		vX = nd.getVx();
		vZ = nd.getVz();
	}

	
	public boolean isFlyingShape() {
		return isFlyingShape;
	}
}
