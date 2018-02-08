package cellsociety_team17;

import cellsociety_team17.Cell;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import javafx.util.Pair;

public class Main extends Application {
	private Stage myPrimaryStage;
	private Scene myScene;
	private Group myRoot;
	private Grid myGrid;
	private int mySimulationType;
	private String mySimulationTitle;
	private ArrayList<Cell> activeCells = new ArrayList<Cell>();
	private ArrayList<Cell> myCells = new ArrayList<Cell>();
	private HashMap myAttributes = new HashMap();
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
			showSplashScreen();
	}
	
	private void showSplashScreen() {
		SplashScreen mySplash;
		try {
			mySplash = new SplashScreen();
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
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
			mySimulationView.getRestart().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					try {
						myTimeline.stop();
						startSimulation(readInput(myXmlFile));
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
				
			});
			mySimulationView.step().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					try {
						System.out.print(myTimeline.getKeyFrames().get(0).getTime());
						myTimeline.setRate(.1);
						myTimeline.play();
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
				
			});
			mySimulationView.goHome().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					try {
						myTimeline.pause();
						showSplashScreen();
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
				
			});
	}
	
	private void setFile(String s) {
		myXmlFile = new File(s);
	}
	
	private Grid readInput(File f) throws Exception {
		myCells = new ArrayList<Cell>();
		activeCells = new ArrayList<Cell>();
		
		
		DocumentBuilderFactory myDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder myDocumentBuilder = myDocumentBuilderFactory.newDocumentBuilder();
		Document myDocument = myDocumentBuilder.parse(f);
		NodeList attrList = myDocument.getElementsByTagName("meta").item(0).getChildNodes();
		for(int i = 0; i < attrList.getLength(); i++) {
			if(!attrList.item(i).getNodeName().equals("#text")) {
				if(isNumeric(attrList.item(i).getTextContent())) {
					 myAttributes.put(attrList.item(i).getNodeName(), getDoubleFromXML(myDocument, attrList.item(i).getNodeName()));
				} else {
					myAttributes.put(attrList.item(i).getNodeName(), attrList.item(i).getTextContent());
				}
			}
		}
		
		try {
		mySimulationType = setSimulationType(myAttributes.get("simulationType").toString()); 
		} catch(Exception e) {
			System.out.println("Invalid or missing Simulation Type");
		}
		
		try {
			mySimulationTitle = myAttributes.get("title").toString(); 
			} catch(Exception e) {
				System.out.println("Invalid or missing Title");
			}
		
		
		int myWidth = 0;
		int myHeight = 0;
		try {
			myWidth = (int) ((double) myAttributes.get("width")); 
			myHeight = (int) ((double) myAttributes.get("height"));
			} catch(Exception e) {
				System.out.println("Invalid or missing dimensions");
		}
		
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
						((FireCell) tempCell).setMyProbability(getDoubleFromXML(myDocument, "probability"));
						myCells.add(tempCell);
						if(cState == 2) {
							activeCells.add(tempCell);
						}
						break;	
					case 1: // Modified by Judi at 6:28PM 2/5/2018
						Cell tempGCell = new GameOfLifeCell(cRow, cColumn, cState);
						myCells.add(tempGCell);
						if(cState==1) {
							activeCells.add(tempGCell);
						}
						break;
					case 2: // Modified by Judi at 6:52PM 2/5/2018
						Cell tempWCell = new WatorCell(cRow, cColumn, cState);
						myCells.add(tempWCell);
						if(cState!=0) {
							activeCells.add(tempWCell);
						}
						break;
					case 3:
						double mThreshold = getDoubleFromXML(myDocument, "probability");
						Cell tempSCell = new SegregationCell(cRow, cColumn, cState, (float) mThreshold);
						myCells.add(tempSCell);
						if(cState != 0) {
							activeCells.add(tempSCell);
						}
						
						//System.out.println(tempSCell.myRectangle.toString());
					}
				}
			}
			
			
		}
		
		myGrid = new Grid(myHeight, myWidth, myCells);
		if(myAttributes.containsKey("shape")) {
			switch(myAttributes.get("shape").toString()){
				case "triangle":
					myGrid.setMyShape(new Triangle());
			}
		}
		return myGrid;
		
	}


	private boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		}
		catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private int setSimulationType(String type) throws Exception {
		switch(type.toLowerCase()){
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
	
	
	private double getDoubleFromXML(Document d, String s) {
		String nodeString = d.getElementsByTagName(s).item(0).getTextContent();
		return Double.parseDouble(nodeString);
	}
}
