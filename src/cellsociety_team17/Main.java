package cellsociety_team17;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage myPrimaryStage;
	private Scene myScene;
	//private Grid myGrid;
	private int mySimulationType;
	//private ArrayList<cell> activeCells;
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
		
	}

	private int getSimulationType(Document d) {
		String typeString = d.getElementsByTagName("SimulationType").item(0).getNodeValue();
		switch(typeString){
			case "fire":
				return 0;
			case "Game Of Life":
				return 1;
			case "Wator":
				return 2;
		}
		return -1;
	}
}
