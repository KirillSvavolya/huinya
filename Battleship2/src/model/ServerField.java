package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import model.ExceptionCollections.IllegalCoordinateException;

public class ServerField implements Field {
	private int[][] board;
	private int numberOfShipsLeft;
	private int score;
	private String playerID;
	
	// Constructor initialises new server field, requires two-dimensional board.
/**
 * @requires board.length == 10 && board[i].length == 15.
 * @ensures getBoard() == board.
 * @param board - game board of a single player filled with ships.
 * @param playerID - ID of the player the board belongs to.
 */
	public ServerField(int[][] board, String playerID) {
		this.board = board;
		this.score = 0;
		this.playerID = playerID;
	}

/**
 * @ensures the game board is returned.
 * @return the board with the current representation of the game.
 */
	public int[][] getBoard() {
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
/**
 * @requires coordinate != null.
 * @ensures coordinate given by the string is checked, and the result of the shot is returned.
 * @param coordinate
 */
	public ShotState getResultOfTheShot(String coordinate) throws IllegalCoordinateException {
		int[] index = breakCoordinates(coordinate);
		int i = index[0];
		int j = index[1];
		if (this.board[i][j] == 0) {
			markTheCell(ShotState.MISS, i, j);
			return ShotState.MISS;
		}
		return null;
	}

	private boolean isShipDead() {
		return false;
	}
	
	private void incrementScore() {
		this.score++;
	}
	
	private void markTheCell(ShotState state, int i, int j) {
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
 * @return integer representing the score of the player.
 */
	public int getScore() {
		return this.score;
	}
}
