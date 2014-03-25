package de.dhbw.androne.model;

public class CircleShape implements Shape {

	private double radius;
	private boolean directionRight;

	
	public CircleShape(int radius, boolean directionRight) {
		this.radius = radius;
		this.directionRight = directionRight;
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
}
