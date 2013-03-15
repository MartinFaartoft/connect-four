
public class DegreesOfFreedomHeuristic implements IHeuristic {
	private static final int[][] vectors = new int[][] {{1, 1}, {1, -1}, {1, 0}, {0, 1}};
	private final int maxDistance = 4;
	private final int ownPieceMultiplier = 1;
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
		int piece;
		for(int[] v : vectors) {
			int ownPieces = 0;
			int x = col;
			int y = row;
			int leftdistance = 0;
			while(leftdistance <= maxDistance) { //look ahead 
				piece = s.Peek(x, y);
				if(piece != 0 && piece != p) break;
				
				if(piece == p) {
					ownPieces++;
				}
				leftdistance++;
				x += v[0];
				y += v[1];
			}
			x = col;
			y = row;
			int rightdistance = 0;
			while(rightdistance <= maxDistance) { //look behind
				piece = s.Peek(x, y);
				if(piece != 0 && piece != p) break;
				
				if(piece == p) {
					ownPieces++;
				}
				rightdistance++;
				x -= v[0];
				y -= v[1];
			}
			
			int freedomsForThisPiece = Math.max(0, rightdistance + leftdistance - 4); 
			freedoms += freedomsForThisPiece == 0 ? 0 : freedomsForThisPiece + ownPieces * ownPieceMultiplier; 			
		}
		
		return freedoms * (p == playerID ? 1 : -1);
	}
	
}
