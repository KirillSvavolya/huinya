package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class CustomGridPane extends GridPane {
	private int column;
	private int row;
	
	public CustomGridPane(int column, int row) {
		this.row = row;
		this.column = column;
		
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getRow() {
		return this.row;
	}
}
