package de.dhbw.androne.control;

import com.codeminders.ardrone.ARDrone;
import de.dhbw.androne.model.*;

public class ShapeDroneControl {

	private ARDrone drone;

	private static final double NINTY_DEGREES = 90.0;
	
	
	public ShapeDroneControl(ARDrone drone) {
		this.drone = drone;
	}
	
	
	public void flyShape(Shape shape) {
		if(shape instanceof RectangleShape) {
			flyRectangle((RectangleShape) shape);
			return;
		} 
		if(shape instanceof TriangleShape) {
			flyTriangle((TriangleShape) shape);
			 return;
		}
		if(shape instanceof CircleShape) {
			flyCircle((CircleShape) shape);
		}
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
		double radius = circleShape.getRadius();
		boolean flyRight = circleShape.flyRight();
		double circumference = circleShape.getCircumference();
		
		int approximationLevel = 100;
		
		for(int i = 0; i < approximationLevel; i++) {
			
		}
	}
	
	
	private void forward(double distance) {
		
	}
	
	
	private void rotateLeft(double degree) {
		
	}

	
	private void rotateRight(double degree) {
		
	}
}
