package cellsociety_team17;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class GameOfLifeCell extends Cell {
	
	// myState: 0 is dead, 1 is alive
	public static final int DEAD=0;
	public static final int ALIVE=1;
	public static final int MIN_ALIVE=2;
	public static final int MAX_ALIVE=3;
	public static final int DEAD_TO_ALIVE=3;
	
	protected static final Color[] STATE_COLORS = { Color.RED, Color.GREEN};

	public GameOfLifeCell(int row, int column, int state) {
		super(row, column, state);
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
		System.out.println("new cell");
		
	}

	@Override
	 List<Cell> update() {
		System.out.println("update");
		ArrayList<Cell> aliveNeighbors = new ArrayList<>();
		ArrayList<Cell> newACells =new ArrayList<>();;
		for(Cell neighbor:this.getMyNeighbors()) {
			if(neighbor.getMyState()==ALIVE) {
				aliveNeighbors.add(neighbor);
			}
		}
		
		if(this.getMyState()==DEAD) {
			 newACells= (ArrayList<Cell>) this.deadUpdate(aliveNeighbors);
		}
		if (this.getMyState()==ALIVE) {
			newACells= (ArrayList<Cell>) this.aliveUpdate(aliveNeighbors);
		}		
		return newACells;
	}
	
	private List<Cell> deadUpdate(List<Cell> alive){
		ArrayList<Cell> newACells= new ArrayList<>();
		if(alive.size()==DEAD_TO_ALIVE) {
			this.setMyState(ALIVE);
			this.updateColor();
			newACells.add(this);
			newACells.addAll(this.getNeighbors());
		}
		return newACells;
	}

	private List<Cell> aliveUpdate(List<Cell> alive) {
		ArrayList<Cell> newACells= new ArrayList<>();
		if(alive.size()<MIN_ALIVE || alive.size()>MAX_ALIVE) {
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
