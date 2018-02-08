package cellsociety_team17;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
	public static final String DEFAULT_PATH = "data/";
	public static final int DEFAULT_SCREEN_SIZE = 400; 
	public static final Paint BACKGROUND_COLOR = Color.WHITE;
	//	private Stage myStage; 
	private Scene myScene;
	private GridPane myGridPane; 
	private Pane myPane; 
	private String myUserSelection; 
	private BooleanProperty userSelectionRecieved = new SimpleBooleanProperty();

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
			System.out.println("Could not find file named "+availableSimulationsFile+". Using default simulation options file.");
		}
		int rowIndex = 0; 
		int colIndex = 0; 
		while (readSimulationsFile.hasNextLine()) {
			Button simulationButton = new Button(readSimulationsFile.nextLine());
			simulationButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent click) {
					handleUserSelection(simulationButton);
				}
			});
			myGridPane.add(simulationButton, colIndex, rowIndex);
			rowIndex++; 
		}
		myPane.getChildren().add(myGridPane);
		//		myStage.setScene(myScene);
		myUserSelection = "";
	}

	public void handleUserSelection(Button selectedButton) {
		String selectedSimulation = selectedButton.getText()+"Cell";
//				System.out.println(selectedSimulation);
		myUserSelection = selectedSimulation;
//		System.out.println(myUserSelection);
		userSelectionReceived();
	}
		
	public boolean userSelectionReceived() {
		userSelectionRecieved.set(myUserSelection.length() > 0);
		return (myUserSelection.length() > 0); 
	}
	
	public String getUserSelection() {
		return myUserSelection;
	}

	public Scene getScene() {
		return myScene;
	}
	//	public Stage getStage() {
	//	return myStage; 
	// }

	public BooleanProperty userSelectionReceivedProperty() {
		return userSelectionRecieved;
	}

	//	@Override
	//	public void start(Stage primaryStage) throws Exception {
	//		myStage.show();
	//	}
	//
	//	public static void main(String[] args) throws FileNotFoundException {
	//		launch(args);
	//	}

}
