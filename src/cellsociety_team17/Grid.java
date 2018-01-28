package cellsociety_team17;

import java.util.ArrayList;

import javafx.scene.Group;

public class Grid {

	private int mySimulationType;
	private int myWidth;
	private int myHeight;
	private Cell[][] myCells; 
	private Group myGroup;

	public Grid(int width, int height) {
		this(1, 400, 400, new ArrayList<Cell>()); 
	}

	public Grid(int simType, int width, int height, ArrayList<Cell> activeCells) {
		mySimulationType = simType;
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myWidth][myHeight]; 
		// TODO propagate myCells with active cells 
		for (Cell cell : myCells) {
			myGroup.getChildren().add(cell.myRectangle);
		}
	}
	
	private Group getGroup() {
		return myGroup; 
	}
	
	private int getWidth() {
		return myWidth;
	}
	
	private int getHeight() {
		return myHeight; 
	}

}
