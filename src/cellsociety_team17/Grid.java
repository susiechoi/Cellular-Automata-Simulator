package cellsociety_team17;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Cell;

public class Grid {

	private int mySimulationType;
	private int myWidth;
	private int myHeight;
	private Cell[][] myCells; 
	private Group myGroup;

	public Grid(int width, int height) {
		this(1, 400, 400, new ArrayList<Cell>()); 
	}

	// is it even necessary to have simulationType? Main can select appropriate subclass after receiving user mouse input
	public Grid(int simulationType, int width, int height, ArrayList<Cell> cells) {
		mySimulationType = simulationType;
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myWidth][myHeight]; 
		// constructor could be Cell(int xPos, int yPos, int startState) (since file format would prob hold x, y coords & start state) 
		for (Cell cell : cells) {
			myCells[cell.myXPos][cell.myYPos] = cell; 
			myGroup.getChildren().add(cell.myRectangle);
		}
	}

	public void updateCells(ArrayList<Cell> activeCells) {
		for (Cell cell : activeCells) {
			cell.update(); 
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
