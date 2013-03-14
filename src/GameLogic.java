import java.util.HashMap;

public class GameLogic implements IGameLogic {
    private int width;
    private int playerID;
    private int opponentID;
    private State state;
    private TerminalStateChecker t = new TerminalStateChecker(4);
	private int height;
    
	private int cacheHits = 0;
	
	private HashMap<State, CacheEntry> cache = new HashMap<State, CacheEntry>();
	
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
    	int d = 1;
    	int move = -1;
    	int value;
    	while(d <= 10) {
    		value = maxValue(0, Integer.MIN_VALUE, Integer.MAX_VALUE, d);
    		move = cache.get(state).bestAction;
    		System.out.println(d + " ply, best move: " + move + ", value: " + value + ", cache hits: " + cacheHits);
    		d++;
    		cacheHits = 0;
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
    	
    	CacheEntry entry = cache.get(state);
    	if (entry != null && entry.subTreeDepth >= maxDepth - depth){
    		cacheHits++;
    		return entry.minimax;	
    	}
    	
    	
    	IGameLogic.Winner res = t.check(state);
    	if(res != IGameLogic.Winner.NOT_FINISHED) {
    		return utility(res);
    	}

    	
    	if(depth == maxDepth) {
    		return heuristic();
    	}
    	
    	int bestMove = -1;
    	int value = Integer.MIN_VALUE;

    	for (int i = 0; i < width; i++) { //For each possible action
    		if (state.isColumnFull(i)) continue; //Illegal move  
    		
    		state.insertCoin(i, playerID);
    		int v = minValue(depth + 1, a, b, maxDepth);
    		if(v > value) {
    			bestMove = i;
    			value = v;
    		}
    		state.undoLastMove();
    		
    		if (value >= b) {
    			break; //beta cut
    		}
    		
    		a = Math.max(a, value);
    	}
    	
    	updateCache(value, maxDepth - depth, bestMove);
    	
    	return value;
	}
    
    private void updateCache(int value, int i, int bestMove) {
    	cache.put(state, new CacheEntry(value, i, bestMove));
	}

	public int minValue (int depth, int a, int b, int maxDepth) {
    	
    	CacheEntry entry = cache.get(state);
    	if (entry != null && entry.subTreeDepth >= maxDepth - depth){
    		cacheHits++;
    		return entry.minimax;	
    	}
		
		
    	IGameLogic.Winner res = t.check(state);
    	if(res != IGameLogic.Winner.NOT_FINISHED) {
    		return utility(res);
    	}
    	
    	if(depth == maxDepth) {
    		return heuristic();
    	}
    	
    	int value = Integer.MAX_VALUE;
    	    	
    	for (int i = 0; i < width; i++) { //For each possible action
    		if (state.isColumnFull(i)) continue; //Illegal move  
    		
    		state.insertCoin(i, opponentID);
    		value = Math.min(value, maxValue(depth+1, a, b, maxDepth));
    		state.undoLastMove();
    		if(value <= a) {
    			break; //alpha cut
    		}
    		
    		b = Math.min(value, b);
    	}

    	updateCache(value, maxDepth - depth, -1); //todo
    	
    	return value;
	}
}
