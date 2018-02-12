package cellsociety_team17;

import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {
	private static final double DEFAULT_SIZE = 40;
	private double mySize = DEFAULT_SIZE;

	public Triangle() {
		this.getPoints().addAll(new Double[] { 0.0, 0.0, DEFAULT_SIZE, 0.0, DEFAULT_SIZE, DEFAULT_SIZE });
	}

	public Triangle(double size) {
		mySize = size;
		this.getPoints().addAll(new Double[] { 0.0, 0.0, size, 0.0, size, size });
	}
	public Triangle copy() {
		return new Triangle(this.mySize);
	}

}
