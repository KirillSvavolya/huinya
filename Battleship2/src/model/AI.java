package model;

public class AI {

	public int[] firstturn = new int[2];
	public OpponentField enemyField;
	public LocalField ownField;
	
// intialiazes its own field, creates empty enemy field
public AI() {
	this.ownField = new LocalField();
	this.ownField.placeShips();
	this.enemyField= new OpponentField();
	this.firstturn[0] = (int) (Math.random() *10);
	this.firstturn[1] = (int) (Math.random() *15);
}



//returns to computer the result of the latest shot and gets back new shot
public int[] computerTurn(ShotState shot, int i, int j) {
	
	
	this.enemyField.markTheCell(shot, i, j); //first computer updates enemy field and then uses it to calculate its next shot
	
	switch (shot) {
	case MISS: 
		return fireRandomShot();
	case HIT: 
		return fireRepeatedShot(i ,j);
	default: 							//it's case KILLED namely
		return fireRandomShot();
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
 

    int[] shot = new int[2];
    boolean found = false;
    
    while (found == false) {   			// we run this while loop until we fire a shot. 
    	
        int i = (int) (Math.random() *10); //this is a random point from where we start firing
        int j = (int) (Math.random() *15);
        
    for (int k=i; k<10; k++) { 		// if after first loop we don't fire a shot (all cells are taken) then we start again with another random point
    	for (int s=j; s<15; s++) {
    		if ( (found == false) && (enemyField.getBoard()[k][s]==0) ) {
    			
    				shot[0] = k;
    				shot[1] = s;
    				found = true;
    			}
    		}
    	} 		
    

    }

    return shot; 
	}   


// we need to check on the right and on the left for empty spaces
// first we go to the right and make a shot if we see empty cell
// if no then left side algorithm starts
private int[] fireRepeatedShot( int i, int j) {
	int[] backshot= new int[2];
	boolean choice = Math.random() < 0.5; //randomly claculates the direction of checking
	boolean found = false;
	
	
	   	if (choice) {						//if true - we check on right. OTherwise - on left
	    	for (int s=j; s<15; s++) { 
	    		if (enemyField.getBoard()[i][s]==0 && (found == false) ) {
	    			backshot[0]=i;
    				backshot[1]=s;
    				found = true;
    				
	    		}
	    	}
	   	}
	   	else {	for (int s=j; s>-1; s--) {
	    		if (enemyField.getBoard()[i][s]==0 && (found == false) ) {
	    			backshot[0]=i;
    				backshot[1]=s;
    				found = true;
    				
	    		}
	    	}
	   	}
	  
	return backshot; 
	}
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
	}


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