package cellsociety_team17;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SimulationView {
	private static final double MIN_WIDTH = 100;
	private static final Paint ACCENT_COLOR = Color.LIGHTGRAY;
	private static final Paint PRIMARY_COLOR = Color.GRAY; 
	private static final double DEFAULT_SPEED = 1;
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
		
		squareButton mySlowDownButton = new squareButton(46, "reverse.png");
		mySlowDownButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				changeSpeed(-.5);	
			}
		});
		
		squareButton myPlayButton = new squareButton(46, "play.png");
		myPlayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(playing.get()) {
					playing.set(false);
					myPlayButton.setImage("play.png");
				} else {
					playing.set(true);
					myPlayButton.setImage("pause.png");
				}
			}
			
		});
		myPlayButton.setTranslateX(46);
		squareButton myFastForwardButton = new squareButton(46, "fastForward.png");
		myFastForwardButton.setTranslateX(92);
		myFastForwardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				changeSpeed(.5);			
			}
		});
		
		squareButton myRestartButton = new squareButton(46, "restart.png");
		myRestartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				broadcastRestart();			
			}
		});
		myRestartButton.setTranslateX(138);
	
		
		myControlsContainer.getChildren().add(myControlsBanner);
		myControlsContainer.getChildren().add(mySlowDownButton);
		myControlsContainer.getChildren().add(myPlayButton);
		myControlsContainer.getChildren().add(myFastForwardButton);
		myControlsContainer.getChildren().add(myRestartButton);
		
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
				this.setScaleX(.75);
				this.setScaleY(.75);
				this.setY(myHeaderHeight + myGrid.getHeightInPixels());
			  
			}
			private void setImage(String type){
				String myFilePath = "assets/IMG/" + type;
				java.io.FileInputStream fis;
				try {
					fis = new FileInputStream(myFilePath);
					Image iv = new Image(fis);
					this.setImage(iv);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
