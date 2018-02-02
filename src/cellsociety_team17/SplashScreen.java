package cellsociety_team17;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class SplashScreen extends Application {

	public static final String DEFAULT_SIMULATION_OPTIONS_FILE = "data/AvailableSimulations.txt";
	public static final int DEFAULT_SCREEN_SIZE = 400; 
	public static final Paint BACKGROUND_COLOR = Color.WHITE;
	private Stage myStage; 
	private GridPane myGridPane; 

	public SplashScreen() throws FileNotFoundException {
		this(DEFAULT_SIMULATION_OPTIONS_FILE);
	}
	
	public SplashScreen(String availableSimulationsFile) throws FileNotFoundException {
		myStage = new Stage(); 
		Pane root = new Pane(); 
		GridPane gridpane = new GridPane(); 
		Scene scene = new Scene(root, DEFAULT_SCREEN_SIZE, DEFAULT_SCREEN_SIZE, BACKGROUND_COLOR);
		Scanner readSimulationsFile = null; 
		try{
			readSimulationsFile = new Scanner(new File(availableSimulationsFile));
		}
		catch (IOException e){
			readSimulationsFile = new Scanner(new File(DEFAULT_SIMULATION_OPTIONS_FILE));
			throw new FileNotFoundException("Could not find file named "+availableSimulationsFile+". Using default simulation options file.");
		}
		int rowIndex = 0; 
		while (readSimulationsFile.hasNextLine()) {
			Button simulationButton = new Button(readSimulationsFile.nextLine());
			gridpane.add(simulationButton, 1, rowIndex);
			rowIndex++; 
		}
		myGridPane = gridpane;
		
		root.getChildren().add(myGridPane);
		myStage.setScene(scene);
	}
	
//	private String getUserSelection() {
//	}
//	
//	private void handleMouseInput(double xPos, double yPos) {
//		
//	}

	public Stage getStage() {
		return myStage; 
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		myStage.show();
	}
 	
}
