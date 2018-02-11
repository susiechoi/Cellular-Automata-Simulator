package cellsociety_team17;

import java.util.ResourceBundle;
import javax.imageio.ImageIO;
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

	public OptionsScreen() {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_OPTIONS_FILENAME);

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
			final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)",String.join("|", ImageIO.getReaderFileSuffixes()));
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

	public boolean neighborSelectionReceived() {
		neighborSelectionReceived.set(myNeighborSelection.length() > 0);
		return (myNeighborSelection.length() > 0);
	}

	private boolean toroidalSelectionReceived() {
		toroidalSelectionReceived.set(myToroidalSelection.length() > 0);
		return (myToroidalSelection.length() > 0);
	}

	public String getNeighborSelection() {
		return myNeighborSelection;
	}

	public boolean getToroidalSelection() {
		return (myToroidalSelection.equals(TOROIDALITY_ON_INDICATOR));
	}

	public Scene getScene() {
		return myScene;
	}

	public BooleanProperty neighborSelectionReceivedProperty() {
		return neighborSelectionReceived;
	}

	public BooleanProperty toroidalSelectionReceivedProperty() {
		return toroidalSelectionReceived;
	}

}