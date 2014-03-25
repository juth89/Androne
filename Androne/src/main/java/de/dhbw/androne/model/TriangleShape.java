package de.dhbw.androne.model;

public class TriangleShape implements Shape {

	private double width;
	private double height;
	private boolean directionRight;
	
	
	public TriangleShape(int width, int height, boolean directionRight) {
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


	/**
	 * returns the bottom angle 
	 */
	public double getAlpha() {
		return Math.toDegrees(Math.atan(height / (width / 2)));
	}
	
	
	/**
	 * returns the top angle
	 */
	public double getBeta() {
		return 2 * Math.toDegrees(Math.atan((width / 2) / height));
	}
	
	
	public double getSideLength() {
		return Math.sqrt(Math.pow((width / 2), 2) + Math.pow(height, 2));
	}
	
	
	public static void main(String[] args) {
		TriangleShape shape = new TriangleShape(5, 5, false);
		System.out.println("Width: " + shape.getWidth());
		System.out.println("Height: " + shape.getHeight());
		System.out.println("Alpha: " + shape.getAlpha());
		System.out.println("Beta: " + shape.getBeta());
		System.out.println("Side Lenght: " + shape.getSideLength());
	}
}
