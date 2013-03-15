
public class DegreesOfFreedomHeuristic implements IHeuristic {
	private static int[][] vectors = new int[][] {{1, 1}, {1, -1}, {1, 0}, {0, 1}};
	int maxDistance = 4;
	@Override
	public int Evaluate(State s, int playerID) {
		int v = 0;
		for(Move move : s.getMoves()) {
			v += CountFreedoms(move, playerID, s);
		}
		
		return v;
	}

	private int CountFreedoms(Move move, int playerID, State s) {
		int col = move.column;
		int row = move.row;
		int p = move.player;
		int freedoms = 0;
		for(int[] v : vectors) {
			int x = col;
			int y = row;
			int leftdistance = 0;
			while(leftdistance <= maxDistance && s.Peek(x, y) == p || s.Peek(x, y) == 0) { //look ahead 
				leftdistance++;
				x += v[0];
				y += v[1];
			}
			x = col;
			y = row;
			int rightdistance = 0;
			while(rightdistance <= maxDistance && s.Peek(x, y) == p || s.Peek(x, y) == 0) { //look behind
				rightdistance++;
				x -= v[0];
				y -= v[1];
			}
			freedoms += Math.max(0, rightdistance + leftdistance - 4); 			
		}
		
		return freedoms * (p == playerID ? 1 : -1);
	}
	
}
