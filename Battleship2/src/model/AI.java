package model;

import java.util.Arrays;

public class AI {

	
	private OpponentField enemyField;
	private LocalField ownField;
	private int[] lastBotTurn = new int[2];
	private boolean first = true;
	private boolean shipIsDead = true;
	
// intialiazes its own field, creates empty enemy field
public AI() {
	this.ownField = new LocalField();
	this.ownField.placeShips();
	this.enemyField= new OpponentField();

}
//returns to computer the result of the latest shot and gets back new shot
public int[] computerTurn(ShotState shot) {	
	
	if (first) {
		lastBotTurn[0] = (int) (Math.random() *10);
		lastBotTurn[1] = (int) (Math.random() *15);
		this.first = false;
		return lastBotTurn;
	}
	else if ((shipIsDead == false) && (shot != ShotState.KILLED)){
		this.enemyField.markTheCell(shot, lastBotTurn[0], lastBotTurn[1]); 
		return fireRepeatedShot(lastBotTurn[0], lastBotTurn[1]);
	}
	else {
	this.enemyField.markTheCell(shot, lastBotTurn[0], lastBotTurn[1]); //first computer updates enemy field and then uses it to calculate its next shot
	switch (shot) {
	case MISS: 
		return fireRandomShot();
	case HIT:
		shipIsDead = false;
		return fireRepeatedShot(lastBotTurn[0], lastBotTurn[1]);
	default:		//it's case KILLED namely
		shipIsDead = true;
		return fireRandomShot();
	}	
	}
}
// gives to game its field
public int[][] getField() {
	return ownField.getBoard();
}
// first chooses a random cell to fire, if it's taken then goes further and checks every cell
// shoots with 50/50 chance
// if face end, then repeats
private int[] fireRandomShot() {  
    boolean found = false;
    while (found == false) {   			// we run this while loop until we fire a shot. 
        int i = (int) (Math.random() *10); //this is a random point from where we start firing
        int j = (int) (Math.random() *15);
    for (int k=i; k<10; k++) { 		// if after first loop we don't fire a shot (all cells are taken) then we start again with another random point
    	for (int s=j; s<15; s++) {
    		if ( (found == false) && (enemyField.getBoard()[k][s]==0) ) {
    			
    			lastBotTurn[0] = k;
    			lastBotTurn[1] = s;
    				found = true;
    			}
    		}
    	} 		
    }
    return lastBotTurn; 
	}   

private int[] fireRepeatedShot( int i, int j) {
	boolean choice = Math.random() < 0.5; //randomly claculates the direction of checking
	boolean found = false;
	while(!found) {
	   	if (choice) {						//if true - we check on right. OTherwise - on left
	    	for (int s=j; s<15; s++) { 
	    		if (enemyField.getBoard()[i][s]==0 && (found == false) ) {
	    			lastBotTurn[0]=i;
	    			lastBotTurn[1]=s;
    				found = true;
    				
	    		}
	    	}
	   	}
	   	else {	for (int s=j; s>-1; s--) {
	    		if (enemyField.getBoard()[i][s]==0 && (found == false) ) {
	    			lastBotTurn[0]=i;
	    			lastBotTurn[1]=s;
    				found = true;
    				
	    		}
	    	}
	   	}
	   	choice = !choice;
	}
	System.out.print(Arrays.toString(lastBotTurn));
	System.out.println("repeated");
	return lastBotTurn; 
	}
	
}
// we need to check on the right and on the left for empty spaces
// first we go to the right and make a shot if we see empty cell
// if no then left side algorithm starts

/*
public static void main(String [] args) {
	AI bot = new AI();
	System.out.println("Enemy field: ");
	for (int[] x : bot.enemyField.getBoard())
	{
	   for (int y : x)
	   {
	        System.out.print(y + " ");
	   }
	   System.out.println();
	}
	System.out.println("Your field: ");
	for (int[] x : bot.ownField.getBoard())
	{
	   for (int y : x)
	   {
	        System.out.print(y + " ");
	   }
	   System.out.println();
	}
	 
	System.out.println(bot.firstturn[0]);
	
}
*/
	


/*private int[] CheckRightLeftAsShot(int i, int j) {
	int[] giveBack = new int[4];
	giveBack[0]=-1;
	giveBack[1]=-1;
	giveBack[2]=-1;
	giveBack[3]=-1;
	// check cells from right.
	for (int k = j; k < 15; k++) {
		// miss cell is marked as 3, thus, the check can start from j coordinate (which was changed to hit before
		// the method was invoked. It allows to exclude checks required if taking j = j + 1 || j = j - 1.
		if (this.board[i][k] == 0) {
			this.board[i][k] = MISS_CELL;
			giveBack[0] = i;
			giveBack[1] = k;
			break;
		} else if (this.board[i][k] == MISS_CELL) {
			break;
		}
	}
	// check cells from left.
	for (int k = j; k >= 0; k--) {
		if (this.board[i][k] == 0) {
			this.board[i][k] = MISS_CELL;
			giveBack[2] = i;
			giveBack[3] = k;
			break;
		} else if (this.board[i][k] == MISS_CELL) {
			break;
		}
	}
	return giveBack;
}

private void StartPainting()*/