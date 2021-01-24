package view;

import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class Grid {
	private Pane[][] rects;
	
	public Grid() {
		this.rects = new Pane[10][15];
	}
	// method to get the cell from it's coordinate.
	public Pane getCustomRect(int row, int column) {
		return rects[row][column];
	}
	
	// method to add a cell to the grid
	public void add(Pane rect, int row, int column) {
		rects[row][column] = rect;
	}
	
	public void setBack(int row, int column, Background back) {
		rects[row][column].setBackground(back);
	}
	
	public void setRectStyle(int row, int column, String style) {
		rects[row][column].setStyle(style);
	}
}
