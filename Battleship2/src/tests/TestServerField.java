package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Field;
import model.ServerField;
import model.ShotState;
import model.ExceptionCollections.IllegalCoordinateException;

class TestServerField {
	private ServerField field;
	private String testID;
	private int[][] board = new int[][] {
		{1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0},
		{1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
		{1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
		{1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1},
		{1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1},
		{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}	
	};
	
	@BeforeEach
	void setUp() {
		field = new ServerField(board, testID);
	}
 
	@Test
	void testIsShipDeadMovingFromLeftToRight() {
		field.markTheCell(ShotState.HIT, 0, 0);
		assertFalse(field.isShipDead(0, 1));
		field.markTheCell(ShotState.HIT, 0, 1);
		assertFalse(field.isShipDead(0, 2));
		field.markTheCell(ShotState.HIT, 0, 2);
		assertFalse(field.isShipDead(0, 3));
		field.markTheCell(ShotState.HIT, 0, 3);
		assertTrue(field.isShipDead(0, 4));
	}

	@Test
	void testIsShipDeadMovingFromRightToLeft() {
		for (int i = 4; i > 1; i--) {
			field.markTheCell(ShotState.HIT, 0, i);
			assertFalse(field.isShipDead(0, i - 1));
		}
		field.markTheCell(ShotState.HIT, 0, 1);
		assertTrue(field.isShipDead(0, 0));
		
		
	}
	
	@Test
	void testIsShipDeadCenterShot() {
		assertFalse(field.isShipDead(0, 2));
		field.markTheCell(ShotState.HIT, 0, 0);
		assertFalse(field.isShipDead(0, 2));
		field.markTheCell(ShotState.HIT, 0, 4);
		assertFalse(field.isShipDead(0, 2));
		field.markTheCell(ShotState.HIT, 0, 1);
		assertFalse(field.isShipDead(0, 2));
		field.markTheCell(ShotState.HIT, 0, 3);
		assertTrue(field.isShipDead(0, 2));
	}
	
	@Test
	void testGettingResultOfTheShotAndAppropriateChangesToTheBoard() throws IllegalCoordinateException {
		// test for hit
		ShotState state = field.getResultOfTheShot(0, 0);
		// test for score increment.
		assertEquals(1, field.getScore());
		assertEquals(Field.DEAD_CELL, field.getBoard()[0][0]);
		assertEquals(ShotState.HIT, state);
		// Test for miss
		state = field.getResultOfTheShot(0, 5);
		assertEquals(ShotState.MISS, state);
		// test for kill
		state = field.getResultOfTheShot(0, 1);
		state = field.getResultOfTheShot(0, 2);
		state = field.getResultOfTheShot(0, 3);
		state = field.getResultOfTheShot(0, 4);
		assertEquals(ShotState.KILLED, state);
		// test the score increment.
		assertEquals(6, field.getScore());
		// test that no cell is marked as miss if the ship was killed and both first right and left
		// cells that do not contain the ship are already marked as miss.
		int num = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == Field.MISS_CELL) {
					num++;
				}
			}
		}
		assertEquals(1, num);

		// test if the the cells are marked after the kill.
		state = field.getResultOfTheShot(5, 2);
		assertEquals(ShotState.KILLED, state);
		assertEquals(Field.MISS_CELL, field.getBoard()[5][1]);
		assertEquals(Field.MISS_CELL, field.getBoard()[5][3]);
	}
}
