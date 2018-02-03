package cellsociety_team17;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SplashScreen {

	public static final String DEFAULT_SIMULATION_OPTIONS_FILE = "data/AvailableSimulations.txt";
	public static final int DEFAULT_SCREEN_SIZE = 400; 
	public static final Paint BACKGROUND_COLOR = Color.WHITE;
	//	private Stage myStage; 
	private Scene myScene;
	private GridPane myGridPane; 
	private Pane myPane; 

	public SplashScreen() throws FileNotFoundException {
		this(DEFAULT_SIMULATION_OPTIONS_FILE);
	}

	public SplashScreen(String availableSimulationsFile) throws FileNotFoundException {
		//		myStage = new Stage(); 
		myPane = new Pane(); 
		myGridPane = new GridPane(); 
		myScene = new Scene(myPane, DEFAULT_SCREEN_SIZE, DEFAULT_SCREEN_SIZE, BACKGROUND_COLOR);
		Scanner readSimulationsFile = null; 
		try{
			readSimulationsFile = new Scanner(new File(availableSimulationsFile));
		}
		catch (IOException e){
			readSimulationsFile = new Scanner(new File(DEFAULT_SIMULATION_OPTIONS_FILE));
			throw new FileNotFoundException("Could not find file named "+availableSimulationsFile+". Using default simulation options file.");
		}
		int rowIndex = 0; 
		int colIndex = 0; 
		while (readSimulationsFile.hasNextLine()) {
			Button simulationButton = new Button(readSimulationsFile.nextLine());
			simulationButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent click) {
					getUserSelection(simulationButton);
				}
			});
			myGridPane.add(simulationButton, colIndex, rowIndex);
			rowIndex++; 
		}
		myPane.getChildren().add(myGridPane);
		//		myStage.setScene(myScene);
	}

	private String getUserSelection(Button selectedButton) {
		String selectedSimulation = selectedButton.getText()+"Cell";
		//		System.out.println(selectedSimulation);
		return selectedSimulation;
	}

	public Scene getScene() {
		return myScene;
	}

	//	public Stage getStage() {
	//	return myStage; 
	// }

	//	@Override
	//	public void start(Stage primaryStage) throws Exception {
	//		myStage.show();
	//	}
	//
	//	public static void main(String[] args) throws FileNotFoundException {
	//		launch(args);
	//	}

}
