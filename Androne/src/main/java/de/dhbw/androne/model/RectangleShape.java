package de.dhbw.androne.model;

public class RectangleShape implements Shape {

	private double width;
	private double height;
	private boolean directionRight;
	
	
	public RectangleShape(int width, int height, boolean directionRight) {
		this.width = width;
		this.height = height;
		this.directionRight = directionRight;
	}
	
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	@Override
	public boolean flyRight() {
		return directionRight;
	}
}
