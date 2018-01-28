package cellsociety_team17;

import java.io.File;

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
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
			//TODO: Complete Start
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
	
	private grid readInput(File f) {
		//TODO: completeReadInput
	}
}
