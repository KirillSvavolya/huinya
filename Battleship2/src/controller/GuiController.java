package controller;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.Client;
import model.Field;
import model.LocalField;
import model.OpponentField;
import model.ShotState;
import model.ExceptionCollections.IllegalCoordinateException;
import view.BattleshipGui;
import view.Result;
import view.Turn;

public class GuiController implements BattleshipGuiDelegate {
	protected BattleshipGui view;
	// Create a single controller. Is created only once as the constructor is private.
	// Implementation of Singleton pattern.
	public static BattleshipGuiDelegate sharedInstance = new GuiController();
	private LocalField playerField;
	private OpponentField opponentField;
	private Turn turn;
	private boolean botIsShooting = false;
	private String playerName;
	private String opponentName;
	private Client player;
	private boolean isWithServer;
	
	private GuiController() {	
	}
	
	@Override
	public BattleshipGui getView() {
		return this.view;
	}
	
	private void initialiseGame() {
		this.opponentField = new OpponentField();
		this.turn = new Turn();
		setTurnListener();
		setPlayerScoreListener();
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
		// initialise game field.
		initialiseGame();
		// set turn label as visible to represent turn changes.
		this.view.getTurnLabel().setVisible(true);
	}

	@Override
	public void placeShipsClicked() {
		playerField = new LocalField();
		setOpponentScoreListener();
		// Place ships.
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
		// initialise required variables.
		this.isWithServer = false;
		initialiseGame();
		// Initialise a client.
		this.player = new Client(playerField, opponentField);
		// Set the names:
		setOpponentName("Computer");
		setPlayerName("Player");
		// Initialise the third scene.
		this.view.setThirdScene();
		copyInitialField();
		this.view.primaryStage.setScene(view.getThirdScene());
		// Disable the turn label (computer makes turns too fast, thus, no reason to output).
		this.view.getTurnLabel().setVisible(false);
		
	}

	@Override
	public void fireMissile(int i, int j) {
        // ADD CONDITION IF THE TURN IS MADE (IT IS PLAYER TURN AND THE GAME IS NOT OVER YET).
        if (botIsShooting == false) {
            ShotState shot = ShotState.KILLED;
            try {
                shot = player.makeTurn(i,j);
            } catch (IllegalCoordinateException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            paintOpponentCell(i, j, shot);
            this.view.getOpponentGrid().getCustomRect(i, j).setDisable(true);

            if (shot == ShotState.KILLED) { //if we killed it then we need to mark the cells on the left and right of the ship
                if (player.getLastLeftRightH()[0] != -1) {
                    paintOpponentCell(player.getLastLeftRightH()[0], player.getLastLeftRightH()[1], ShotState.MISS);
                    this.view.getOpponentGrid().getCustomRect(player.getLastLeftRightH()[0], player.getLastLeftRightH()[1]).setDisable(true);
                }
                if (player.getLastLeftRightH()[2] != -1) {
                    paintOpponentCell(player.getLastLeftRightH()[2], player.getLastLeftRightH()[3], ShotState.MISS);
                    this.view.getOpponentGrid().getCustomRect(player.getLastLeftRightH()[2], player.getLastLeftRightH()[3]).setDisable(true);
                }
            }

            // EVERYTHING AFTER SUCCESSFUL TURN GOES HERE.
            // DISABLES THE CELL FOR THE SECOND CLICK.


            //WAITING COLOR CAN BE ADDED HERE.
            if(shot == ShotState.MISS) {
                try {
                    receiveAndPaintBotTurn();
                } catch (IllegalCoordinateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
      
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
		// DISABLES THE CELL FOR THE SECOND CLICK.
		this.view.getOpponentGrid().getCustomRect(i, j).setDisable(true);
	}
	
	@Override
	public void paintPlayerCell(int i, int j, ShotState state) {
		Pane rect = this.view.getPlayerGrid().getCustomRect(i, j);
		paintCell(i, j, state, rect);
	}
	
	private void receiveAndPaintBotTurn() throws IllegalCoordinateException {
        int[] botTurn = new int[2];
        ShotState botTurnState = ShotState.HIT;
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
	
	private void paintCell(int i, int j, ShotState state, Pane rect) {
		if (state.equals(ShotState.HIT) || state.equals(ShotState.KILLED)) {
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

	@Override
	public void nameEntered(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void portEntered(int port) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ipEntered(String ip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayerScoreListener() {
		// PLAYER SCORE IS STORED IN THE OPPONENT FIELD.
		this.opponentField.getScore().addPropertyChangeListener(e -> updatePlayerScoreOnTheScreen ((int) e.getNewValue()));
	}
	
	@Override
	public void setOpponentScoreListener() {
		// OPPONENT SCORE IS LOCALLY STORED IN THE PLAYER FIELD.
		this.playerField.getScore().addPropertyChangeListener(e -> updateOpponentScoreOnTheScreen((int) e.getNewValue()));
	}

	@Override
	public void updatePlayerScoreOnTheScreen(int score) {
		this.view.getPlayerScoreLabel().setText(String.format("%s: %d", this.getPlayerName(), score));
	}
	
	@Override
	public void updateOpponentScoreOnTheScreen(int score) {
		this.view.getOpponentScoreLabel().setText(String.format("%s: %d", this.getOpponentName(), score));
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public void setPlayerName(String name) {
		this.playerName = name;
	}

	@Override
	public void setOpponentName(String name) {
		this.opponentName = name;
		
	}

	@Override
	public String getOpponentName() {
		return this.opponentName;
	}

	@Override
	public void setTurnListener() {
		this.turn.addPropertyChangeListener(e -> updateTurnOnTheScreen((boolean) e.getNewValue()));
		
	}

	@Override
	public void updateTurnOnTheScreen(boolean isPlayerTurn) {
		Label turnLabel = this.view.getTurnLabel();
		if (isPlayerTurn) {
			turnLabel.setText(this.turn.getPlayerTurnText(this.playerName));
		} else {
			turnLabel.setText(this.turn.getOpponentTurnText(this.opponentName));
		}
	}

	@Override
	public void endGameScreen(Result winState) {
		showWinner(winState);
		this.view.primaryStage.setScene(this.view.getEndGameScene());
	}

	@Override
	public void exitClicked() {
		System.exit(0);
	}

	@Override
	public void showWinner(Result state) {
		Label label = this.view.getWinLabel();
		if (state == Result.LOSE) {
			label.setText("You Lose");
		} else if (state == Result.WIN_SCORE) {
			label.setText("You won by Score");
		} else {
			label.setText("You won by Walkover");
		}
	}

}
