/**
 * Displays user options for/receives user selection for neighborhood shape/type and toroidality for Simulation.
 *
 * Use by initializing and accepting default options filepath (no-arg constructor), 
 * 		or specifying in 1-arg constructor.
 * Set up listener to the BooleanProperty that indicates 
 * 		that the user selected a neighborhood shape/type (neighborhoodSelectionReceivedProperty)
 * 		and toroidality (toroidalSelectionReceivedProperty).
 * 
 * Assumes that an outside class is listening to when user selection is received, 
 * 		and coordinating transitions between screens appropriately 
 * 		so that user can view simulation with selected settings. 
 * 
 * @author Susie Choi 
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

public class OptionsScreen {
	public static final String DEFAULT_RESOURCE_PACKAGE = "properties/";
	public static final String DEFAULT_OPTIONS_FILENAME = "Options";
	public static final String[] DEFAULT_NEIGHBOR_OPTIONS = {"NeighborOption1","NeighborOption2","NeighborOption3"};
	public static final String[] DEFAULT_TOROIDAL_OPTIONS = {"ToroidalOn","ToroidalOff"};
	public static final String TOROIDALITY_ON_INDICATOR = "Yes";
	public static final int DEFAULT_SCREEN_SIZE = 400;
	public static final Paint BACKGROUND_COLOR = Color.WHITE;
	
	private ResourceBundle myResources;
	private Scene myScene;
	private GridPane myGridPane;
	private Pane myPane;
	private String myNeighborSelection;
	private String myToroidalSelection;
	private BooleanProperty neighborSelectionReceived = new SimpleBooleanProperty();
	private BooleanProperty toroidalSelectionReceived = new SimpleBooleanProperty();

	/**
	 * No-arg constructor calls multi-arg OptionsScreen constructor 
	 * 		using default filepath for options Properties. 
	 */
	public OptionsScreen() {
		this(DEFAULT_RESOURCE_PACKAGE + DEFAULT_OPTIONS_FILENAME);
	}
	
	/**
	 * Creates OptionsScreen containing buttons for each neighborhood type and one toroidality setting (on/off).
	 * @param String optionsFileName: the name of the Properties file with text to be contained in buttons:
	 * 		assumes that all button types are listed in public static final vars, 
	 * 		so that they may be matched with in-file Properties.
	 */
	public OptionsScreen(String optionsFileName) {
		myResources = ResourceBundle.getBundle(optionsFileName);

		myPane = new Pane();
		myGridPane = new GridPane();

		int rowIndex = 0;
		int colIndex = 0;

		for (String neighborOption : DEFAULT_NEIGHBOR_OPTIONS) {
			String optionLabel = myResources.getString(neighborOption);
			Button optionButton = new Button(optionLabel);
			optionButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent click) {
					handleUserNeighborSelection(optionButton);
				}
			});
			myGridPane.add(optionButton, colIndex, rowIndex);
			rowIndex++;
		}
		rowIndex = 0;
		colIndex++;
		for (String toroidalOption : DEFAULT_TOROIDAL_OPTIONS) {
			String optionLabel = myResources.getString(toroidalOption);
			Button optionButton = new Button(optionLabel);
			optionButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent click) {
					handleUserToroidalSelection(optionButton);
				}
			});
			myGridPane.add(optionButton, colIndex, rowIndex);
			rowIndex++;
		}
		myPane.getChildren().add(myGridPane);
		myNeighborSelection = "";
		myToroidalSelection = ""; 

		myScene = new Scene(myPane, DEFAULT_SCREEN_SIZE, DEFAULT_SCREEN_SIZE, BACKGROUND_COLOR);
	}

	private void handleUserNeighborSelection(Button selectedButton) {
		String neighborSelection = selectedButton.getText().substring(0, 1);
		myNeighborSelection = neighborSelection;
		neighborSelectionReceived();
	}

	private void handleUserToroidalSelection(Button selectedButton) {
		String toroidalSelection = selectedButton.getText().substring(0, TOROIDALITY_ON_INDICATOR.length());
		myToroidalSelection = toroidalSelection;
		toroidalSelectionReceived();
	}

	private boolean neighborSelectionReceived() {
		neighborSelectionReceived.set(myNeighborSelection.length() > 0);
		return (myNeighborSelection.length() > 0);
	}

	private boolean toroidalSelectionReceived() {
		toroidalSelectionReceived.set(myToroidalSelection.length() > 0);
		return (myToroidalSelection.length() > 0);
	}

	/**
	 * Returns user selection for neighborhood type/shapes for Cells.
	 * Useful for receiving user selection information after Listener indicates that selection was received. 
	 * @return String, name of selected neighborhood type/shape.
	 */
	public String getNeighborSelection() {
		return myNeighborSelection;
	}

	/**
	 * Returns user selection for toroidality.
	 * Useful for receiving user selection information after Listener indicates that selection was received. 
	 * @return boolean, toroidality setting of Grid.
	 */
	public boolean getToroidalSelection() {
		return (myToroidalSelection.equals(TOROIDALITY_ON_INDICATOR));
	}

	/**
	 * Returns scene including buttons for OptionsScreen obj.
	 * Useful for setting scene of Stage to display SplashScreen obj.
	 * @return Scene containing GridPane with buttons for OptionsScreen .
	 */
	public Scene getScene() {
		return myScene;
	}

	/**
	 * Returns BooleanProperty to indicate whether user selected neighborhood type/shape for Cells.
	 * Useful for calling class' listener to identify when user selection was received and, thus, 
	 * 		scene transition is necessary. 
	 * @return BooleanProperty, indicating whether user selected neighborhood type/shape for Cells.
	 */
	public BooleanProperty neighborSelectionReceivedProperty() {
		return neighborSelectionReceived;
	}

	/**
	 * Returns BooleanProperty to indicate whether user selected toroidality setting for Grid.
	 * Useful for calling class' listener to identify when user selection was received and, thus, 
	 * 		scene transition is necessary. 
	 * @return BooleanProperty, indicating whether user selected toroidality setting.
	 */
	public BooleanProperty toroidalSelectionReceivedProperty() {
		return toroidalSelectionReceived;
	}

}