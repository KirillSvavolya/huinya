package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import model.ExceptionCollections.IllegalCoordinateException;

public class ServerField extends Field {
	private int numberOfShipsLeft;
	private String playerID;
	
	// Constructor initialises new server field, requires two-dimensional board.
/**
 * @requires board.length == 10 && board[i].length == 15.
 * @ensures getBoard() == board.
 * @param board - game board of a single player filled with ships.
 * @param playerID - ID of the player the board belongs to.
 */
	public ServerField(int[][] board, String playerID) {
		super(board);
		// set the counter of the ships.
		this.numberOfShipsLeft = Field.numberOfShips;
		// assign the board to the player.
		this.playerID = playerID;
	}
	
/**
 * Checks the result of the shot, adjusts the board stored on the server accordingly and returns
 * the result for further processing.
 * @requires coordinate == "[A-O] + [1-9]".
 * @requires board[coordinate1][coordinate2] = 1 || 0. (The cell was not marked as dead or missed).
 * @ensures coordinate given by the string is checked, and the result of the shot is returned.
 * @param coordinate
 */
	public ShotState getResultOfTheShot(String coordinate) throws IllegalCoordinateException {
		int[] index = breakCoordinates(coordinate);
		int i = index[0];
		int j = index[1];
		// check the status of the cell.
		if (this.board[i][j] == EMPTY_CELL) {
			// if the cell is empty - then the shot missed. Thus, the cell is marked as miss and the
			// result is returned.
			// mark the cell as miss.
			markTheCell(ShotState.MISS, i, j);
			// return the miss result.
			return ShotState.MISS;
			// the case when the cell is taken > the shot is successful.
		} else if (this.board [i][j] == TAKEN_CELL) {
			// if the shot is successful, the score is incremented by 1.
			incrementScore();
			// Check if the shot destroyed the ship.
			if (isShipDead(i, j)) {
				// if true, the shot is a kill.
				markTheCell(ShotState.KILLED, i, j);
				// if the shot is a kill, one is added to the score.
				incrementScore();
				return ShotState.KILLED;
			} else {
				// if the ship is still alive - then the shot is a hit.
				markTheCell(ShotState.HIT, i, j);
				return ShotState.HIT;
			}
		} else {
			String text = "precondition violated: The cell must to be empty or taken by the ship";
			throw new IllegalCoordinateException(text);
		}
	}

	private boolean isShipDead(int i, int j) {
		int counter = 0;
		// loop through the row to the right of the ship. 
		for (int k = j; k < board[0].length; k++) {
			// if the cell is miss or empty before encountering 1, there is no
			// ship continuation to the right.
			if (board[i][k] == EMPTY_CELL && board[i][k] == MISS_CELL) {
				break;
			// The first iteration condition is always true (starting from the coordinate of the shot),
			// thus, the counter is used.
			} else if(board[i][k] == TAKEN_CELL) {
				counter++;
			}
			// if value of the counter is 2, then, the ship continues to the right, thus, false is returned.
			if (counter == 2) {
				return false;
			} 
		}
		// reset the counter.
		counter = 0;
		// now, loop to the left.
		for (int h = j; h >= 0; h--) {
			// if the cell is miss or empty before encountering 1, there is no
			// ship continuation to the left.
			if (board[i][h] == EMPTY_CELL && board[i][h] == MISS_CELL) {
				break;
			// The first iteration condition is always true (starting from the coordinate of the shot),
			// thus, the counter is used.
			} else if(board[i][h] == TAKEN_CELL) {
				counter++;
			}
			// if value of the counter is 2, then, the ship continues to the left, thus, false is returned.
			if (counter == 2) {
				return false;
			}
		}
		// if no continuation to the right and left, the ship is dead.
		return true;
	}
	
/**
 * Checks whether the game is over. The game can be over either when: all of the 
 * ships which belong to one board is 0, or the timer has ended.
 * @return
 */
	private boolean isGameOver() {
		if (this.numberOfShipsLeft == 0) {
			return true;
		}
		// NOTE: ADD TIMER CONDITION HERE.
		return false;
	}
}
