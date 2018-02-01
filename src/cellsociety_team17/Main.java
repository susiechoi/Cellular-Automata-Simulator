package cellsociety_team17;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private Stage myPrimaryStage;
	private Scene myScene;
	private Group myRoot;
	private Grid myGrid;
	private int mySimulationType;
	private ArrayList<cell> activeCells = new ArrayList<cell>();
	private File myXmlFile;
	private Timeline myTimeLine;
	private static String FILEPATH = "/assets/test.xml";
	private final int FRAMES_PER_SECOND = 60;
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
			primaryStage.setTitle("Team 17 -- Cell Society");
			primaryStage.show();
			setFile(FILEPATH); 
			readInput(myXmlFile);
			startSimulation(myGrid);
	}
	
	/**
	 * 
	 * @param timeElapsed
	 * @return
	 */
	private void Step(Double timeElapsed) {
		//Scene
		myScene = setUpScene();
		myPrimaryStage.setScene(myScene);
		
		//Timeline
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> Step(SECOND_DELAY));
		Timeline myTimeline = new Timeline();
		myTimeline.setCycleCount(Timeline.INDEFINITE);
		myTimeline.getKeyFrames().add(frame);
		myTimeline.play();
	}
	
	/**
	 * 
	 * @param e
	 */
	private void handleMouseInput(MouseEvent e) {
		//TODO: Complete handleMouseInput
	}
	
	private Scene setUpScene() {
		myRoot = new Group();
		Scene tempScene = new Scene(myRoot, 600,600);
		//TODO: Add input buttons surrounding grid graphic
		myRoot.getChildren().add(myGrid.getGraphic());
	}
	
	private void startSimulation(grid G) {
		//TODO: completeStartSimulation
	}
	
	private void setFile(String s) {
		myXmlFile = new File(s);
	}
	
	private grid readInput(File f) {
		DocumentBuilderFactory myDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder myDocumentBuilder = myDocumentBuilderFactory.newDocumentBuilder();
		Document myDocument = myDocumentBuilder.parse(f);
		
		mySimulationType = getSimulationType(myDocument);
		int myWidth = getIntFromXML(myDocument, "width");
		int myHeight = getIntFromXML(myDocument, "height");
		
		for(int i = 0; i < myDocument.getElementsByTagName("cell").getLength(); i++) {
			Node currentNode = myDocument.getElementsByTagName("cell").item(i);
			int cRow = Integer.parseInt(currentNode.getAttributes().getNamedItem("row").toString());
			int cColumn = Integer.parseInt(currentNode.getAttributes().getNamedItem("column").toString());
			int cState = Integer.parseInt(currentNode.getNodeValue());
			activeCells.add(new cell(cRow, cColumn, cState));
		}
		
		myGrid = new Grid(myHeight, myWidth, mySimulationType, activeCells);
		return myGrid;
		
	}

	private int getSimulationType(Document d) throws Exception {
		String typeString = d.getElementsByTagName("simulationType").item(0).getNodeValue();
		switch(typeString){
			case "fire":
				return 0;
			case "Game Of Life":
				return 1;
			case "Wator":
				return 2;
		}
		throw new Exception("No Simulation Type Defined");
	}
	
	private int getIntFromXML(Document d, String s) {
		String nodeString = d.getElementsByTagName(s).item(0).getNodeValue();
		return Integer.parseInt(nodeString);
	}
}
