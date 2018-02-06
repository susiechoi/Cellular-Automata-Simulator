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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

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
	private Grid myGrid;
	private Group myRoot;
	private Group myHeader;
	private Group myGridContainer;
	private Group myControlsContainer;
	private Scene myScene;
	private String mySimulationTitle;
	private BooleanProperty playing = new SimpleBooleanProperty();
	private BooleanProperty restart = new SimpleBooleanProperty();
	private DoubleProperty mySpeed = new SimpleDoubleProperty();

	public SimulationView(Grid g, String simulationTitle) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+DEFAULT_LANGUAGE);
		mySimulationTitle = simulationTitle;
		myGrid = g;
		myHeaderHeight = 25;
		myControlsContainerHeight = 50;
		myHeight = myHeaderHeight + myGrid.getHeightInPixels() + myControlsContainerHeight;
		mySpeed.set(DEFAULT_SPEED);

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

		
		squareButton mySlowDownButton = new squareButton(DEFAULT_BUTTON_SIZE, "Reverse");
		mySlowDownButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				changeSpeed(-DEFAULT_SPEED_CHANGE);	
			}
		});

		squareButton myPlayButton = new squareButton(DEFAULT_BUTTON_SIZE, "Play");
		myPlayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(playing.get()) {
					playing.set(false);
					myPlayButton.setImage("Play");
				} else {
					playing.set(true);
					myPlayButton.setImage("Pause");
				}
			}
			
		});
		myPlayButton.setTranslateX(DEFAULT_BUTTON_SIZE);

		squareButton myFastForwardButton = new squareButton(DEFAULT_BUTTON_SIZE, "FastForward");
		myFastForwardButton.setTranslateX(DEFAULT_BUTTON_SIZE * 2);
		myFastForwardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				changeSpeed(DEFAULT_SPEED_CHANGE);			
			}
		});
		
		squareButton myRestartButton = new squareButton(DEFAULT_BUTTON_SIZE, "Restart");
		myRestartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				broadcastRestart();			
			}
		});
		myRestartButton.setTranslateX(DEFAULT_BUTTON_SIZE * 3);
		
		squareButton myStepButton = new squareButton(DEFAULT_BUTTON_SIZE, "Step");
		myStepButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
							
			}
		});
		myStepButton.setTranslateX(DEFAULT_BUTTON_SIZE * 4);
		
		squareButton myReturnHomeButton = new squareButton(DEFAULT_BUTTON_SIZE, "ReturnHome");
		myReturnHomeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
						
			}
		});
		myReturnHomeButton.setTranslateX(DEFAULT_BUTTON_SIZE * 5);

		myControlsContainer.getChildren().add(myControlsBanner);
		myControlsContainer.getChildren().add(mySlowDownButton);
		myControlsContainer.getChildren().add(myPlayButton);
		myControlsContainer.getChildren().add(myFastForwardButton);
		myControlsContainer.getChildren().add(myRestartButton);
		myControlsContainer.getChildren().add(myStepButton);
		myControlsContainer.getChildren().add(myReturnHomeButton);
	}
	
	protected void changeSpeed(double d) {
		mySpeed.set(mySpeed.get()+ d);
	}

	public DoubleProperty getMySpeed() {
		return mySpeed;
	}

	private void setUpGridContainer() {
		myGridContainer = new Group();
		int n = 0;
		while(n < myGrid.getGroup().getChildren().size()) {
			Node temp = myGrid.getGroup().getChildren().get(n);
			temp.setTranslateY(temp.getTranslateY()+myHeaderHeight);
			myGridContainer.getChildren().add(temp);
		}
	}

	private class squareButton extends ImageView {
		squareButton(int size, String type){
			this.setFitHeight(size);
			this.setFitWidth(size);			
			setImage(type);
		}
		private void setImage(String type) {
			final String IMAGEFILE_SUFFIXES =
					String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
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
				}
				catch (FileNotFoundException e) {
					System.out.println("Specify an existing file path for button images.");
//					e.printStackTrace();
				}
			}
		
		}
	}
	
	public BooleanProperty getPlaying() {
		return playing;
	}
	public BooleanProperty getRestart() {
		return restart;
	}
	private void broadcastRestart() {
		restart.set(!restart.get());
	}

}

