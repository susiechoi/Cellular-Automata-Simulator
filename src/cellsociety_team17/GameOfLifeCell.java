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
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
		System.out.println("new cell");
		
	}

	@Override
	 ArrayList<Cell> update() {
		System.out.println("update");
		ArrayList<Cell> aliveNeighbors = new ArrayList<Cell>();
		ArrayList<Cell> newACells = new ArrayList<Cell>();
		for(Cell neighbor:this.getMyNeighbors()) {
			if(neighbor.getMyState()==ALIVE) {
				aliveNeighbors.add(neighbor);
			}
		}
		
		if(this.getMyState()==DEAD) {
			 newACells= this.deadUpdate(aliveNeighbors);
		}
		if (this.getMyState()==ALIVE) {
			newACells= this.aliveUpdate(aliveNeighbors);
		}		
		return newACells;
	}
	
	private ArrayList<Cell> deadUpdate(ArrayList<Cell> alive){
		ArrayList<Cell> newACells= new ArrayList<Cell>();
		if(alive.size()==3) {
			this.setMyState(ALIVE);
			this.updateColor();
			newACells.add(this);
			newACells.addAll(this.getNeighbors());
		}
		return newACells;
	}

	private ArrayList<Cell> aliveUpdate(ArrayList<Cell> alive) {
		ArrayList<Cell> newACells= new ArrayList<Cell>();
		if(alive.size()<2 || alive.size()>3) {
			this.setMyState(DEAD);
			this.updateColor();
			newACells.add(this);
			newACells.addAll(this.getNeighbors());
		}
		return newACells;
	}
	
	 void updateColor() {
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
	}
}
