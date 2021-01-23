package controller;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.Client;
import model.Field;
import model.LocalField;
import model.ShotState;
import model.ExceptionCollections.IllegalCoordinateException;
import view.BattleshipGui;

public class GuiController implements BattleshipGuiDelegate {
	protected BattleshipGui view;
	// Create a single controller. Is created only once as the constructor is private.
	// Implementation of Singleton pattern.
	public static BattleshipGuiDelegate sharedInstance = new GuiController();
	private LocalField playerField;
	private Client player;
	private boolean isWithServer = false;
	private boolean botIsShooting = false;
	
 
	
	private GuiController() {	
		this.player = new Client();
	}
	
	@Override
	public BattleshipGui getView() {
		return this.view;
	}

	@Override
	public void setView(BattleshipGui gui) {
		this.view = gui;
		
	}

	@Override
	public void playClicked() {
		// Initialise new second scene (to remove all ships places, etc.);
		this.view.setSecondScene();
		this.view.primaryStage.setScene(view.getSecondScene());
	}

	@Override
	public void playOnlineClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placeShipsClicked() {
		// Create new Local Field and place ships.
		this.playerField = new LocalField();
		playerField.placeShips();
		// Get array of pointers to the rectangles.
		copyInitialField();
		// Enable play buttons.
		this.view.getPlayOfflineButton().setDisable(false);
		this.view.getPlayOnlineButton().setDisable(false);
	}
	
	// Fills the initial GUI board by iterating through the field with placed ships.
	private void copyInitialField() {
		// use the array of pointers to modify the view accordingly to the board.
		int[][] board = playerField.getBoard();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 15; j++) {
				if(board[i][j] == Field.TAKEN_CELL) {
					// Set border style.
					this.view.getPlayerGrid().setRectStyle(i, j, BattleshipGui.TAKEN_CELL_BORDER);
					this.view.getPlayerGrid().setBack(i, j, BattleshipGui.TAKEN_CELL_BACKGROUND);
				} else {
					this.view.getPlayerGrid().setRectStyle(i, j, BattleshipGui.EMPTY_CELL_BORDER);
					this.view.getPlayerGrid().setBack(i, j, BattleshipGui.EMPTY_CELL_BACKGROUND);
				}
			}
		}
	}

	@Override
	public void playOfflineClicked() {
		// Initialise the third scene.
		this.view.setThirdScene();
		copyInitialField();
		this.view.primaryStage.setScene(view.getThirdScene());
		
	}

	@Override
	public void fireMissile(int i, int j) {
		System.out.println(i + " " + j);
		// ADD CONDITION IF THE TURN IS MADE (IT IS PLAYER TURN AND THE GAME IS NOT OVER YET).
		if (botIsShooting == false /*&& timer.working*/) { 
			player.makeTurn(i,j);
			
			ShotState shot = player.getShotFeedback();
			paintOpponentCell(i, j, shot);
			
			if (shot == ShotState.KILLED) { //if we killed it then we need to mark the cells on the left and right of the ship
				if (player.getLastLeftRightH()[0] != -1) {
					paintOpponentCell(player.getLastLeftRightH()[0], player.getLastLeftRightH()[1], ShotState.MISS);
				}
				if (player.getLastLeftRightH()[2] != -1) {
					paintOpponentCell(player.getLastLeftRightH()[2], player.getLastLeftRightH()[3], ShotState.MISS);
				}
			}
			
			// EVERYTHING AFTER SUCCESSFUL TURN GOES HERE.
			// DISABLES THE CELL FOR THE SECOND CLICK.
			
			this.view.getOpponentGrid().getCustomRect(i, j).setDisable(true);
			//WAITING COLOR CAN BE ADDED HERE.
			if(shot == ShotState.MISS) {
				receiveAndPaintBotTurn();
			}
		}

		
	}

	
	private void receiveAndPaintBotTurn()throws IllegalCoordinateException {
		int[] botTurn = new int[2];
		ShotState botTurnState;
		if (isWithServer == false) { //gets bot's turn if play against bot
			botIsShooting = true;
			while (botTurnState != ShotState.MISS) {
			botTurn = player.recieveTurn();
			botTurnState = player.recieveBotShotState();
			paintPlayerCell(botTurn[0], botTurn[1], botTurnState);
			}
			botIsShooting = false;
		}	
	}

	@Override
	public void returnClicked() {
		this.view.primaryStage.setScene(view.getFirstScene());
	}

	@Override
	public void paintOpponentCell(int i, int j, ShotState state) {
		// Get the cell to paint.
		Pane rect = this.view.getOpponentGrid().getCustomRect(i, j);
		// Paint the cell.
		paintCell(i, j, state, rect);
	}
	
	@Override
	public void paintPlayerCell(int i, int j, ShotState state) {
		Pane rect = this.view.getPlayerGrid().getCustomRect(i, j);
		paintCell(i, j, state, rect);
	}
	
	private void paintCell(int i, int j, ShotState state, Pane rect) {
		if (state.equals(ShotState.HIT)) {
			// Set background to shot.
			rect.setBackground(BattleshipGui.SHOT_CELL_BACKGROUND);
			// If the ship is shot, add the borders to distinguish from the ships above and bellow.
			rect.setStyle(BattleshipGui.TAKEN_CELL_BORDER);
			// RIGHT-LEFT CHANGES HERE.
		} else {
			Circle cr = new Circle(rect.getWidth() / 2, rect.getHeight() / 2, 3, Paint.valueOf(BattleshipGui.DOT_HEX_COLOR));
			rect.getChildren().add(cr);
		}
	}
	private void setIsWithServer() {
		this.isWithServer = true;
		
	}

}
