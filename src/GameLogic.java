
public class GameLogic implements IGameLogic {
    private int width = 0;
    private int height = 0;
    private int playerID;
    private int opponentID;
    private State state;
    private TerminalStateChecker t = new TerminalStateChecker();
    
    int expandedNodes = 0;
    
    public GameLogic() {

    }
	
    public void initializeGame(int x, int y, int playerID) {
        width = x;
        height = y;
        this.playerID = playerID;
        opponentID = playerID == 1 ? 2 : 1;
        state = new State(width, height);
    }
	
    public Winner gameFinished() {
        return t.check(state);
    }


    public void insertCoin(int column, int player) {
    	state.insertCoin(column, player);
    }

    public int decideNextMove() {    	
    	return minimaxDecision();
    }
    
    public int minimaxDecision (){
    	expandedNodes = 0;
    	int resultAction = -1;
    	double resultValue = Double.NEGATIVE_INFINITY;
    	
    	for (int i = 0; i < width; i++) { //For each possible action
    		state.insertCoin(i, playerID);
    		double value = minValue();
    		
			if (value > resultValue){
				resultAction = i;
				resultValue = value;
			}    		
    	}
    	    	    	    	
    	System.out.println("Ended calculation, expandedNodes: " + expandedNodes);
    	System.out.println("resultAction: " + resultAction + ", resultValue: " + resultValue);
    	
    	return resultAction;
    }
    
    public double maxValue (){
    	System.out.println("PLAYER MAX - expandedNodes: " + expandedNodes);
    	
    	expandedNodes++;
    	//if (TERMINAL-TEST) return UTILITY(state);
    	//if (simpleCheckRowTest(boardInspecting)) return 5 * emptySlotsCount(boardInspecting);

    	//if (simpleCheckColumnTest(boardInspecting)) return 10 * emptySlotsCount(boardInspecting);
    	//if (simpleTerminalTest(boardInspecting)) return 0;
    	
    	boolean isTerminal = false;
    	
    	if(isTerminal) {
    		return -1; //TODO some value
    	}
    	
    	
    	double value = Double.NEGATIVE_INFINITY;

    	for (int i = 0; i < width; i++) { //For each possible action
    		
    		if (state.isColumnFull(i)) continue; //Illegal move  
    		
    		state.insertCoin(i, playerID);
    		value = Math.max(value, minValue());
    		state.undoLastMove();
    	}
    	
    	System.out.println("Value of node: " + value);

    	return value;
	}
    
    public double minValue (){
    	System.out.println("PLAYER MIN - expandedNodes: " + expandedNodes);
    	
    	expandedNodes++;
    	//if (TERMINAL-TEST) return UTILITY(state);
    	//if (simpleCheckRowTest(state)) return 5 * emptySlotsCount(state);

    	//if (simpleCheckColumnTest(state)) return 10 * emptySlotsCount(state);
    	//if (simpleTerminalTest(state)) return 0;
    	
    	boolean isTerminal = false;
    	
    	if(isTerminal) {
    		return -1; //TODO some value
    	}
    	
    	double value = Double.POSITIVE_INFINITY;
    	    	
    	for (int i = 0; i < width; i++) { //For each possible action
    		if (state.isColumnFull(i)) continue; //Illegal move  
    		
    		state.insertCoin(i, opponentID);
    		value = Math.min(value, maxValue());
    		state.undoLastMove();
    	}
    	
    	System.out.println("Value of node: " + value);

    	return value;
	}
    
    public boolean simpleTerminalTest(int[][] boardToCheck){
    	for (int i = 0; i < width; i++) {
    		if (boardToCheck[i][height - 1] == 0) return false;
        }
    	return true;
    
    }
    
    public boolean simpleCheckColumnTest(int[][] boardToCheck){
    	
    	for (int i = 0; i < width; i++) {
    		if (boardToCheck[i][0] == playerID){
    			if (boardToCheck[i][1] == playerID){
    				return true;
    				
    			}
    		}
        }
    	return false;
    }
    
    public boolean simpleCheckRowTest(State boardToCheck){
    	return false;
//    	for (int j = 0; j < y; j++) {
//    		if (boardToCheck[0][j] == playerID){
//    			if (boardToCheck[1][j] == playerID){
//    				return true;
//    				
//    			}
//    		}
//        }
//    	return false;
    }
    
    public void printBoard(int[][] boardInspecting){
    	System.out.println("BOARD: ");
    	for (int j = height - 1; j > -1; j--) {
				
			for (int i = 0; i < width; i++) {
				System.out.print(boardInspecting[i][j] + " ");
			}
			System.out.println(" ");
    	}
    }
    
    public int emptySlotsCount(int[][] boardInspecting){
    	int count = 0;
    	for (int i = 0; i < width; i++) {
        	for (int j = 0; j < height; j++) {
        		if (boardInspecting[i][j] == 0) count++;
        	}
    	}
    	return count;
    }
}
