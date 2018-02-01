package cellsociety_team17;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SimulationView {
	private double myHeight;
	private double myWidth;
	private double myHeaderHeight;
	private double myHeaderWidth;
	private double myControlsContainerHeight;
	private double myControlsContainerWidth;
	private Grid myGrid;
	private Group myRoot;
	private Group myHeader;
	private Group myGridContainer;
	private Group myControlsContainer;
	private Scene myScene;

	public SimulationView(Grid g) {
		myGrid = g;
		myHeaderHeight = 50;
		myHeight = myHeaderHeight + myGrid.getHeightInPixels() + myControlsContainerHeight;
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	public void establishScene() {
		myScene = new Scene(myRoot);
		myRoot.getChildren().addAll(myHeader);
		myRoot.getChildren().addAll(myGridContainer);
		myRoot.getChildren().addAll(myControlsContainer);
	}
	
	private void setUpHeader() {
		
	}
	private void setUpControls() {
		
	}
	private void setUpGridContainer() {
		
	}
	
}