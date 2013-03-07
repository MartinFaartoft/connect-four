
public class GameLogic implements IGameLogic {
    private int x = 0;
    private int y = 0;
    private int playerID;
    
    private int[][] board;
    private int opponentID;
    
    int expandedNodes = 0;
    
    public GameLogic() {

    }
	
    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        this.playerID = playerID;
        
        board = new int[x][y];
        if (playerID == 1) opponentID = 2;
        if (playerID == 2) opponentID = 1;
    }
	
    public Winner gameFinished() {
        //TODO Write your implementation for this method
        return Winner.NOT_FINISHED;
    }


    public void insertCoin(int column, int playerID) {
        //TODO Write your implementation for this method	
    	board = resultOfActionBoard(board, column, playerID);
    }

    public int decideNextMove() {    	
    	return minimaxDecision();
    }
    
    public int minimaxDecision (){
    	printBoard(board);
    	
    	expandedNodes = 0;
    	int resultAction = 0;
    	double resultValue = Double.NEGATIVE_INFINITY;
    	
    	for (int i = 0; i < x; i++) { //For each possible action
    		int[][] boardAfterAction = new int[x][y];
    		boardAfterAction = resultOfActionBoard(board, i, playerID);
    		double value = minValue(boardAfterAction);
    		
			if (value > resultValue){
				resultAction = i;
				resultValue = value;
			}    		
    	}
    	    	    	    	
    	System.out.println("Ended calculation, expandedNodes: " + expandedNodes);
    	System.out.println("resultAction: " + resultAction + ", resultValue: " + resultValue);
    	
    	return resultAction;
    }
    
    public double maxValue (int[][] boardInspecting){
    	System.out.println("PLAYER MAX - expandedNodes: " + expandedNodes);
    	
    	
    	expandedNodes++;
    	//if (TERMINAL-TEST) return UTILITY(state);
    	if (simpleCheckRowTest(boardInspecting)) return 5 * emptySlotsCount(boardInspecting);

    	if (simpleCheckColumnTest(boardInspecting)) return 10 * emptySlotsCount(boardInspecting);
    	if (simpleTerminalTest(boardInspecting)) return 0;
    	
    	double value = Double.NEGATIVE_INFINITY;

    	for (int i = 0; i < x; i++) { //For each possible action
    		
    		if (boardInspecting[i][y - 1] > 0) continue; //Illegal move  
    		int[][] boardAfterAction = new int[x][y];
    		printBoard(boardInspecting);
    		boardAfterAction = resultOfActionBoard(boardInspecting, i, playerID);
    		printBoard(boardAfterAction);
    		value = Math.max(value, minValue(boardAfterAction));
    	}
    	
    	System.out.println("Value of node: " + value);

    	
    	return value;
	}
    
    public double minValue (int[][] boardInspecting){
    	System.out.println("PLAYER MIN - expandedNodes: " + expandedNodes);
    	
    	expandedNodes++;
    	//if (TERMINAL-TEST) return UTILITY(state);
    	if (simpleCheckRowTest(boardInspecting)) return 5 * emptySlotsCount(boardInspecting);

    	if (simpleCheckColumnTest(boardInspecting)) return 10 * emptySlotsCount(boardInspecting);
    	if (simpleTerminalTest(boardInspecting)) return 0;
    	
    	double value = Double.POSITIVE_INFINITY;
    	    	
    	for (int i = 0; i < x; i++) { //For each possible action
    		if (boardInspecting[i][y - 1] > 0) continue; //Illegal move  
    		int[][] boardAfterAction = new int[x][y];
    		printBoard(boardInspecting);
    		boardAfterAction = resultOfActionBoard(boardInspecting, i, opponentID);
    		printBoard(boardAfterAction);
    		value = Math.min(value, maxValue(boardAfterAction));
    	}
    	
    	System.out.println("Value of node: " + value);

    	
    	return value;
	}
    
    public boolean simpleTerminalTest(int[][] boardToCheck){
    	for (int i = 0; i < x; i++) {
    		if (boardToCheck[i][y - 1] == 0) return false;
        }
    	return true;
    
    }
    
    public boolean simpleCheckColumnTest(int[][] boardToCheck){
    	
    	for (int i = 0; i < x; i++) {
    		if (boardToCheck[i][0] == playerID){
    			if (boardToCheck[i][1] == playerID){
    				return true;
    				
    			}
    		}
        }
    	return false;
    }
    
    public boolean simpleCheckRowTest(int[][] boardToCheck){
    	
    	for (int j = 0; j < y; j++) {
    		if (boardToCheck[0][j] == playerID){
    			if (boardToCheck[1][j] == playerID){
    				return true;
    				
    			}
    		}
        }
    	return false;
    }
    
    public void printBoard(int[][] boardInspecting){
    	System.out.println("BOARD: ");
    	for (int j = y - 1; j > -1; j--) {
				
			for (int i = 0; i < x; i++) {
				System.out.print(boardInspecting[i][j] + " ");
			}
			System.out.println(" ");
    	}
    }
    
    public int emptySlotsCount(int[][] boardInspecting){
    	int count = 0;
    	for (int i = 0; i < x; i++) {
        	for (int j = 0; j < y; j++) {
        		if (boardInspecting[i][j] == 0) count++;
        	}
    	}
    	return count;
    }
    
    public int[][] resultOfActionBoard(int[][] beforeBoard, int action, int player){
    	int[][] newboard = new int[x][y];

    	for (int i = 0; i < x; i++) {
    		for (int j = 0; j < y; j++) {
    			newboard[i][j] = beforeBoard[i][j];
    		}
    	}
    
    	//printBoard(newboard);
    	
    	for (int j = 0; j < y; j++){
    		if (beforeBoard[action][j] == 0){
    			newboard[action][j] = player;
    			break;
    		}
    	}
    	
    	return newboard;
    }

}
