package lab6;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DrawPyramid extends Application {
	
	int numRows = 2;
	double weight = 128;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// VBox to hold the rows of circles
		VBox stack = new VBox();
		stack.setPadding(new Insets(5));
		populateStack(stack);
		
		// ScrollPane to hold the VBox
//		ScrollPane scroll = new ScrollPane(stack);
		
		// Slider to pick the number of rows
		Label rowsLbl = new Label("# rows:");
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
				populateStack(stack);
			}
		});
		
		// TextField and Button to enter weight
		TextField weightField = new TextField("128");
		weightField.setPrefColumnCount(3);
		Label weightLbl = new Label("Weight at the top:");
		// Change listener to update the weight
		weightField.textProperty().addListener((obs, old_val, new_val) -> {
			if (!new_val.isEmpty()) {	// text field is not empty
				try {
					weight = Double.parseDouble(new_val);
					populateStack(stack);
				} catch (NumberFormatException e) {
					weightField.setText(old_val);
					populateStack(stack);
				}
			}
		});
		
		// HBox to hold weight entry
		HBox top = new HBox(10);
		top.setAlignment(Pos.CENTER);
		top.setPadding(new Insets(10));
		top.getChildren().addAll(weightLbl, weightField);
		
		// HBox to hold slider and label
		HBox bottom = new HBox(10);
		bottom.setAlignment(Pos.CENTER);
		bottom.getChildren().addAll(rowsLbl, slider);
		HBox.setHgrow(slider, Priority.ALWAYS);
		bottom.setPadding(new Insets(10));
				
		BorderPane pane = new BorderPane();
		pane.setTop(top);
		pane.setBottom(bottom);
//		pane.setCenter(scroll);
		pane.setCenter(stack);
		
		Scene scene = new Scene(pane, 1200, 1000);
		primaryStage.setTitle("Node Stacking Demo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	void populateStack(VBox stack) {
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
				double nodeWeight = NodeStacking.weightSupporting(weight, i, j);
				Color color;
				// Set the color of the circle based on the weight supported
				// Red is rgb(1,0,0), yellow is rgb(1,1,0) and green is rgb(0,1,0)
				if (nodeWeight >= weight * 0.2) {	
					// Range from (1,0,0) to (1,1,0)
					// If nodeWeight = 28, green should equal 1.0
					// If nodeWeight = 128, green should equal 0.0
					double green = (weight - nodeWeight) / (weight * 0.8);
					color = new Color(1.0, green, 0, 1.0);
				} else {
					// Range from (1,1,0) to (0,1,0)
					// If nodeWeight = 28, red should equal 1.0
					// If nodeWeight = 0, red should equal 0.0
					double red = nodeWeight / (weight * 0.2);
					color = new Color(red, 1.0, 0, 1.0);
				}
				circle.setFill(color);
				String weightString = String.valueOf(nodeWeight);
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
