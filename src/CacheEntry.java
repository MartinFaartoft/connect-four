
public class CacheEntry {
	
	int minimax;
	int subTreeDepth;
	int bestAction;
	
	public CacheEntry (int minimax, int subTreeDepth, int bestAction){
		this.minimax = minimax;
		this.subTreeDepth = subTreeDepth;
		this.bestAction = bestAction;
	}
}
