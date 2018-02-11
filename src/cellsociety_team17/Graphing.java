package cellsociety_team17;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import java.util.List;


public class Graphing {
	private Group myRoot;
	private Scene graphScene;
	private NumberAxis yaxis= new NumberAxis(0, 25, 1);
	private NumberAxis xaxis= new NumberAxis(0, 25, 1);
	private LineChart<Number, Number> myChart;
	private Series<Number, Number> typeOne = new Series<Number, Number>();
	private Series<Number, Number> typeTwo = new Series<Number, Number>();
	private Series<Number, Number> typeThree = new Series<Number, Number>();
	private int count;
	
	public Graphing() {
		myChart = new LineChart<Number, Number>(xaxis, yaxis);
		myChart.getData().add(typeOne);
		myChart.getData().add(typeTwo);
		myChart.getData().add(typeThree);
		myRoot= new Group();
		newScene();
		count=0;
	}
	
	public void newScene() {
		graphScene = new Scene(myRoot);
		myRoot.getChildren().add(myChart);
	}

	private int[] countCells(List<Cell> allCells) {
		int[] numCells = new int[3];
		for (Cell cell : allCells) {
			numCells[cell.getMyState()]++;
		}
		return numCells;
	}

	public void graphCells(List<Cell> allCells) {
		int[] numCells = countCells(allCells);
		XYChart.Data<Number, Number> data1 = new XYChart.Data<Number, Number>(count, numCells[0]);
		typeOne.getData().add(data1);
		XYChart.Data<Number, Number> data2 = new XYChart.Data<Number, Number>(count, numCells[1]);
		typeTwo.getData().add(data2);
		XYChart.Data<Number, Number> data3 = new XYChart.Data<Number, Number>(count, numCells[2]);
		typeThree.getData().add(data3);
		
		
		count++;

	}
	
	public Scene getScene() {
		return graphScene;
	}

}
