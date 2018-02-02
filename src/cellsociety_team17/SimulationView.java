package cellsociety_team17;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SimulationView {
	private static final double MIN_WIDTH = 100;
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
		
		if(myGrid.getWidthInPixels() > MIN_WIDTH) {
			myWidth = myGrid.getWidthInPixels();
		} else {
			myWidth = MIN_WIDTH;
		}
		myRoot = new Group();
		setUpHeader();
		setUpGridContainer();
		setUpControls();
		establishScene();
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	public Group getRoot() {
		return myRoot;
	}
	
	public Group getMyGridContainer() {
		return myGridContainer;
	}
	
	public void establishScene() {
		myScene = new Scene(myRoot, myWidth, myHeight);
		myRoot.getChildren().addAll(myHeader);
		myRoot.getChildren().addAll(myGridContainer);
		myRoot.getChildren().addAll(myControlsContainer);
	}
	
	private void setUpHeader() {
		myHeader = new Group();
		Rectangle myBanner = new Rectangle(myHeaderWidth, myHeaderHeight);
		myBanner.setFill(Color.RED);
		myHeader.getChildren().add(myBanner);
	}
	private void setUpControls() {
		myControlsContainer = new Group();
	}
	private void setUpGridContainer() {
		myGridContainer = new Group();
	}
	
}