package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Score {
	private int score;
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public Score() {
		this.score = 0;
	}

/**
 * @return integer representing the score of the player.
 */
	public int getScore() {
		return this.score;
	}
	
/**
 * Increments the score and fires an event to inform observers about the change.
 * @ensures getScore() == old.getScore()++;
 */
	public void incrementScore() {
		int oldValue = this.score;
		this.score++;
		int newValue = this.score;
		support.firePropertyChange("score", oldValue, newValue);
	}
	
/**
 * Adds listener to the property score.
 * @param listener
 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener("score", listener);
	}

/**
 * Removes listener
 * @param listener to remove.
 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
}
