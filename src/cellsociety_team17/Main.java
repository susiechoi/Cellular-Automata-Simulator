package cellsociety_team17;

import cellsociety_team17.Cell;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private Stage myPrimaryStage;
	private Scene myScene;
	private Grid myGrid;
	private int mySimulationType;
	private String mySimulationTitle;
	private List<Cell> activeCells = new ArrayList<Cell>();
	private List<Cell> myCells = new ArrayList<Cell>();
	@SuppressWarnings("rawtypes")
	private HashMap myAttributes = new HashMap();
	private File myXmlFile;
	public static final String DEFAULT_FILEPATH = "data/";
	public static final int FRAMES_PER_SECOND = 10;
	public static final long MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
	public static final String WILDCARD_INDICATOR = "WildCard";
	private boolean mySimulationWild; 
	private int windowCount;
	private String[] mArgs;
	private Timeline myTimeline;

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
		showSplashScreen(myPrimaryStage);
	}

	private void showSplashScreen(Stage relevantStage) {
		SplashScreen mySplash;
		try {
			mySplash = new SplashScreen();
			myScene = mySplash.getScene();
			relevantStage.setScene(myScene);
			mySplash.userSelectionReceivedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					String userSelection = mySplash.getUserSelection();
					showOptionsScreen(userSelection, relevantStage);
				}
			});
		} catch (FileNotFoundException e1) {
			System.out.print("File not found!!!");
			LOGGER.log(Level.FINE, e1.getMessage());
			e1.printStackTrace();
		}
	}

	private void showOptionsScreen(String simulationSelection, Stage relevantStage) {
		OptionsScreen myOptions; 
		myOptions = new OptionsScreen();
		myScene = myOptions.getScene();
		relevantStage.setScene(myScene);
		myOptions.neighborSelectionReceivedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				String neighborSelection = myOptions.getNeighborSelection();
				myOptions.toroidalSelectionReceivedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
						boolean toroidalSelection = myOptions.getToroidalSelection();
						showSimView(simulationSelection, neighborSelection, toroidalSelection, relevantStage);
					}
				});
			}
		});
	}
	
	private void showSimView(String simulationSelection, String neighborSelection, boolean toroidalSelection, Stage relevantStage) {
		if (simulationIsWild(simulationSelection)) {
			mySimulationWild = true; 
			String simulationCellToAccess = simulationSelection.substring(WILDCARD_INDICATOR.length()); 
			RandomizedInitConfig wildCardSimulation = new RandomizedInitConfig(simulationCellToAccess, neighborSelection, toroidalSelection);
			startWildSimulation(simulationSelection, wildCardSimulation, relevantStage);
		}
		else {
			mySimulationWild = false; 
			setFile(DEFAULT_FILEPATH + simulationSelection + ".xml");
			try {
				startSimulation(readInput(myXmlFile, neighborSelection, toroidalSelection), relevantStage);
			} 
			catch (Exception e) {
				System.out.println("error starting the simulation");
				LOGGER.log(Level.FINE, e.getMessage());
			}
		}
	}

	private boolean simulationIsWild(String selectedSimulationName) {
		return (selectedSimulationName.length() >  WILDCARD_INDICATOR.length() && selectedSimulationName.substring(0, WILDCARD_INDICATOR.length()).equals(WILDCARD_INDICATOR));
	}

	private void startWildSimulation(String wildSimulationTitle, RandomizedInitConfig wildCardSimulation, Stage relevantStage) {
		mySimulationTitle = wildSimulationTitle;
		myGrid = wildCardSimulation.getGrid();
		startSimulation(myGrid, relevantStage);
		activeCells = wildCardSimulation.getActiveCells();
	}

	/**
	 * 
	 * @param timeElapsed
	 * @return
	 */
	private void Step(Double timeElapsed) {
		activeCells = myGrid.updateCells(activeCells);
		if(activeCells.size() == 0) {
			myTimeline.stop();
		}

	}

	private void startSimulation(Grid G, Stage relevantStage) {
		SimulationView mySimulationView = new SimulationView(myGrid, mySimulationTitle);
		myScene = mySimulationView.getScene();
		// System.out.println(myScene.getWidth());
		relevantStage.setScene(myScene);

		// Timeline
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> Step(SECOND_DELAY));
		myTimeline = new Timeline();
		myTimeline.setCycleCount(Animation.INDEFINITE);
		myTimeline.getKeyFrames().add(frame);
		myTimeline.pause();
		setUpChangeListeners(mySimulationView, myTimeline, relevantStage);
	}

	private void setUpChangeListeners(SimulationView mySimulationView, Timeline myTimeline, Stage relevantStage) {
		setUpPlayingChangeListener(mySimulationView, myTimeline);
		setUpSpeedChangeListener(mySimulationView, myTimeline);
		setUpRestartChangeListener(mySimulationView, myTimeline, relevantStage);
		setUpStepChangeListener(mySimulationView, myTimeline);
		setUpHomeListener(mySimulationView, myTimeline);
		setUpSaveListener(mySimulationView, myTimeline);
		setUpWindowListener(mySimulationView, myTimeline);
	}

	private void setUpSaveListener(SimulationView mySimulationView, Timeline myTimeline) {
		mySimulationView.getSave().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				try {
					myTimeline.pause();
					XMLSaver myXMLSaver = new XMLSaver(myGrid, myAttributes);
					myXMLSaver.save();
				} catch (Exception e) {
					System.out.println("Error returning to Home Screen");
				}
			}});
			}

	private void setUpWindowListener(SimulationView mySimulationView, Timeline myTimeline) {
		mySimulationView.getWindow().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				try {
					String[] temp = {};
					new Main().start(new Stage());;
				} catch (Exception e) {
					System.out.println("Error opening new window");
					e.printStackTrace();
					LOGGER.log(Level.FINE, e.getMessage());
				}				
			}

		});
	}

	private void setUpHomeListener(SimulationView mySimulationView, Timeline myTimeline) {
		mySimulationView.goHome().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				try {
					myTimeline.pause();    
					showSplashScreen(myPrimaryStage);
				} catch (Exception e) {
					System.out.println("Error returning to Home Screen");
					LOGGER.log(Level.FINE, e.getMessage());
				}				
			}

		});
	}

	private void setUpStepChangeListener(SimulationView mySimulationView, Timeline myTimeline) {
		mySimulationView.step().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				try {
					//System.out.print(myTimeline.getKeyFrames().get(0).getTime());
					myTimeline.setRate(.1);
					myTimeline.play();
				} catch (Exception e) {
					System.out.println("Error stepping through project");
					e.printStackTrace();
					LOGGER.log(Level.FINE, e.getMessage());
				}				
			}

		});
	}

	private void setUpRestartChangeListener(SimulationView mySimulationView, Timeline myTimeline, Stage relevantStage) {
		String currNeighborType = mySimulationView.getGrid().getNeighborType(); 
		boolean currToroidality = mySimulationView.getGrid().getToroidal();
		System.out.println(mySimulationView.getGrid().getNeighborType());
		mySimulationView.getRestart().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				try {
					myTimeline.stop();
					if (mySimulationWild) {
						String simulationCellToAccess = mySimulationTitle.substring(WILDCARD_INDICATOR.length()); 
						RandomizedInitConfig wildCardSimulation = new RandomizedInitConfig(simulationCellToAccess, currNeighborType, currToroidality);
						startWildSimulation(mySimulationTitle, wildCardSimulation, relevantStage);
			
					}
					else {
						startSimulation(readInput(myXmlFile, currNeighborType, currToroidality), relevantStage);
					}
				} catch (Exception e) {
					System.out.print("Error Starting Simulation");
					LOGGER.log(Level.FINE, e.getMessage());
				}				
			}

		}); 
	}

	private void setUpSpeedChangeListener(SimulationView mySimulationView, Timeline myTimeline) {
		mySimulationView.getMySpeed().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				myTimeline.setRate(mySimulationView.getMySpeed().get());
			}
		});
	}

	private void setUpPlayingChangeListener(SimulationView mySimulationView, Timeline myTimeline) {
		mySimulationView.getPlaying().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				try {
					if (mySimulationView.getPlaying().get()) {
						myTimeline.play();
					} else {
						myTimeline.pause();
					}
				} catch (Exception e) {
					System.out.println("Error in playing/pausing the timeline");
					LOGGER.log(Level.FINE, e.getMessage());
				}
			}
		});
	}

	private void setFile(String s) {
		myXmlFile = new File(s);
	}

	private Grid readInput(File f, String neighborSelection, boolean toroidalSelection) throws Exception {
		myCells = new ArrayList<Cell>();
		activeCells = new ArrayList<Cell>();

		Document myDocument = buildDocument(f);
		NodeList attrList = myDocument.getElementsByTagName("meta").item(0).getChildNodes();
		for (int i = 0; i < attrList.getLength(); i++) {
			if (!attrList.item(i).getNodeName().equals("#text")) {
				if (isNumeric(attrList.item(i).getTextContent().toString())) {		
					myAttributes.put(attrList.item(i).getNodeName(),
							getDoubleFromXML(myDocument, attrList.item(i).getNodeName()));
				} else {
					myAttributes.put(attrList.item(i).getNodeName(), attrList.item(i).getTextContent());
				}
			}
		}

		setMySimulationType();
		setMySimulationTitle();

		int myWidth = 0;
		int myHeight = 0;
		try {
			myWidth = (int) ((double) myAttributes.get("width"));
			myHeight = (int) ((double) myAttributes.get("height"));
		} catch (Exception e) {
			System.out.println("Invalid or missing dimensions");
			LOGGER.log(Level.FINE, e.getMessage());
		}

		createCells(myDocument);

		myGrid = new Grid(myHeight, myWidth, myCells, neighborSelection, toroidalSelection);
		if (myAttributes.containsKey("shape")) {
			switch (myAttributes.get("shape").toString()) {
			case "triangle":
				myGrid.setMyShape(new Triangle());
			}
		}
		return myGrid;

	}

	private void createCells(Document myDocument) {
		for (int i = 0; i < myDocument.getElementsByTagName("row").getLength(); i++) {
			Node currentNode = myDocument.getElementsByTagName("row").item(i);
			int cRow = i;
			int cColumn = -1;
			int cState = -1;
			int count = 0;
			for (int j = 0; j < currentNode.getChildNodes().getLength(); j++) {
				if (currentNode.getChildNodes().item(j).getNodeName().equals("cell")) {
					cColumn = count;
					count++;
					cState = Integer.parseInt(currentNode.getChildNodes().item(j).getTextContent());
					// System.out.println(cRow + ", " + cColumn + ", "+ cState);

					// TODO:make sure to check all possible parameters for each simulation
					switch (mySimulationType) {
					case 0:
						makeFireCell(myDocument, cRow, cColumn, cState);
						break;
					case 1:
						createGameOfLifeCell(cRow, cColumn, cState);
						break;
					case 2:
						createWatorCell(cRow, cColumn, cState);
						break;
					case 3:
						createSegregationCell(myDocument, cRow, cColumn, cState);
						break;

						// System.out.println(tempSCell.myRectangle.toString());
					}
				}
			}

		}
	}

	private void createSegregationCell(Document myDocument, int cRow, int cColumn, int cState) {
		Cell tempSCell; 
		if(myAttributes.containsKey("threshold")) {
			tempSCell = new SegregationCell(cRow, cColumn, cState, (double) myAttributes.get("threshold"));
		} else {
			tempSCell = new SegregationCell(cRow, cColumn, cState);
		}
		myCells.add(tempSCell);
		if (cState != 0) {
			activeCells.add(tempSCell);
		}
	}

	private void createWatorCell(int cRow, int cColumn, int cState) {
		Cell tempWCell = new WatorCell(cRow, cColumn, cState);
		if(myAttributes.containsKey("sharkClock")) {
			((WatorCell) tempWCell).setMySharkCycles((int)myAttributes.get("sharkClock"));
		}
		if(myAttributes.containsKey("sharkEnergy")) {
			((WatorCell) tempWCell).setInitialSharkEnergy((int)myAttributes.get("sharkEnergy"));
		}
		if(myAttributes.containsKey("fishClock")) {
			((WatorCell) tempWCell).setMyfishCycles((int)myAttributes.get("fishClock"));
		}
		myCells.add(tempWCell);
		if (cState != 0) {
			activeCells.add(tempWCell);
		}
	}

	private void createGameOfLifeCell(int cRow, int cColumn, int cState) {
		Cell tempGCell = new GameOfLifeCell(cRow, cColumn, cState);
		myCells.add(tempGCell);
		if (cState == 1) {
			activeCells.add(tempGCell);
		}
	}

	private void makeFireCell(Document myDocument, int cRow, int cColumn, int cState) {
		Cell tempCell = new FireCell(cRow, cColumn, cState);
		if(myAttributes.containsKey("probability")) {
			((FireCell) tempCell).setMyProbability((double) myAttributes.get("probability"));
		}
		
		myCells.add(tempCell);
		if (cState == 2) {
			activeCells.add(tempCell);
		}
	}

	private void setMySimulationTitle() {
		try {
			mySimulationTitle = myAttributes.get("title").toString();
		} catch (Exception e) {
			System.out.println("Invalid or missing Title");
			LOGGER.log(Level.FINE, e.getMessage());
		}
	}

	private void setMySimulationType() {
		try {
			mySimulationType = setSimulationType(myAttributes.get("simulationType").toString());
		} catch (Exception e) {
			System.out.println("Invalid or missing Simulation Type");
			LOGGER.log(Level.FINE, e.getMessage());
		}
	}

	private Document buildDocument(File f) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory myDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder myDocumentBuilder = myDocumentBuilderFactory.newDocumentBuilder();
		Document myDocument = myDocumentBuilder.parse(f);
		return myDocument;
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
		switch (type.toLowerCase()) {
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
		
