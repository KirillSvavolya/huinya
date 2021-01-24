package model;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import model.ExceptionCollections.IllegalCoordinateException;
import view.Result;

public class Client {

	
	private OpponentField enemyField;
	private LocalField ownField;
	public Game battle;
	private Timer timer = new Timer();
	private int[] lastLeftRightH = {-1,-1,-1,-1};

	
	

public Client(LocalField field, OpponentField opponentField) {
	this.ownField = field; //create a new field
	this.enemyField = opponentField; // create empty enemy field
	this.battle = new Game(ownField.getBoard()); // create new Game and give own board to it
}

public ShotState makeTurn(int i, int j) throws IllegalCoordinateException {

	ShotState shotResult = battle.humanTurnResult(i,j); //make shot and get the result back
	this.lastLeftRightH = enemyField.markTheCell(shotResult, i, j);

	return shotResult;
}


public int[] recieveTurn() throws IllegalCoordinateException {
	int[] botTurn;		
	botTurn = battle.getComputerTurn();
	
	ownField.markTheCell(recieveBotShotState(), botTurn[0], botTurn[1]);
	return botTurn;
}
public ShotState recieveBotShotState() throws IllegalCoordinateException{
	return battle.getBotShotState();
}

public int[] getLastLeftRightH() {
	return this.lastLeftRightH;
}

public Result isGameEnd() {
	return battle.isGameEnd();
	
}



}








/*private void timerGame() { // timer needs to be moved out of here so it can be stopped
	TimerTask task = new TimerTask() {
        public void run() {
            try {
            	int[] emptyshot = new int[2];
            	emptyshot[0] = 20;
				makeTurn(emptyshot);
			} catch (IllegalCoordinateException e) {
				// TODO Auto-generated catch block
				System.out.println("pzdc");
			} 
        }
    };
    Timer timer = new Timer("Timer");
    
    long delay = 300000;
    timer.schedule(task, delay);
}


private void timerTurn() {
    TimerTask task = new TimerTask() {
        public void run() {
            try {
            	int[] emptyshot = new int[2];
            	emptyshot[0] = 20;
				makeTurn(emptyshot);
			} catch (IllegalCoordinateException e) {
				// TODO Auto-generated catch block
				System.out.println("pzdc");
			} 
        }
    };
    Timer timer = new Timer("Timer");
    
    long delay = 30000;
    timer.schedule(task, delay);
}*/






/*
public static void main(String [] args) throws IllegalCoordinateException {
	 Scanner scn = new Scanner(System.in);
			 
	Client player = new Client();
	int[] coor = new int[2];
	String go = "";
	
	
	while (player.go){
		System.out.println("Your field: ");
		for (int[] x : player.getOwnField())
		{
		   for (int y : x)
		   {
		        System.out.print(y + " ");
		   }
		   System.out.println();
		}
		
		System.out.println("Enemy Field: ");
		for (int[] x : player.getEnemyField())
		{
		   for (int y : x)
		   {
		        System.out.print(y + " ");
		   }
		   System.out.println();
		}
		System.out.println("Enemy Full Field: ");
		for (int[] x : player.getFullEnemyField())
		{
		   for (int y : x)
		   {
		        System.out.print(y + " ");
		   }
		   System.out.println();
		}
		
		System.out.println("Give coordinates: ");
	    coor[0] = scn.nextInt();
	    coor[1] = scn.nextInt();
	    player.makeTurn(coor);
	    
	    
		
	}
	
	System.out.println("time is up");
	
	
}*/


