public class TerminalStateChecker {
	private static int[][] Directions = new int[][] {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
	
	private State _currentState;
	
	public IGameLogic.Winner check(State s) {
		_currentState = s;
		//get index of last move
		Move lastMove = s.PeekLatestMove();
		if(lastMove == null) {
			return IGameLogic.Winner.NOT_FINISHED;
		}
		
		int p = lastMove.player;
		int col = lastMove.column;
		int row = _currentState.columnHeight(col) - 1;
		
		for (int[] d : Directions) {
			if(foundConnected(4, col, row, p, d)) {
				return p == 1 ? IGameLogic.Winner.PLAYER1 : IGameLogic.Winner.PLAYER2;
			}
		}
		//for each possible neighbor (8), look for 4 in a row
		
		return IGameLogic.Winner.NOT_FINISHED;
	}
	
	private boolean foundConnected(int n, int col, int row, int player, int[] direction) {
		if(n == 0) {
			return true;
		}
		
		if(col < 0) // || col >= width
			return false;
		
		if(row < 0) // || row >= height
			return false;
		
		if(_currentState.Peek(col, row) != player) //current is right
			return false;
		
		return foundConnected(n-1, col + direction[0], row + direction[1], player, direction);
	}
}

