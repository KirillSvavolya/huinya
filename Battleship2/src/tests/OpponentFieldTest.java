package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import model.OpponentField;

public class OpponentFieldTest {
	private OpponentField field;
	private int[][] board;
	
	@BeforeEach
	public void setup() {
		field = new OpponentField();
		board = field.getBoard();
	}
	
	@Test
	public void testShot() {
		
	}
}
