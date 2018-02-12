/**
 * Screen to receive user input for desired simulation to view,
 * and whether to load initial configurations from file or randomly.
 * 
 * Use by initializing and accepting default filepath (no-arg constructor), 
 * 		or specifying in 1-arg constructor.
 * Set up listener to the BooleanProperty that indicates 
 * 		that the user selected a simulation (userSelectionReceivedProperty).
 * 
 * Assumes that an outside class is listening to when user selection is received, 
 * 		and coordinating transitions between screens appropriately 
 * 		so that user can view simulation that is selected.
 * 
 * @author Susie Choi
 * @author Collin Brown
 */

package cellsociety_team17;

import java.util.ResourceBundle;
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

	public static final String DEFAULT_RESOURCE_PACKAGE = "properties/";
	public static final String DEFAULT_OPTIONS_FILENAME = "Simulations";
	public static final String DEFAULT_SIMULATION_PREFIX = "Simulation";
	public static final int DEFAULT_NUM_SIMULATIONS = 5; 
	public static final String WILDCARD_INDICATOR = "WildCard";
	public static final int DEFAULT_SCREEN_SIZE = 400;
	public static final Paint BACKGROUND_COLOR = Color.WHITE;
	// private Stage myStage;
	private ResourceBundle myResources; 
	private Scene myScene;
	private GridPane myGridPane;
	private Pane myPane;
	private int myNumSimulations; 
	private String myUserSelection;
	private BooleanProperty userSelectionRecieved = new SimpleBooleanProperty();

	/**
	 * No-arg constructor calls multi-arg SplashScreen constructor 
	 * 		using default filepath for simulation name Properties
	 * 		and default number of simulations
	 */
	public SplashScreen() {
		this(DEFAULT_RESOURCE_PACKAGE + DEFAULT_OPTIONS_FILENAME, DEFAULT_NUM_SIMULATIONS);
	}

	/**
	 * Creates SplashScreen containing buttons for the number of specified simulations,
	 * 		with names listed in the corresponding Properties file.
	 * @param String availableSimulationsFile: the name of the properties file.
	 * @param int numSimulations: the number of simulations in the file-
	 * 		  assumes that Properties are labeled as "Simulation1", "Simulation2", "Simulation3", etc.
	 */
	public SplashScreen(String availableSimulationsFile, int numSimulations) {
		myNumSimulations = numSimulations;
		
		myResources = ResourceBundle.getBundle(availableSimulationsFile);
		myPane = new Pane();
		myGridPane = new GridPane();
		myScene = new Scene(myPane, DEFAULT_SCREEN_SIZE, DEFAULT_SCREEN_SIZE, BACKGROUND_COLOR);

		int rowIndex = 0;
		int colIndex = 0;

		for (int i=0; i<myNumSimulations; i++) {
			String simulationName = myResources.getString(DEFAULT_SIMULATION_PREFIX+i);
			Button simulationButton = new Button(simulationName);
			Button wildCardSimulationButton = new Button(WILDCARD_INDICATOR+simulationName);
			simulationButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent click) {
					handleUserSelection(simulationButton);
				}
			});
			wildCardSimulationButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent click) {
					handleUserSelection(wildCardSimulationButton);
				}
			});
			myGridPane.add(simulationButton, colIndex, rowIndex);
			myGridPane.add(wildCardSimulationButton, colIndex+1, rowIndex);
			rowIndex++;
		}
		myPane.getChildren().add(myGridPane);
		myUserSelection = "";
	}

	private void handleUserSelection(Button selectedButton) {
		String selectedSimulation = selectedButton.getText() + "Cell";
		myUserSelection = selectedSimulation;
		userSelectionReceived();
	}

	
	private boolean userSelectionReceived() {
		userSelectionRecieved.set(myUserSelection.length() > 0);
		return (myUserSelection.length() > 0);
	}

	/**
	 * Returns user selection for simulation to view. 
	 * Useful for receiving user selection information after Listener indicates that selection was received. 
	 * @return String, name of selected simuation (e.g. "Segregation").
	 */
	public String getUserSelection() {
		return myUserSelection;
	}

	/**
	 * Returns scene including buttons for SplashScreen obj.
	 * Useful for setting scene of Stage to display SplashScreen obj.
	 * @return Scene containing GridPane with buttons for SplashScreen .
	 */
	public Scene getScene() {
		return myScene;
	}

	/**
	 * Returns BooleanProperty to indicate whether user selected simulation to view.
	 * Useful for calling class' listener to identify when user selection was received and, thus, 
	 * 		scene transition is necessary. 
	 * @return BooleanProperty, indicating whether user selected simulation to view. 
	 */
	public BooleanProperty userSelectionReceivedProperty() {
		return userSelectionRecieved;
	}

}
