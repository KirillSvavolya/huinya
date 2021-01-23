package model;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import model.ExceptionCollections.IllegalCoordinateException;

public class Client {

	
	private OpponentField enemyField;
	private LocalField ownField;
	private Game battle;
	private Timer timer = new Timer();
	public boolean go = true;
	
// intialiazes its own field, creates empty enemy field
public Client() {
	this.ownField = new LocalField();
	this.ownField.placeShips();
	this.enemyField= new OpponentField();
	this.battle = new Game(ownField.getBoard());
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

public void makeTurn(int[] coor) throws IllegalCoordinateException {
	
	ShotState shot = battle.countTurn(coor); //make shot and get the result back
	
	if (coor[0] != 20) {
	enemyField.markTheCell(shot, coor[0], coor[1]);
	}
	
	receiveTurn();
}

private void receiveTurn() throws IllegalCoordinateException {
	int[] botturn;	
	
	botturn = battle.getComputerTurn();
	ShotState botshot = battle.getShotState();
	ownField.markTheCell(botshot, botturn[0], botturn[1]);
//	timerTurn();
	System.out.println("Bot made turn - " + botturn[0] + " " + botturn[1]);
	
}

public int[][] getOwnField(){
	return this.ownField.getBoard();
}

public int[][] getEnemyField(){
	return this.enemyField.getBoard();
}

public int[][] getFullEnemyField(){
	return battle.getBotfield();
}





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


}