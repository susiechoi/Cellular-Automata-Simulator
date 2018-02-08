package cellsociety_team17;

import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {
	private static final int DEFAULT_SIZE = 20;
	private double mySize;

	public Triangle() {
		this.getPoints().addAll(new Double[] { 0.0, 0.0, 20.0, 0.0, 20.0, 20.0 });
	}

	public Triangle(double size) {
		mySize = size;
		this.getPoints().addAll(new Double[] { 0.0, 0.0, size, 0.0, size, size });
	}

}
