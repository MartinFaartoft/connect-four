public class MoveOrderingGameLogic implements IGameLogic {
    private int width;
    private int playerID;
    private int opponentID;
    private State state;
    private TerminalStateChecker t = new TerminalStateChecker(4);
	private int height;
	private int[] bestMoves;
    
    public void initializeGame(int x, int y, int playerID) {
        width = x;
        height = y;
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
    	int maxDepth = 10;
    	bestMoves = new int[10];
    	for(int i = 0; i < maxDepth; i++) {
    		bestMoves[i] = -1;
    	}
    	
    	int d = 1;
    	int move = -1;
    	while(d <= maxDepth) {
    		move = maxValue(0, Integer.MIN_VALUE, Integer.MAX_VALUE, d);
    		System.out.println(d + " ply, best move: " + move);
    		d++;
    	}
    	//return maxValue(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    	
    	return move;
    }
    
    private int utility(IGameLogic.Winner w) {
    	switch(w) {
    	case PLAYER1:
    		return playerID == 1 ? 1000 : -1000;
    	
    	case PLAYER2:
    		return playerID == 2 ? 1000 : -1000;
    		
    	default:
    		return 0;
    	}
    	
    }
    
    private int heuristic() {
    	double val = 0;
    	double vertCenter = (double)width / 2;
    	double horizCenter = (double)height / 2;
    	double maxDistance = Math.sqrt(Math.pow(vertCenter, 2) + Math.pow(horizCenter, 2));
    	int p;
    	double fieldVal = 0;
    	for(int i = 0; i < width; i++) {
    		for(int j = 0; j < height; j++) {
    			p = state.Peek(i, j);
    			if(p < 1) continue;
    			
    			fieldVal = maxDistance - Math.sqrt(Math.pow(i - vertCenter, 2) + Math.pow(j - horizCenter, 2)); 
    			val = p == playerID ? val + fieldVal : val - fieldVal;
    		}
    	}
    	
    	return (int)val;
    }
    
    public int maxValue (int depth, int a, int b, int maxDepth) {
    	IGameLogic.Winner res = t.check(state);
    	if(res != IGameLogic.Winner.NOT_FINISHED) {
    		return utility(res);
    	}
    	
    	if(depth == maxDepth) {
    		return heuristic();
    	}
    	
    	int value = Integer.MIN_VALUE;

    	int[] actions = getOrderedActions(bestMoves[depth]);
    	for (int action : actions) { //For each possible action
    		if (state.isColumnFull(action)) continue; //Illegal move  
    		
    		state.insertCoin(action, playerID);
    		int v = minValue(depth + 1, a, b, maxDepth);
    		if(v > value) {
    			setBestMove(depth, action);
    			value = v;
    		}
    		state.undoLastMove();
    		
    		if (value >= b) {
    			break; //beta cut
    		}
    		
    		a = Math.max(a, value);
    	}
    	
    	if(depth == 0) {
    		return bestMoves[depth];
    	}
    	
    	return value;
	}
    
    private void setBestMove(int depth, int action) {
    	if(bestMoves[depth] == action) {
    		return;
    	}
    	
    	bestMoves[depth] = action;
		for(int i = depth + 1; i < bestMoves.length; i++) {
			bestMoves[i] = -1;
		}
	}

	public int[] getOrderedActions(int bestMove) {
		int[] actions = new int[width];
		
		boolean addedBestMove = false;
		if(bestMove > -1) {
			actions[0] = bestMove;
			addedBestMove = true;
			
			int j = 0;
			for(int i = 1; i < width; i++) {
				if(j == bestMove) {
					j++;
				}
				
				actions[i] = j++;
			}
			
			return actions;
		}
		
		int j = 0;
		
		for(int i = 0; i < width; i++) {
			actions[i] = i;
		}
		
		return actions;
	}

	public int minValue (int depth, int a, int b, int maxDepth) {
    	
    	IGameLogic.Winner res = t.check(state);
    	if(res != IGameLogic.Winner.NOT_FINISHED) {
    		return utility(res);
    	}
    	
    	if(depth == maxDepth) {
    		return heuristic();
    	}
    	
    	int value = Integer.MAX_VALUE;
    	
    	int[] actions = getOrderedActions(bestMoves[depth]);
    	for (int action : actions) { //For each possible action
    		if (state.isColumnFull(action)) continue; //Illegal move  
    		
    		state.insertCoin(action, opponentID);
    		int v = maxValue(depth+1, a, b, maxDepth);
    		if(v < value) {
    			setBestMove(depth, action);
    			value = v;
    		}
    		state.undoLastMove();
    		if(value <= a) {
    			break; //alpha cut
    		}
    		
    		b = Math.min(value, b);
    	}

    	return value;
	}
}
