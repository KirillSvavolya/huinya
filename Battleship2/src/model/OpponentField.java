package model;

public class OpponentField {
	private int[][] opponentField;
	private static final int verticalDimension = 10;
	private static final int horizontalDimension = 15;
	public static enum States {HIT, EMPTY};
	// Initialise an empty opponent field.
	public OpponentField() {
		this.opponentField = new int[verticalDimension][horizontalDimension];
	}
	
	// Mark the cell from coordinate and state.
	public void markCell() {
		
	}
	
	// Return the cell.
	public int[][] getBoard() {
		return this.opponentField;
	}
}
