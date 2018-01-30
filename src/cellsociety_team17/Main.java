package cellsociety_team17;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage myPrimaryStage;
	private Scene myScene;
	private Grid myGrid;
	private int mySimulatCell<T>ype;
	private ArrayList<cell> activeCells = new ArrayList<cell>;
	private File myXmlFile;
	
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
	}
	
	/**
	 * 
	 * @param timeElapsed
	 * @return
	 */
	private Object Step(Double timeElapsed) {
		//TODO: Complete Step
		return null;
	}
	
	/**
	 * 
	 * @param e
	 */
	private void handleMouseInput(MouseEvent e) {
		//TODO: Complete handleMouseInput
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
			//TODO: Parse out the row, column, and state of a cell
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
