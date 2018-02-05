package cellsociety_team17;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class GameOfLifeCell extends Cell {
	
	// myState: 0 is dead, 1 is alive
	public static final int DEAD=0;
	public static final int ALIVE=1;
	
	public static final Color[] STATE_COLORS = { Color.RED, Color.GREEN};

	public GameOfLifeCell(int row, int column, int state) {
		super(row, column, state);
		
	}

	@Override
	void update() {
		ArrayList<Cell> aliveNeighbors = new ArrayList<Cell>();
		for(Cell neighbor:myNeighbors) {
			if(neighbor.getMyState()==ALIVE) {
				aliveNeighbors.add(neighbor);
			}
		}
		
		if(myState==DEAD) {
			this.deadUpdate(aliveNeighbors);
		}
		if (myState==ALIVE) {
			this.aliveUpdate(aliveNeighbors);
		}		
		this.updateColor();
	}
	
	private void deadUpdate(ArrayList<Cell> alive){
		if(alive.size()==3) {
			this.setMyState(ALIVE);
		}
	}

	private void aliveUpdate(ArrayList<Cell> alive) {
		if(alive.size()<2 || alive.size()>3) {
			this.setMyState(DEAD);
		}
		
	}
	
	 void updateColor() {
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}
}
