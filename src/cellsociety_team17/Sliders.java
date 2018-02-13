package cellsociety_team17;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;

public class Sliders {
	private Group myRoot;
	private Scene mySliderScene;
	private Slider mySlider;

	/**
	 * Creates a new instance of the Sliders class
	 * Contains a root, scene, and Slider
	 */
	public Sliders() {
		myRoot= new Group();
		mySlider= new Slider(0, 1.0, .5);
		newScene();
	}

	/**
	 * Creates a new scene with the myRoot variable in the instance of the class 
	 */
	public void newScene() {
		mySliderScene = new Scene(myRoot);
		myRoot.getChildren().add(mySlider);
	}
	
	/**
	 * This is a getter for the mySliderScene variable
	 * @return the Scene in the instance of the class
	 */
	public Scene getScene() {
		return mySliderScene;
	}
	
	/**
	 * This is a getter for the mySlider variable
	 * @return the Slider in the instance of the class
	 */
	public Slider getSlider() {
		return mySlider;
	}
}
