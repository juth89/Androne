package de.dhbw.androne.model;

public class CircleShape implements Shape {

	private double radius;
	private boolean directionRight;
	private int approximationSteps;

	
	public CircleShape(int radius, boolean directionRight, int approximationSteps) {
		this.radius = radius;
		this.directionRight = directionRight;
		this.approximationSteps = approximationSteps;
	}
	
	
	public double getRadius() {
		return radius;
	}
	
	
	@Override
	public boolean flyRight() {
		return directionRight;
	}

	
	public double getCircumference() {
		return 2.0 * Math.PI * radius;
	}
	
	
	public int getApproximationSteps() {
		return approximationSteps;
	}
}
