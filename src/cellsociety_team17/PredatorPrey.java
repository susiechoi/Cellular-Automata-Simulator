package cellsociety_team17;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public class PredatorPrey extends Cell {
	public static final Color[] STATE_COLORS = {Color.BLUE, Color.GREEN, Color.YELLOW}; 
	public static final int PREDATOR = 2;
	public static final int PREY = 1;
	public static final int EMPTY = 0;
	private int myPredatorStarve = 5;
	private int myPredatorSpawn = 20;
	private int myPreySpawn = 1;
	
	
	private int myStepsToStarve;
	private int myStepsToSpawn;
	
	public PredatorPrey(int row, int column, int state) {
		super(row, column, state);
		if(myState == 2) {
			myStepsToStarve = myPredatorStarve;
			myStepsToSpawn = myPredatorSpawn;
		} else {
			myStepsToSpawn = myPreySpawn;
		}
	}


	@Override
	void update() {
		switch(myState) {
		case 2:
			break;
		case 1:
			updatePrey();
			myStepsToSpawn--;
			break;
			
		
		}
	}

	private void updatePrey() {
		ArrayList<Cell> emptyNeighbors = new ArrayList<Cell>();
		for(Cell c : this.getNeighbors()) {
			if(c.myState == EMPTY) {
				emptyNeighbors.add(c);
			}
		}
		if(emptyNeighbors != null) {
			Random rand = new Random();
			int  n = rand.nextInt(3);
			Cell p = emptyNeighbors.get(n);
			emptyNeighbors.remove(n);
			p.setMyState(PREY);
			p.updateColor();
			this.setMyState(EMPTY);
			this.updateColor();
			
			if(myStepsToSpawn == 0) {
				((PredatorPrey) p).spawn();
			}
		}
		
	}
	
	public void spawn() {
		ArrayList<Cell> emptyNeighbors = new ArrayList<Cell>();
		for(Cell c : this.getNeighbors()) {
			if(c.myState == EMPTY) {
				emptyNeighbors.add(c);
			}
		}
		if(emptyNeighbors != null) {
			Random rand = new Random();
			int  n = rand.nextInt(3);
			Cell p = emptyNeighbors.get(n);
			emptyNeighbors.remove(n);
			p.setMyState(PREY);
			p.updateColor();
			this.setMyStepsToSpawn(PREY);
		}
	}


	private void setMyStepsToSpawn(int state) {
		if(state == PREY) {
		myStepsToSpawn = myPreySpawn;
		} else if (state == PREDATOR) {
		myStepsToSpawn = myPredatorSpawn;
		}
		
	}


	@Override
	void updateColor() {
		// TODO Auto-generated method stub
		
	}

}
