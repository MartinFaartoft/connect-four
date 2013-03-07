public class TerminalStateChecker {
	private static int[][] vectors = new int[][] {{1, 1}, {1, -1}, {1, 0}, {0, 1}};
	
	public IGameLogic.Winner check(State s) {
		//get index of last move
		Move lastMove = s.PeekLatestMove();
		if(lastMove == null) {
			return IGameLogic.Winner.NOT_FINISHED;
		}
		
		int p = lastMove.player;
		int col = lastMove.column;
		int row = s.columnHeight(col) - 1;
		
		for (int[] v : vectors) {
			int found = 1;
			int x = col + v[0];
			int y = row + v[1];
			
			while(found < 4 && s.Peek(x, y) == p) { //look ahead 
				found++;
				x += v[0];
				y += v[1];
			}
			
			x = col - v[0];
			y = row - v[1];
			
			while(found < 4 && s.Peek(x, y) == p) { //look behind
				found++;
				x -= v[0];
				y -= v[1];
			}
			
			if (found >= 4) {
				return p == 1 ? IGameLogic.Winner.PLAYER1 : IGameLogic.Winner.PLAYER2;
			}
		}
		
		if(s.isBoardFull()) {
			return IGameLogic.Winner.TIE;
		}
		
		return IGameLogic.Winner.NOT_FINISHED;
	}
}

