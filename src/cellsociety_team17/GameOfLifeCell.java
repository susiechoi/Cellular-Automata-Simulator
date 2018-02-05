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
	 ArrayList<Cell> update() {
		ArrayList<Cell> aliveNeighbors = new ArrayList<Cell>();
		ArrayList<Cell> newACells = new ArrayList<Cell>();
		for(Cell neighbor:myNeighbors) {
			if(neighbor.getMyState()==ALIVE) {
				aliveNeighbors.add(neighbor);
			}
		}
		
		if(myState==DEAD) {
			 newACells= this.deadUpdate(aliveNeighbors);
		}
		if (myState==ALIVE) {
			this.aliveUpdate(aliveNeighbors);
		}		
		this.updateColor();
		return newACells;
	}
	
	private ArrayList<Cell> deadUpdate(ArrayList<Cell> alive){
		ArrayList<Cell> newACells= new ArrayList<Cell>();
		if(alive.size()==3) {
			this.setMyState(ALIVE);
			newACells.add(this);
		}
		return newACells;
	}

	private ArrayList<Cell> aliveUpdate(ArrayList<Cell> alive) {
		ArrayList<Cell> newACells= new ArrayList<Cell>();
		if(alive.size()<2 || alive.size()>3) {
			this.setMyState(DEAD);
			newACells.add(this);
		}
		return newACells;
	}
	
	 void updateColor() {
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}
}
