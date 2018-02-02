package cellsociety_team17;

import javafx.scene.paint.Color;

public class WatorCell extends Cell {
	public static final int DEFAULT_FISH_CLOCK = 2;
	public static final int DEFAULT_SHARK_CLOCK = 4;
	public static final int DEFAULT_SHARK_ENERGY = 6;
	public static final int EMPTY=0;
	public static final int FISH= 1;
	public static final int SHARK=2;
	public static final Color[] STATE_COLORS= {Color.WHITE, Color.PALEGREEN, Color.CYAN};
	
	private int myFishClock;
	private int mySharkClock;
	private int mySharkEnergy;

	public WatorCell(int row, int column, int state) {
		super(row, column, state);

	}

	public WatorCell(int row, int column, int state, int fishClock, int sharkClock, int sharkEnergy) {
		super(row, column, state);
		myFishClock = fishClock;
		mySharkClock = sharkClock;
		mySharkEnergy = sharkEnergy;
	}

	@Override
	void update() {
		if(this.getMyState()==FISH){
			this.fishUpdate();
		}
		
		if(this.getMyState()==SHARK) {
			this.sharkUpdate();
		}
		
		this.updateColor();
	}
	
	private void fishUpdate() {
		
	}
	
	private void sharkUpdate() {
		
	}
	
	private void updateColor() {
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}

}
