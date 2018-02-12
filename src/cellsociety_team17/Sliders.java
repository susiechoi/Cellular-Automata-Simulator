package cellsociety_team17;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;

public class Sliders {
	private Group myRoot;
	private Scene mySliderScene;
	private Slider mySlider;

	public Sliders() {
		myRoot= new Group();
		mySlider= new Slider(0, 1.0, .5);
		newScene();
	}

	public void newScene() {
		mySliderScene = new Scene(myRoot);
		myRoot.getChildren().add(mySlider);
	}
	
	public Scene getScene() {
		return mySliderScene;
	}
	
	public Slider getSlider() {
		return mySlider;
	}
}
