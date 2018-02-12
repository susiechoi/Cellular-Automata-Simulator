package cellsociety_team17;

import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {
	private static final double DEFAULT_SIZE = 40;
	private double mySize = DEFAULT_SIZE;

	/**
	 * Default triangle constructor
	 */
	public Triangle() {
		this.getPoints().addAll(new Double[] { 0.0, 0.0, DEFAULT_SIZE, 0.0, DEFAULT_SIZE, DEFAULT_SIZE });
	}

	/**
	 * Triangle constructor given a size double.
	 * @param size
	 */
	public Triangle(double size) {
		mySize = size;
		this.getPoints().addAll(new Double[] { 0.0, 0.0, size, 0.0, size, size });
	}
	/**
	 * Creates a duplicate of the current triangle shape
	 * @return
	 */
	public Triangle copy() {
		return new Triangle(this.mySize);
	}

}
