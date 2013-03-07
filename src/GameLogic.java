
public class GameLogic implements IGameLogic {
    private int width;
    private int playerID;
    private int opponentID;
    private State state;
    private TerminalStateChecker t = new TerminalStateChecker(3);
    
    public void initializeGame(int x, int y, int playerID) {
        width = x;
        this.playerID = playerID;
        opponentID = playerID == 1 ? 2 : 1;
        state = new State(width, y);
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
    	int resultAction = -1;
    	int resultValue = Integer.MIN_VALUE;
    	
    	for (int i = 0; i < width; i++) { //For each possible action
    		if (state.isColumnFull(i)) continue; //Illegal move 
    		state.insertCoin(i, playerID);
    		int value = minValue(1);
    		state.undoLastMove();
			if (value > resultValue){
				resultAction = i;
				resultValue = value;
			}    		
    	}

    	System.out.println("resultAction: " + resultAction + ", resultValue: " + resultValue);
    	
    	return resultAction;
    }
    
    private int utility(IGameLogic.Winner w) {
    	switch(w) {
    	case PLAYER1:
    		return playerID == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
    	
    	case PLAYER2:
    		return playerID == 2 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
    		
    	default:
    		return 0;
    	}
    	
    }
    
    public int maxValue (int depth) {
    	IGameLogic.Winner res = t.check(state);
    	if(res != IGameLogic.Winner.NOT_FINISHED) {
    		return utility(res);
    	}
    	
    	int value = Integer.MIN_VALUE;

    	for (int i = 0; i < width; i++) { //For each possible action
    		
    		if (state.isColumnFull(i)) continue; //Illegal move  
    		
    		state.insertCoin(i, playerID);
    		value = Math.max(value, minValue(depth+1));
    		state.undoLastMove();
    	}
    	
    	return value;
	}
    
    public int minValue (int depth) {
    	
    	IGameLogic.Winner res = t.check(state);
    	if(res != IGameLogic.Winner.NOT_FINISHED) {
    		return utility(res);
    	}
    	
    	int value = Integer.MAX_VALUE;
    	    	
    	for (int i = 0; i < width; i++) { //For each possible action
    		if (state.isColumnFull(i)) continue; //Illegal move  
    		
    		state.insertCoin(i, opponentID);
    		value = Math.min(value, maxValue(depth+1));
    		state.undoLastMove();
    	}

    	return value;
	}
}
