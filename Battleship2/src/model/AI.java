package model;

public class AI {

	public int[] firstturn= {(int) Math.random() *10, (int) Math.random() *15};
	OpponentField enemyField;
	LocalField ownField;
	
// initialises its own field, creates empty enemy field
public AI() {
	this.ownField = new LocalField();
	this.ownField.placeShips();
	this.enemyField= new OpponentField();
	
}



//returns to computer the result of the latest shot and gets back new shot
public int[] computerTurn(ShotState shot, int i, int j) {
	
	
	this.enemyField.markTheCell(shot, i, j); //first computer updates enemy field and then uses it to calculate its next shot
	
	switch (shot) {
	case MISS: 
		return fireRandomShot();
	case HIT: 
		return fireRepeatedShot(i ,j);
	case KILLED: 
		return fireRandomShot();
	}
	
	
	int[] num = {1,2}; //let's hope it never works
	return num;
}





// first chooses a random cell to fire, if it's taken then goes further and checks every cell
// shoots with 50/50 chance
// if face end, then repeats
private int[] fireRandomShot() {
 
    int i=(int) Math.random() *10; //this is a random point from where we start firing
    int j=(int) Math.random() *15;
    int[] shot = new int[2];
    boolean choice;
    boolean stop = false;
    
    while (!stop) {   			// we run this while loop until we fire a shot
    for (int k=i; k<10; k++) { 
    	for (int s=j; s<15; s++) {
    		if (enemyField.getBoard()[k][s]==0) {
    			
    			choice = Math.random() < 0.5; // shoot 50/50
    			
    			if (choice) { 
    				shot[0]=k;
    				shot[1]=s;
    			return shot;
    			}
    		}
    	} 		
    }
    i=(int) Math.random() *10; //if after first run no shot is made, then we generate another point and start shooting again
    j=(int) Math.random() *15;
 }
    return shot; //lets hope it will never work
    
}

// we need to check on the right and on the left for empty spaces
// first we go to the right and make a shot if we see empty cell
// if no then left side algorithm starts
private int[] fireRepeatedShot( int i, int j) {
	int[] backshot= new int[2];
	
	   
	    	for (int s=j; s<15; s++) { 
	    		if (enemyField.getBoard()[i][s]==0) {
	    			backshot[0]=i;
    				backshot[1]=s;
    				return backshot;
	    		}
	    	}
	    	for (int s=j; s>-1; s--) {
	    		if (enemyField.getBoard()[i][s]==0) {
	    			backshot[0]=i;
    				backshot[1]=s;
    				return backshot;
	    		}
	    	}
	  
	return backshot; //this shoudn't work
	}

}