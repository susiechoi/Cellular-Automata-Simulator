package cellsociety_team17;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SimulationView {
	public static final String DEFAULT_RESOURCE_PACKAGE = "properties/";
	public static final String DEFAULT_IMG_FILEPATH = "src/properties/";
	public static final String DEFAULT_LANGUAGE = "Image";
	public static final double MIN_WIDTH = 100;
	public static final Paint ACCENT_COLOR = Color.LIGHTGRAY;
	public static final Paint PRIMARY_COLOR = Color.GRAY; 
	public static final int DEFAULT_BUTTON_SIZE = 46;
	public static final double DEFAULT_BUTTON_SCALE = 0.75; 
	public static final String IMG_FILE_PATH = "assets/IMG/"; 

	private ResourceBundle myResources;
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
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+DEFAULT_LANGUAGE);
		mySimulationTitle = simulationTitle;
		myGrid = g;
		myHeaderHeight = 25;
		myControlsContainerHeight = 50;
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
		myBanner.setFill(ACCENT_COLOR);

		Text myBannerText = new Text();
		myBannerText.setText(mySimulationTitle);
		myBannerText.setTranslateX((myWidth-myBannerText.getBoundsInLocal().getWidth())/2);
		myBannerText.setTranslateY((myHeaderHeight)/2);
		myBannerText.fontProperty().setValue(Font.font("Verdana", FontWeight.BOLD, 12));

		myHeader.getChildren().add(myBanner);
		myHeader.getChildren().add(myBannerText);

	}
	private void setUpControls() {
		myControlsContainer = new Group();
		myControlsContainerWidth = myWidth;

		Rectangle myControlsBanner = new Rectangle(myControlsContainerWidth,myControlsContainerHeight);
		myControlsBanner.setFill(ACCENT_COLOR);
		myControlsBanner.setX(0);
		myControlsBanner.setY(myHeaderHeight + myGrid.getHeightInPixels());

		squareButton myPlayButton = new squareButton(DEFAULT_BUTTON_SIZE, "Play");
		squareButton myPauseButton = new squareButton(DEFAULT_BUTTON_SIZE, "Pause");
		myPauseButton.setTranslateX(DEFAULT_BUTTON_SIZE);
		squareButton myFastForwardButton = new squareButton(DEFAULT_BUTTON_SIZE, "FastForward");
		myFastForwardButton.setTranslateX(DEFAULT_BUTTON_SIZE * 2);

		myControlsContainer.getChildren().add(myControlsBanner);
		myControlsContainer.getChildren().add(myPlayButton);
		myControlsContainer.getChildren().add(myPauseButton);
		myControlsContainer.getChildren().add(myFastForwardButton);

	}
	private void setUpGridContainer() {
		myGridContainer = new Group();
		myGridContainer.getChildren().add(myGrid.getGroup());
	}

	private class squareButton extends ImageView {
		squareButton(int size, String type){
			this.setFitHeight(size);
			this.setFitWidth(size);			
			final String IMAGEFILE_SUFFIXES =
					String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
			//	        Button result = new Button();
			String label = myResources.getString(type);
			if (label.matches(IMAGEFILE_SUFFIXES)) {
				//	            result.setGraphic(new ImageView(
				//	                                  new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_PACKAGE + label))));
				java.io.FileInputStream fis; 
				try {
					fis = new FileInputStream(DEFAULT_IMG_FILEPATH + label);
					Image buttonImage = new Image(fis);
					this.setImage(buttonImage);
					this.setScaleX(DEFAULT_BUTTON_SCALE);
					this.setScaleY(DEFAULT_BUTTON_SCALE);
					this.setY(myHeaderHeight + myGrid.getHeightInPixels());
				}
				// TODO make more detailed catch block
				catch (FileNotFoundException e) {
					System.out.println("No file found");
//					e.printStackTrace();
				}
			}
//			else {
//			}
		}
	}

}

