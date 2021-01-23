package controller;

import model.ShotState;
import view.BattleshipGui;

public interface BattleshipGuiDelegate {
	// variable to store a shared instance of the view.
	public static BattleshipGui sharedInstance = null;
/**
 * Returns shared instance of the view.
 * @return instance of the battleship GUI view.
 */
	public BattleshipGui getView();
	
/**
 * Sets the shared instance view to the GUI provided.
 * @requires GUI != null.
 * @param gui the view to store in the shared instance.
 */
	public void setView(BattleshipGui gui);

/**
 * Switches window to the game initialisation (ship placement).
 * @requires the window with the view is opened.
 * @ensures the window is switched to ship placement.
 */
	public void playClicked();

/**
 * Opens new window where the online game can be played.
 * Initialises the connection to the server, finds opponent and starts a game.
 * @requires placeShips() invoked before.
 * @ensures the game starts in online mode.
 */
	public void playOnlineClicked();
	
/**
 * Forces ship placement and updates the view showing all the ships placed.
 * @requires window is opened.
 * @ensures the ships are placed and drawn randomly.
 */
	public void placeShipsClicked();
	
/**
 * Changes the window to the game window, where the game can be played with a computer.
 * @requires placeShipClicked() called before.
 * @ensures the game starts in offline mode and the window is drawn.
 */
	public void playOfflineClicked();
	
/**
 * Command to fire a missile passed to controller from the view.
 * Disables the cell if the turn was made (e.g. the time is for player turn).
 * @requires i >= 0 && i <= 9.
 * @ensures the command is processed and the change to the view is made.
 * @param i vertical coordinate.
 * @param j horizontal coordinate.
 */
	public void fireMissile(int i, int j);

/**
 * Command to go back to the main screen from game preparation.
 * @requires window is open.
 * @ensures the scene is switched to the main.
 */
	public void returnClicked();

/**
 * Command to change background of the fired cell based on the result of the shot.
 * @requires i >= 0 && i < 10 && j >= 0 && j < 15.
 * @param i row index.
 * @param j column index.
 * @param state Enum state of the shot.
 */
	public void paintOpponentCell(int i, int j, ShotState state);

/**
 * Command to paint player cell after it was shot.
 * @param i y coordinate of the cell.
 * @param j x coordinate of the cell.
 * @param state the state representing result of the shot.
 */
	public void paintPlayerCell(int i, int j, ShotState state);
}
