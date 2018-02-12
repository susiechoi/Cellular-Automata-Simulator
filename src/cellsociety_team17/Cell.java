package cellsociety_team17;

import java.util.List;

import javafx.scene.shape.Shape;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Cell {

	protected static final Color[] STATE_COLORS = { Color.WHITE, Color.BLUE, Color.RED };

	private int myState;
	private int myRow;
	private int myColumn;
	private Shape myShape;
	private List<Cell> myNeighbors;
 	public static final int CELLSIZE = 40;

	public Cell(int row, int column, int state) {
		myState = state;
		myRow = row;
		myColumn = column;
		myShape = new Rectangle(CELLSIZE, CELLSIZE);
		myShape.setTranslateX(myColumn * CELLSIZE);
		myShape.setTranslateY(myRow * CELLSIZE);
		//this.getMyShape().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	
	 /*EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
         @Override 
         public void handle(MouseEvent event) { 
        	 	int randomState = (int) Math.random() * 2;
        	 	setMyState(randomState) ;
        	 	myShape.setFill(STATE_COLORS[randomState]);
         } 
      };  */

	public Cell(int row, int column, int state, Shape shape) {
		this(row, column, state);
		myShape = shape;
	}

	/**
	 * Returns the int value of current state
	 * 
	 * @return int the current state of the cell
	 */
	public int getMyState() {
		return myState;
	}

	/**
	 * Sets the int value state of the cell
	 * 
	 * @param state
	 *            int value of current state
	 */
	public void setMyState(int state) {
		myState = state;
	}

	/**
	 * Returns the int value of the cell's row
	 * 
	 * @return int row containing the cell
	 */
	public int getMyRow() {
		return myRow;
	}

	/**
	 * Sets the int value of the row in the grid representing this cell
	 * 
	 * @param r
	 *            int value of the row in the grid
	 */
	public void setMyRow(int r) {
		myRow = r;
	}

	/**
	 * Returns the int value of the cell's column
	 * 
	 * @return int column containing the cell
	 */
	public int getMyColumn() {
		return myColumn;
	}

	/**
	 * Sets the int value of the column in the grid representing this cell
	 * 
	 * @param c
	 *            int value of the column in the grid
	 */
	public void setMyColumn(int c) {
		myColumn = c;
	}

	/**
	 * returns the Shape instance of this cell
	 * 
	 * @return Shape of the cell
	 */
	public Shape getMyShape() {
		return myShape;

	}

	/**
	 * Sets the shape value of a cell
	 * 
	 * @param s
	 *            Shape of the cell
	 */
	public void setMyShape(Shape s) {
		myShape = s;
		this.updateColor();
		if(myShape.getClass().getSimpleName().equals("Triangle")) {
			myShape.setTranslateY(myRow * myShape.getBoundsInLocal().getHeight());
			if(myColumn % 2 == 0) {
			myShape.setRotate(180);
			myShape.setTranslateX(((myColumn) * myShape.getBoundsInLocal().getWidth())-((myColumn/2)* myShape.getBoundsInLocal().getWidth()));
			} else {
				myShape.setTranslateX(((myColumn-1) * myShape.getBoundsInLocal().getWidth())-((myColumn/2)* myShape.getBoundsInLocal().getWidth()));
			}
		}
	}

	/**
	 * Gets an ArrayList<Cell> of cells containing all the neighboring cells
	 * 
	 * @return ArrayList<Cell><CEll>
	 */
	public List<Cell> getNeighbors() {
		return getMyNeighbors();
	}

	/**
	 * Sets an ArrayList<Cell> of cells containing all the neighboring cells
	 * 
	 * @param neighbors
	 */
	public void setNeighbors(List<Cell> neighbors) {
		setMyNeighbors(neighbors);
	}

	abstract List<Cell> update();

	abstract void updateColor();

	public List<Cell> getMyNeighbors() {
		return myNeighbors;
	}

	public void setMyNeighbors(List<Cell> neighbors) {
		this.myNeighbors = neighbors;
	}

}
