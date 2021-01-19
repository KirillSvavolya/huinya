package model;

import java.util.Arrays;

import model.ExceptionCollections.IllegalCoordinateException;

public abstract class Field {
	// Shared attributes. The attributes are declared as public for test purposes.
	// Is a subject to change in the later stages of development.
	public static final int numOfCellsRequired = 63; // for test purposes.
	public static final int numberOfShips = 28;
	public static final int EMPTY_CELL = 0;
	public static final int TAKEN_CELL = 1;
	public static final int DEAD_CELL = 2;
	public static final int MISS_CELL = 3;
	private static final int V_DIM = 10;
	private static final int H_DIM = 15;
	public static int AAscii = 65;
	public static int OAscii = 79;
	protected int[][] board;
	private int score;
	
/**
 * Initialises the common part of all the fields.
 * @param board - an empty board for the field.
 */
	protected Field() {
		this(new int[V_DIM][H_DIM]);
	}

/**
 * Initialises the board and sets it to the given value.
 * @param board
 */
	protected Field(int[][] board) {
		this.board = board;
		this.score = 0;
	}

/**
 * @return integer representing the score of the player.
 */
	protected int getScore() {
		return this.score;
		
	}

/**
 * @ensures getScore() == old.getScore()++;
 */
	protected void incrementScore() {
		this.score++;
	}
	
/**
 * Marks the given cell with the given state.
 * @param state == {HIT, MISS, KILLED}.
 * @param i - vertical coordinate of the cell.
 * @param j - horizontal coordinate of the cell.
 */
	protected void markTheCell(ShotState state, int i, int j) {
		switch (state) {
		case HIT:{
			board[i][j] = DEAD_CELL;
			break;
		}
		case MISS:{
			board[i][j] = MISS_CELL;
			break;
		}
		case KILLED:{
			board[i][j] = DEAD_CELL;
			markRightLeftAsShot(i, j);
			break;
		}
		}
	}

	// As the ships cannot be in the adjacent cells, when the ship is destroyed,
	// one cell from right and one from left is marked as shot.
	private void markRightLeftAsShot(int i, int j) {
		// check cells from right.
		for (int k = j; k < 15; k++) {
			// miss cell is marked as 3, thus, the check can start from j coordinate (which was changed to hit before
			// the method was invoked. It allows to exclude checks required if taking j = j + 1 || j = j - 1.
			if (this.board[i][k] == 0) {
				this.board[i][k] = MISS_CELL;
				break;
			}
		}
		// check cells from left.
		for (int k = j; k >= 0; k--) {
			if (this.board[i][k] == 0) {
				this.board[i][k] = MISS_CELL;
				break;
			}
		}
	}

/**
 * Method to mark a shoot.
 * @requires coordinate == "[0-9]" + "[A-O]".
 * @param coordinate of the shot in string.
 * @param state result of the shot in Enum format (hit||killed||miss).
 * @throws IllegalCoordinateException if the coordinate provided does not respect the preconditions.
 */
	public void shoot(String coordinate, ShotState state) throws IllegalCoordinateException {
		int[] res = breakCoordinates(coordinate);
		int i = res[0];
		int j = res[1];
		switch (state) {
		case MISS: {
			this.board[i][j] = MISS_CELL;
			break;
		}
		case HIT: {
			this.board[i][j] = DEAD_CELL;
			break;
		}
		case KILLED: {
			this.board[i][j] = DEAD_CELL;
			// if the ship is dead, both right and left cells have to be marked as miss.
			markRightLeftAsShot(i, j);
			break;
		}
		}
	}
	
/**
 * Translates coordinate from string to indices.
 * @requires coordinate == "[0-9]" + "[A-O]".
 * @param coordinate to translate.
 * @return array with two integers representing indices of the given coordinate in the two dimensional array.
 * @throws IllegalCoordinateException if the coordinate given does not respect the preconditions.
 */
	public int[] breakCoordinates(String coordinate) throws IllegalCoordinateException {
			int[] res = new int[2];
			int vertical = Integer.parseInt("" + coordinate.charAt(0));
			char horizontal = coordinate.charAt(1);
			int ascii = (int) horizontal;
			if (ascii > OAscii && ascii < AAscii && vertical < 0 && vertical > 9) {
				throw new IllegalCoordinateException();
			}
			ascii = ascii - AAscii;
			res[0] = vertical;
			res[1] = ascii;
			return res;
		}
	
	// getter for int[][] representation of the board.
/**
 * Returns two dimensional board.
 * @ensures the game board is returned
 * @return board with the current representation of the game.
 */
	public int[][] getBoard(){
		return this.board;
	}
	
/**
 * @return printable version of the game board.
 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			str.append(Arrays.toString(board[i]));
			str.append("\n");
		}
		return str.toString();
	}
}
