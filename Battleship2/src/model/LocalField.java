package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import model.ExceptionCollections.IllegalCoordinateException;

public class LocalField implements Field {
	// variables to initialise a field, and test it.
	private int[][] board;
	private ArrayList<Integer> array; // is required to pick a random ship from the list
	// as it is necessary to remove an element from the array if it was picked.
	public static int[] ships = {
			1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 2, 2, 2, 2,
			2, 2, 2, 2, 3, 3, 3,
			3, 3, 4, 4, 4, 5, 5};

/**
 * Creates empty field for a single player.
 * @ensures getBoard() == new int[10][15].
 */
	public LocalField() {
		this.array = new ArrayList<Integer>();
		this.board = new int[10][15];
	}
	
	// for test purposes.
	public int getNumOfCellsRequired() {
		return numOfCellsRequired;
	}
	
	// getter for int[][] representation of the board.
/**
 * Returns two dimensional board.
 * @return board with the current representation of the game.
 */
	public int[][] getBoard() {
		return board;
	}
	
	// String representation - for test purposes.
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
 * Places ships randomly on the field. 
 * Only horizontal placement is supported.
 * @ensures getBoard() returns board with randomly placed ships. All the ships are placed.
 */
	public void placeShips() {
		// create a variable to store size of the ship to be placed.
		int size;
		// Create a random object to calculate probabilities.
		Random rand = new Random();
		// Copy the list of the ships to the class variable array (of type ArrayList).
		fillArrayList();
		// create a variable to indicate if the ship was placed.
		boolean placed;
		// declare a variable to store the size of the empty space found.
		int emptySpace;
		// declare a variable to store the coordinate for ship placement in the determined sector.
		// in the code is chosen randomly.
		int whereToPlace;
		// stores coordinates of the sector to where the ship can be placed.
		int lowerSectorBound;
		int upperSectorBound;
		// create a variable to store direction of the population
		int direction;
		// create a list to store possible values of direction (1, -1);
		int[] directionOptions = {1, -1};
		// create a field to store the index of the start position.
		int startPosition;
		// iterate for each ship to be placed.
		for (int i = 0; i < numberOfShips; i++) {
			// get random ship from the collection and store it's size.
			size = getRandomShip(this.array);
			// indicate that the ship is not placed yet.
			placed = false;
			// Choose a direction of the loop randomly (from bottom to top or vice-versa)
			direction = directionOptions[rand.nextInt(2)];
			// adjust start position of the loop according to the direction.
			if (direction == 1) {
				// if from top - set to 0.
				startPosition = 0;
			} else {
				// if from bottom to top - start with 9, decrementing.
				startPosition = 9;
			}
			while (!placed) {
				// set j to startPostion and adjust direction with the direction variable.
				// conditions checked: j is lower than 15, the ship is not placed yet and j is greater than -1.
				// the first condition is necessary for top-bottom iteration, the last - for bottom-top.
				for (int j = startPosition; j < board.length && !placed && j > -1; j = j + direction) {
					emptySpace = 0;
					for (int k = 0; k < board[0].length && !placed; k++) {
						// Check for the end of the row.
						if (k != board[0].length - 1) {
							// Check if the cell is empty.
							// if not - check whether the ship can fit in the empty space before. If true - place ship with 
							// a small chance. if the ship won't be placed, the chance to be increased.
							if (board[j][k] == EMPTY_CELL) {
								emptySpace++;
							} else {
								// Check if the ship fits.
								// Decide whether to place ship here.
								if (size + 2 <= emptySpace && rand.nextBoolean()) { // NOTE: CHANGE RAND.NEXTBOOLEAN TO ANY OTHER METHOD
									// TO CHANGE PROBABILITY.
									// ship placement block.
									// decide where to start placing the ship.
									// k - emptySpace + size indicates the first coordinate available for ship placement.
									// NOTE: algorithm is designed based on the backward placement.
									// k - 1 indicates the last empty coordinate of the sector.
									lowerSectorBound = k - emptySpace + size; 
									// Condition to check if the first empty cell in the sector is with index 0; if true
									// the ship can be placed in the cell, as there is nothing else from the left (start of the row).
									if (k - emptySpace == 0) {
										lowerSectorBound--;
									}
									upperSectorBound = k - 1;
									// choose random position to start placement between lowerSectorBound and upperSectorBound.
									whereToPlace = rand.nextInt(upperSectorBound - lowerSectorBound) + lowerSectorBound;
									// place the ship starting from coordinate specified in whereToPlace variable.
									// Note: the ship is placed backwards.
									for (int h = whereToPlace; size > 0; h--) {
										// place part of the ship.
										board[j][h] = TAKEN_CELL;
										// decrement number of cells to be placed.
										size--;
									}
									placed = true;
								}
								emptySpace = 0;
							}
							// Situation when the end of the row is reached (k = 14).
						} else {
							// Check if the last cell of the row is already occupied. If not
							// Increment emptySpace by 1.
							if (board[j][k] == EMPTY_CELL) {
								emptySpace++;
							}
							// Check if the ship fits.
							
							// FLAW - won't place a ship if k is free and emptySpace = size + 1; (e.g. the ship can only be 
							// placed starting from the last cell in the row.
							
							if (size + 2 <= emptySpace && rand.nextBoolean()) {
								if (board[j][k] == EMPTY_CELL) {
									// ship cannot be placed only on 16th column.
									// k + 1 is used because the random algorithm skips the k coordinate as it is considered taken in
									// the previous part of the algorithm. However, if k is the last cell in the row, and also
									// it is free, then the "next imaginary" cell which is taken has the coordinate k++;
									upperSectorBound = k + 1;
									// as empty space is incremented by 1 if k = 14 is empty, we also need to add 1 to the lowerSectorBound
									// (the formula subtracts emptySpace)
									lowerSectorBound = k - emptySpace + size + 1;
									// Check for the case with empty row:
									if (emptySpace == 15) {
										// move the lower bound backwards by 1.
										lowerSectorBound--;
									}
								} else {
									lowerSectorBound = k - emptySpace + size;
									// if k is taken - upperSector bound is calculated in the same way as in part 1 of the method.
									upperSectorBound = k - 1;
								}
								whereToPlace = rand.nextInt(upperSectorBound - lowerSectorBound) + lowerSectorBound;
								
								for (int h = whereToPlace; size > 0; h--) {
									// place part of the ship.
									board[j][h] = TAKEN_CELL;
									// decrement number of cells to be placed.
									size--;
								}
								placed = true;
							}
							emptySpace = 0;
						}
					}
				}
			}
		}	
	}
	
	// Returns random ship from the array and removes it. If the array is empty - returns 0.
	public int getRandomShip(ArrayList<Integer> list) {
		if (!list.isEmpty()) {
			int index = (int)(Math.random() * list.size());
			int res = list.get(index);
			list.remove(index);
			return res;
		}
		return 0;
	}
	
	// Copies values from the list of ships to the to the arrayList.
	private void fillArrayList() {
		for(int elem: ships) {
			array.add(elem);
		}
	}
	
	// Method to indicate a shot.
/**
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
}
