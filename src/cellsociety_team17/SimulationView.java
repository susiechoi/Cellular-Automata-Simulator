/**
 * 
 * 
 * @author Collin Brown
 * @author Susie Choi
 */

package cellsociety_team17;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
	private static final double DEFAULT_SPEED = 1;
	private static final double DEFAULT_SPEED_CHANGE = 0.5;

	private ResourceBundle myResources;
	private double myHeight;
	private double myWidth;
	private double myHeaderHeight;
	private double myHeaderWidth;
	private double myControlsContainerHeight;
	private double myControlsContainerWidth;
	private double mySliderContainerHeight;
	private Grid myGrid;
	private Group myRoot;
	private Group myHeader;
	private Group myGridContainer;
	private Group myControlsContainer;
	private Group mySlideContainer;
	private Scene myScene;
	private String mySimulationTitle;
	private Slider mySlider;
	private BooleanProperty playing = new SimpleBooleanProperty();
	private BooleanProperty restart = new SimpleBooleanProperty();
	private DoubleProperty mySpeed = new SimpleDoubleProperty();
	private BooleanProperty home = new SimpleBooleanProperty();
	private BooleanProperty step = new SimpleBooleanProperty();
	private BooleanProperty save = new SimpleBooleanProperty();
	private BooleanProperty newWindow = new SimpleBooleanProperty();

	/**
	 * 
	 * @param Grid g: The active grid to be used in the simulation
	 * @param String simulationTitle: The title of the simulation being run
	 */
	public SimulationView(Grid g, String simulationTitle, Slider slider) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
		mySimulationTitle = simulationTitle;
		mySlider=slider;
		myGrid = g;
		myHeaderHeight = 25;
		myControlsContainerHeight = 50;
		mySliderContainerHeight=50;
		myHeight = myHeaderHeight + myGrid.getHeightInPixels() + myControlsContainerHeight + mySliderContainerHeight;
		mySpeed.set(DEFAULT_SPEED);

		if (myGrid.getWidthInPixels() > MIN_WIDTH) {
			myWidth = myGrid.getWidthInPixels();
		} else {
			myWidth = MIN_WIDTH;
		}
		
		myRoot = new Group();
		setUpHeader();
		setUpGridContainer();
		setUpControls();
		setUpSlider();
		establishScene();
		home.set(false);
		step.set(false);
	}
	/**
	 * Returns the scene being used by the SimulationView
	 * @return Scene myScene: The current scene being used
	 */
	public Scene getScene() {
		return myScene;
	}
	/**
	 * Returns the root group of the scene being used by the SimulationView
	 * @return Group myRoot: The root group of the SimulationView
	 */
	public Group getRoot() {
		return myRoot;
	}
	/**
	 * Returns the group containing all the elements pertaining to the grid
	 * @return Group myGridContainer: The group containing all the grid elements
	 */
	public Group getMyGridContainer() {
		return myGridContainer;
	}
	/**
	 * Combines all the elements of the header, grid, and controls container into myScene
	 */
	public void establishScene() {
	  	myScene = new Scene(myRoot, myWidth, myHeight);
		myRoot.getChildren().addAll(myHeader);
		myRoot.getChildren().addAll(myGridContainer);
		myRoot.getChildren().addAll(myControlsContainer);
		myRoot.getChildren().addAll(mySlideContainer);
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
		myBannerText.setTranslateX((myWidth - myBannerText.getBoundsInLocal().getWidth()) / 2);
		myBannerText.setTranslateY((myHeaderHeight) / 2);
		myBannerText.fontProperty().setValue(Font.font("Verdana", FontWeight.BOLD, 12));

		myHeader.getChildren().add(myBanner);
		myHeader.getChildren().add(myBannerText);

	}

	private void setUpControls() {
		myControlsContainer = new Group();
		myControlsContainerWidth = myWidth;

		myControlsContainer.getChildren().add(setUpControlsBanner());
		myControlsContainer.getChildren().add(setUpSlowdownButton());
		myControlsContainer.getChildren().add(setUpPlayButton());
		myControlsContainer.getChildren().add(setUpFastForwardButton());
		myControlsContainer.getChildren().add(setUpRestartButton());
		myControlsContainer.getChildren().add(setUpStepButton());
		myControlsContainer.getChildren().add(setUpReturnHomeButton());
		myControlsContainer.getChildren().add(setUpSaveButton());
		myControlsContainer.getChildren().add(setUpNewWindowButton());
	}
	
	
	private void setUpSlider() {
		mySlideContainer= new Group();
		Rectangle sliderRect = new Rectangle(myWidth, mySliderContainerHeight);
		sliderRect.setFill(Color.BLACK);
		sliderRect.setY(myHeaderHeight + myGrid.getHeightInPixels() + myControlsContainerHeight);
		mySlideContainer.getChildren().add(sliderRect);
		mySlider.setLayoutY(myHeaderHeight + myGrid.getHeightInPixels() + myControlsContainerHeight);
		mySlideContainer.getChildren().add(mySlider);
	}

	private squareButton setUpSaveButton() {
		squareButton mySaveButton = new squareButton(DEFAULT_BUTTON_SIZE, "Save");
		mySaveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				save.set(!save.get());
			}
		});
		mySaveButton.setTranslateX(DEFAULT_BUTTON_SIZE * 6);
		return mySaveButton;

	}

	private squareButton setUpNewWindowButton() {
		squareButton myNewWindowButton = new squareButton(DEFAULT_BUTTON_SIZE, "NewWindow");
		myNewWindowButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				newWindow.set(!newWindow.get());
			}
		});
		myNewWindowButton.setTranslateX(DEFAULT_BUTTON_SIZE * 7);
		return myNewWindowButton;
	}
	
	private squareButton setUpReturnHomeButton() {
		squareButton myReturnHomeButton = new squareButton(DEFAULT_BUTTON_SIZE, "ReturnHome");
		myReturnHomeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				home.set(!home.get());
			}
		});
		myReturnHomeButton.setTranslateX(DEFAULT_BUTTON_SIZE * 5);
		return myReturnHomeButton;
	}

	private squareButton setUpStepButton() {
		squareButton myStepButton = new squareButton(DEFAULT_BUTTON_SIZE, "Step");
		myStepButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				step.set(!step.get());
			}
		});
		myStepButton.setTranslateX(DEFAULT_BUTTON_SIZE * 4);
		return myStepButton;
	}

	private squareButton setUpRestartButton() {
		squareButton myRestartButton = new squareButton(DEFAULT_BUTTON_SIZE, "Restart");
		myRestartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				broadcastRestart();
			}
		});
		myRestartButton.setTranslateX(DEFAULT_BUTTON_SIZE * 3);
		return myRestartButton;
	}

	private squareButton setUpFastForwardButton() {
		squareButton myFastForwardButton = new squareButton(DEFAULT_BUTTON_SIZE, "FastForward");
		myFastForwardButton.setTranslateX(DEFAULT_BUTTON_SIZE * 2);
		myFastForwardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				changeSpeed(DEFAULT_SPEED_CHANGE);
			}
		});
		return myFastForwardButton;
	}

	private squareButton setUpPlayButton() {
		squareButton myPlayButton = new squareButton(DEFAULT_BUTTON_SIZE, "Play");
		myPlayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (playing.get()) {
					playing.set(false);
					myPlayButton.setImage("Play");
				} else {
					playing.set(true);
					myPlayButton.setImage("Pause");
				}
			}

		});
		myPlayButton.setTranslateX(DEFAULT_BUTTON_SIZE);
		return myPlayButton;
	}

	private squareButton setUpSlowdownButton() {
		squareButton mySlowDownButton = new squareButton(DEFAULT_BUTTON_SIZE, "Reverse");
		mySlowDownButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				changeSpeed(-DEFAULT_SPEED_CHANGE);
			}
		});
		return mySlowDownButton;
	}

	private Rectangle setUpControlsBanner() {
		Rectangle myControlsBanner = new Rectangle(myControlsContainerWidth, myControlsContainerHeight);
		myControlsBanner.setFill(ACCENT_COLOR);
		myControlsBanner.setX(0);
		myControlsBanner.setY(myHeaderHeight + myGrid.getHeightInPixels());
		return myControlsBanner;
	}

	private void changeSpeed(double d) {
		mySpeed.set(mySpeed.get() + d);
	}
	/**
	 * Returns the DoubleProperty that is changed pertaining to the speed of the simulation
	 * @return DoubleProperty mySpeed: the doubleProperty pertaining to the speed othe the simulation
	 */
	public DoubleProperty getMySpeed() {
		return mySpeed;
	}
	/**
	 * Returns the grid being used in this simulation
	 * @return Grid myGrid: The grid being used in this simulation
	 */
	public Grid getGrid() {
		return myGrid;
	}

	private void setUpGridContainer() {
		myGridContainer = new Group();
		int n = 0;
		while (n < myGrid.getGroup().getChildren().size()) {
			Node temp = myGrid.getGroup().getChildren().get(n);
			temp.setTranslateY(temp.getTranslateY() + myHeaderHeight);
			myGridContainer.getChildren().add(temp);
		}
	}
	
//	public void sideBySideView() {
//		Group gridcontainer2 = new Group();
//		int n = 0;
//		while (n < myGrid.getGroup().getChildren().size()) {
//			System.out.println("Side by side view executed");
//			Node temp = myGrid.getGroup().getChildren().get(n);
//			temp.setTranslateY(temp.getTranslateY() + myHeaderHeight);
//			temp.setTranslateX(temp.getTranslateX() + myGrid.getWidth());
//			myGridContainer.getChildren().add(temp);
//			n++;
//		}
//		myRoot.getChildren().addAll(gridcontainer2);
//	}

	private class squareButton extends ImageView {
		squareButton(int size, String type) {
			this.setFitHeight(size);
			this.setFitWidth(size);
			setImage(type);
		}

		private void setImage(String type) {
			final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)",
					String.join("|", ImageIO.getReaderFileSuffixes()));
			String label = myResources.getString(type);
			if (label.matches(IMAGEFILE_SUFFIXES)) {
				java.io.FileInputStream fis;
				try {
					fis = new FileInputStream(DEFAULT_IMG_FILEPATH + label);
					Image buttonImage = new Image(fis);
					this.setImage(buttonImage);
					this.setScaleX(DEFAULT_BUTTON_SCALE);
					this.setScaleY(DEFAULT_BUTTON_SCALE);
					this.setY(myHeaderHeight + myGrid.getHeightInPixels());
				} catch (FileNotFoundException e) {
					System.out.println("Specify an existing file path for button images.");
					// e.printStackTrace();
				}
			}

		}
	}
	/**
	 * Returns the BooleanProperty pertaining to whether or not the simulation is being played.
	 * @return BooleanProperty playing: The booleanProperty of whether or not the simulation is playing
	 */
	public BooleanProperty getPlaying() {
		return playing;
	}
	/**
	 * Returns the BooleanProperty pertaining to whether or not the simulation needs to be restarted
	 * @return BooleanProperty restart: The boolean property pertaining to the need to restart
	 */
	public BooleanProperty getRestart() {
		return restart;
	}

	private void broadcastRestart() {
		restart.set(!restart.get());
	}
	/**
	 * Returns the BooleanProperty pertaining to wether or not the simulation needs to go home
	 * @return BooleanProperty goHome: The booleanProperty that determines whether or not to return to the home screen.
	 */
	public BooleanProperty goHome() {
		return home;
	}
	/**
	 * Returns the BooleanProperty pertaining to whether or not the simulation needs to go at a stepping speed;
	 * @return BooleanProperty step: The booleanProperty that determines whether or not to move at stepping speed
	 */
	public BooleanProperty step() {
		return step;
	}
	/**
	 * Returns the BooleanProperty that determines whether or not to open a new window
	 * @return BooleanProperty newWindow: The boolean property that determines opening a new window
	 */
	public BooleanProperty getWindow() {
		return newWindow;
	}
	/**
	 * Returns the BooleanProperty that determines whether or not to save the simulation
	 * @return BooleanProperty save: The booleanProperty that dictates when to save
	 */
	public BooleanProperty getSave() {
		return save;
	}

}
