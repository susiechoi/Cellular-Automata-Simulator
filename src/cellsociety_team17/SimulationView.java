package cellsociety_team17;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
	private String mySimulationTitle;

	public SimulationView(Grid g, String simulationTitle) {
		mySimulationTitle = simulationTitle;
		myGrid = g;
		myHeaderHeight = 25;
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
		myHeaderWidth = myWidth;
		Rectangle myBanner = new Rectangle(myHeaderWidth, myHeaderHeight);
		myBanner.setX(0);
		myBanner.setY(0);
		myBanner.setFill(Color.GREY);
		
		Text myBannerText = new Text();
		myBannerText.setText(mySimulationTitle);
		myBannerText.setTranslateX((myWidth-myBannerText.getBoundsInLocal().getWidth())/2);
		myBannerText.setTranslateY((myHeaderHeight)/2);
		
		myHeader.getChildren().add(myBanner);
		myHeader.getChildren().add(myBannerText);
		
	}
	private void setUpControls() {
		myControlsContainer = new Group();
	}
	private void setUpGridContainer() {
		myGridContainer = new Group();
	}
	
}