import java.util.HashMap;

public class GameLogicTM implements IGameLogic {
    private int width;
    private int playerID;
    private int opponentID;
    private State state;
    private TerminalStateChecker t = new TerminalStateChecker(4);
	private int height;
    
	private int cacheHits = 0;
	
	private HashMap<Integer, CacheEntry> cache = new HashMap<Integer, CacheEntry>();
	int[][] precomputedActions;
	
    public void initializeGame(int x, int y, int playerID) {
        width = x;
        height = y;
        this.playerID = playerID;
        opponentID = playerID == 1 ? 2 : 1;
        state = new State(width, y);
        initPrecomputedActions(width);
    }
	
    private void initPrecomputedActions(int width) {
		precomputedActions = new int[width][width];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < width; j++) {
				precomputedActions[i][j] = (i + j) % width;
			}
		}
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
    	long now = System.currentTimeMillis();
    	long limit = now + 10 * 1000;
    	while(System.currentTimeMillis() < limit) {
    		value = maxValue(0, Integer.MIN_VALUE, Integer.MAX_VALUE, d);
    		move = cache.get(state.hashCode()).bestAction;
    		System.out.println(d + " ply, best move: " + move + ", value: " + value + ", cache hits: " + cacheHits);
    		d++;
    		cacheHits = 0;
    	}
    	
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
    	int firstMove = 0;
    	CacheEntry entry = cache.get(state.hashCode());
    	if (entry != null) {
    		if(entry.subTreeDepth >= maxDepth - depth) {
    			cacheHits++;
    			return entry.minimax;
    		}
    		
    		//use best move from cache as first candidate
    		firstMove = entry.bestAction;
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
    	
    	for (int action : precomputedActions[firstMove]) {
    		if (state.isColumnFull(action)) continue; //Illegal move  
    		
    		state.insertCoin(action, playerID);
    		int v = minValue(depth + 1, a, b, maxDepth);
    		if(v > value) {
    			bestMove = action;
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
    
	public int minValue (int depth, int a, int b, int maxDepth) {
		int firstMove = 0;
    	CacheEntry entry = cache.get(state.hashCode());
    	if (entry != null) {
    		if(entry.subTreeDepth >= maxDepth - depth) {
    			cacheHits++;
    			return entry.minimax;
    		}
    		
    		//use best move from cache as first candidate
    		firstMove = entry.bestAction;
    	}
		
    	IGameLogic.Winner res = t.check(state);
    	if(res != IGameLogic.Winner.NOT_FINISHED) {
    		return utility(res);
    	}
    	
    	if(depth == maxDepth) {
    		return heuristic();
    	}
    	
    	int bestMove = -1;
    	int value = Integer.MAX_VALUE;    	

    	for(int action : precomputedActions[firstMove]) {
    		if (state.isColumnFull(action)) continue; //Illegal move  
    		
    		state.insertCoin(action, opponentID);
    		int v = maxValue(depth + 1, a, b, maxDepth);
    		if(v < value) {
    			value = v;
    			bestMove = action;
    		}
    		state.undoLastMove();
    		if(value <= a) {
    			break; //alpha cut
    		}
    		
    		b = Math.min(value, b);
    	}

    	updateCache(value, maxDepth - depth, bestMove);	
    	return value;
	}
	
	private void updateCache(int value, int subTreeSize, int bestMove) {
		cache.put(state.hashCode(), new CacheEntry(value, subTreeSize, bestMove));
		cache.put(state.reverseHashCode(), new CacheEntry(value, subTreeSize, width - bestMove - 1));
	}
}
