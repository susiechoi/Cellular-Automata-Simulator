package cellsociety_team17;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class RockPaperScissorsCell extends Cell{
	public static final int EMPTY= 0;
	public static final int RED= 1;
	public static final int Green= 2;
	public static final int BLUE=3;
	protected static final Color[] STATE_COLORS = { Color.WHITE, Color.RED, Color.GREEN, Color.BLUE};
	private Shape myShape;
	private int myLevel;
	
	public RockPaperScissorsCell(int row, int column, int state) {
		super(row, column, state);
		myShape=this.getMyShape();
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);

	}
	
	@Override
	List<Cell> update() {
		ArrayList<Cell> newACells = new ArrayList<>();
		
		int indexOfNeighb = (int) Math.random() * getMyNeighbors().size();
		Cell cellToMoveTo = getMyNeighbors().get(indexOfNeighb);
		if(this.getMyState()==0 && cellToMoveTo.getMyState()!=0 && this.getMyLevel()<9) {
			cellToMoveTo.setMyState(this.getMyState());
			((RockPaperScissorsCell) cellToMoveTo).setMyLevel(this.getMyLevel()+1);
		}
		this.updateColor();
		return newACells;
	}
	
	private void setMyLevel(int level) {
		myLevel= level;
	}
	
	private int getMyLevel() {
		return myLevel;
	}

	@Override
	void updateColor() {
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
		
	}

}