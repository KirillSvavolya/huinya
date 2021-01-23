package model;

import java.util.Timer;
import java.util.TimerTask;

import model.ExceptionCollections.IllegalCoordinateException;

public class Game {

	private ServerField humanField;
	private ServerField botField;
	private AI bot;
	private ShotState botShotResult;
	
	
	public Game(int[][] board) {
		this.humanField = new ServerField(board, "1");
		this.bot = new AI();
		this.botField = new ServerField(bot.getField(), "2");
		
	}

// sends back the result of humna's turn
	public ShotState gumanTurnResult(int[] turn) throws IllegalCoordinateException {
			ShotState shot = botField.getResultOfTheShot(turn[0],turn[1]);
			return shot;
	}
	// we call comptuerTurn to get a new turn generated by the bot
	//and we store his new turn for the next turn
	public int[] getComputerTurn() throws IllegalCoordinateException {
		int[] botturn;
		botturn = bot.computerTurn(botShotResult); //based on the previous move bot makes his turn
		botShotResult= humanField.getResultOfTheShot(botturn[0],botturn[1]); //we keep the result of his turn to further give it back to bot
		return botturn;	
	}
	
	public ShotState getBotShotState() throws IllegalCoordinateException {
		return this.botShotResult;
	}
	
	//returns BotField to Client
	public int[][] getBotfield(){
		return this.botField.getBoard();
	}
	

	//public void analyseString(){} needs to be implemented for server
}
