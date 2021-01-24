package view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Turn {
	private boolean isPlayerTurn;
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public Turn() {
		this.isPlayerTurn = false;
	}
	
	public void setPlayerTurn(boolean state) {
		boolean oldValue = this.isPlayerTurn;
		this.isPlayerTurn = state;
		boolean newValue = this.isPlayerTurn;
		support.firePropertyChange("Turn", oldValue, newValue);
	}
	
	public boolean playerTurn() {
		return this.isPlayerTurn;
	}
	
	public String getPlayerTurnText(String playerName) {
		return String.format("%s's turn", playerName);
	}
	
	public String getOpponentTurnText(String opponentName) {
		return String.format("%s's turn", opponentName);
	}
	
/**
 * Adds listener to the turn boolean.
 * @param listener
 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener("Turn", listener);
	}

/**
 * Removes listener from the property.
 * @param listener to remove.
 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
}
