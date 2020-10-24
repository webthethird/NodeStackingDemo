package lab6;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DrawPyramid extends Application {
	
	int numRows = 2;
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// VBox to hold the rows of circles
		VBox stack = new VBox();
		stack.setPadding(new Insets(5));
		populateStack(stack, numRows);
		
		// ScrollPane to hold the VBox
//		ScrollPane scroll = new ScrollPane(stack);
		
		// Slider to pick the number of rows
		Slider slider = new Slider();
		slider.setMin(1);
		slider.setMax(20);
		slider.setValue(2);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setBlockIncrement(1);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
		// Change listener to update the number of rows
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				numRows = new_val.intValue();
				populateStack(stack, numRows);
			}
		});
				
		BorderPane pane = new BorderPane();
		pane.setBottom(slider);
//		pane.setCenter(scroll);
		pane.setCenter(stack);
		
		Scene scene = new Scene(pane, 1200, 1000);
		primaryStage.setTitle("Node Stacking Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	void populateStack(VBox stack, int numRows) {
		// Clear the stack before populating it again
		stack.getChildren().clear();
		
		for (int i = 0; i < numRows; i++) {
			// Create an HBox for each row
			HBox row = new HBox();
			row.setAlignment(Pos.CENTER);
			row.setSpacing(5);
			for (int j = 0; j <= i; j++) {
				// For each row with index i, add i+1 circles with weight labels
				// StackPane to hold each circle and its associated label
				StackPane node = new StackPane();
				Circle circle = new Circle(22.5);
				circle.setStroke(Color.BLACK);
				// Use NoseStacking to calculate weight supported by each node
				double weight = NodeStacking.weightSupporting(i, j);
				Color color;
				// Set the color of the circle based on the weight supported
				// Red is rgb(1,0,0), yellow is rgb(1,1,0) and green is rgb(0,1,0)
				if (weight >= 28) {	
					// Range from (1,0,0) to (1,1,0)
					// If weight = 28, green should equal 1.0
					// If weight = 128, green should equal 0.0
					double green = (128 - weight) / 100;
					color = new Color(1.0, green, 0, 1.0);
				} else {
					// Range from (1,1,0) to (0,1,0)
					// If weight = 28, red should equal 1.0
					// If weight = 0, red should equal 0.0
					double red = weight / 28;
					color = new Color(red, 1.0, 0, 1.0);
				}
				circle.setFill(color);
				String weightString = String.valueOf(weight);
				if (weightString.length() > 5) {
					weightString = weightString.substring(0, 5);
				}
				Label label = new Label(weightString);
				node.getChildren().addAll(circle, label);
				row.getChildren().add(node);
			}
			stack.getChildren().add(row);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
