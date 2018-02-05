package cellsociety_team17;

import cellsociety_team17.Cell;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private Stage myPrimaryStage;
	private Scene myScene;
	private Group myRoot;
	private Grid myGrid;
	private int mySimulationType;
	private String mySimulationTitle;
	private ArrayList<Cell> activeCells = new ArrayList<Cell>();
	private ArrayList<Cell> myCells = new ArrayList<Cell>();
	private File myXmlFile;
	private Timeline myTimeLine;
	private static String DEFAULT_FILEPATH = "data/";
	private final int FRAMES_PER_SECOND = 10;
	private final long MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
			
			myPrimaryStage = primaryStage;
			myPrimaryStage.setResizable(false);
			primaryStage.setTitle("Team 17 -- Cell Society");
			primaryStage.show();
			//startSimulation(myGrid);
			SplashScreen mySplash = new SplashScreen();
			myScene = mySplash.getScene();
			myPrimaryStage.setScene(myScene);
			mySplash.userSelectionReceivedProperty().addListener(new ChangeListener<Boolean>(){

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					setFile(DEFAULT_FILEPATH + mySplash.getUserSelection() + ".xml");
					try {
						startSimulation(readInput(myXmlFile));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}});
	}
	
	/**
	 * 
	 * @param timeElapsed
	 * @return
	 */
	private void Step(Double timeElapsed) {
		activeCells = myGrid.updateCells(activeCells);
		
	}
	
	/**
	 * 
	 * @param e
	 */
	private void handleMouseInput(MouseEvent e) {
		//TODO: Complete handleMouseInput
	}
	
	
	private void startSimulation(Grid G) {
		SimulationView mySimulationView = new SimulationView(myGrid, mySimulationTitle);
		myScene = mySimulationView.getScene();
		//System.out.println(myScene.getWidth());
		myPrimaryStage.setScene(myScene);
		
		
		//Timeline
				KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
		                e -> Step(SECOND_DELAY));
				Timeline myTimeline = new Timeline();
				myTimeline.setCycleCount(Timeline.INDEFINITE);
				myTimeline.getKeyFrames().add(frame);
				myTimeline.pause();
			mySimulationView.getPlaying().addListener(new ChangeListener<Boolean>(){
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					try {
						if(mySimulationView.getPlaying().get()) {
							myTimeline.play();
						} else {
							myTimeline.pause();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}});
			mySimulationView.getMySpeed().addListener(new ChangeListener<Object>() {

				@Override
				public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
					myTimeline.setRate(mySimulationView.getMySpeed().get());
					
				}

				
			});
	}
	
	private void setFile(String s) {
		myXmlFile = new File(s);
	}
	
	private Grid readInput(File f) throws Exception {
		DocumentBuilderFactory myDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder myDocumentBuilder = myDocumentBuilderFactory.newDocumentBuilder();
		Document myDocument = myDocumentBuilder.parse(f);
		
		mySimulationType = getSimulationType(myDocument);
		try {
			mySimulationTitle = myDocument.getElementsByTagName("title").item(0).getTextContent();
		} catch(Exception e) {
			throw new Exception("No <Title> tag in the XML");
		}
		
		int myWidth = getIntFromXML(myDocument, "width");
		int myHeight = getIntFromXML(myDocument, "height");
		
		for(int i = 0; i < myDocument.getElementsByTagName("row").getLength(); i++) {
			Node currentNode = myDocument.getElementsByTagName("row").item(i);
			int cRow = i;
			int cColumn = -1;
			int cState = -1;
			int count = 0;
			for(int j = 0; j < currentNode.getChildNodes().getLength(); j++) {
				if(currentNode.getChildNodes().item(j).getNodeName().equals("cell")) {
					cColumn = count;
					count++;
					cState = Integer.parseInt(currentNode.getChildNodes().item(j).getTextContent());
					//System.out.println(cRow + ", " + cColumn + ", "+ cState);
					
					//TODO:change to use Java Reflection
					switch(mySimulationType){
					case 0:
						Cell tempCell = new FireCell(cRow, cColumn, cState);
						myCells.add(tempCell);
						if(cState == 2) {
							activeCells.add(tempCell);
						}
						break;	
					case 1:
						myCells.add(new GameOfLifeCell(cRow, cColumn, cState));
						break;
					case 2:
						myCells.add(new WatorCell(cRow, cColumn, cState));
						break;
					case 3:
						myCells.add(new SegregationCell(cRow, cColumn, cState));
						break;
					}
				}
			}
			
			
		}
		
		myGrid = new Grid(myHeight, myWidth, myCells);
		return myGrid;
		
	}

	private int getSimulationType(Document d) throws Exception {
		String typeString = d.getElementsByTagName("simulationType").item(0).getTextContent().toLowerCase();
		switch(typeString){
			case "fire":
				return 0;
			case "game of life":
				return 1;
			case "wator":
				return 2;
			case "segregation":
				return 3;
		}
		throw new Exception("No Simulation Type Defined");
	}
	
	private int getIntFromXML(Document d, String s) {
		String nodeString = d.getElementsByTagName(s).item(0).getTextContent();
		return Integer.parseInt(nodeString);
	}
}
