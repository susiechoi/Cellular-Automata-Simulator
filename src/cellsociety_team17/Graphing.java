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
	
	/**
	 * Creates an instance of the Graphing class. 
	 * The class contains a scene that contains a LineChart
	 * It also starts the count initializes the count variable that will be used for the x-axis of chart
	 */
	public Graphing() {
		yaxis.setAutoRanging(true);
		xaxis.setAutoRanging(true);
		myChart = new LineChart<Number, Number>(xaxis, yaxis);
		myChart.getData().add(typeOne);
		myChart.getData().add(typeTwo);
		myChart.getData().add(typeThree);
		myRoot= new Group();
		newScene();
		count=0;
	}
	
	private void newScene() {
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

	/**
	 * This method is called from Step in the Main method
	 * It counts the number of Cells of each state and it graphs them in up to 3 different lines
	 * @param allCells- a list of all the Cells in the grid from the main method
	 * @param simulationType- the simulation type so that the LineChart can have the proper legend with the correct labels
	 */
	public void graphCells(List<Cell> allCells, int simulationType) {
		int[] numCells = countCells(allCells);
		XYChart.Data<Number, Number> data1 = new XYChart.Data<Number, Number>(count, numCells[0]);
		typeOne.getData().add(data1);
		XYChart.Data<Number, Number> data2 = new XYChart.Data<Number, Number>(count, numCells[1]);
		typeTwo.getData().add(data2);
		XYChart.Data<Number, Number> data3 = new XYChart.Data<Number, Number>(count, numCells[2]);
		typeThree.getData().add(data3);
		
		if(simulationType==0) {
			typeOne.setName("EMPTY");
			typeTwo.setName("TREE");
			typeThree.setName("BURNING");
		}
		

		if(simulationType==1) {
			typeOne.setName("DEAD");
			typeTwo.setName("ALIVE");
			myChart.getData().remove(typeThree);
		}
		

		if(simulationType==2) {
			typeOne.setName("EMPTY");
			typeTwo.setName("FISH");
			typeThree.setName("SHARK");
		}
		

		if(simulationType==3) {
			typeOne.setName("EMPTY");
			typeTwo.setName("BLUE");
			typeThree.setName("RED");
		}
		
		
		count++;

	}
	
	public Scene getScene() {
		return graphScene;
	}

}
