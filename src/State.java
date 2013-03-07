public class State
{
	private FixedSizeStack[] _board;
	private int latestColPushed = -1;
	
	public State(int width, int height) {
		_board = new FixedSizeStack[width];
		
		for(int i = 0; i < width; i++) {
			_board[i] = new FixedSizeStack(height);
		}
	}
	
	public void insertCoins(int player, int... columns) {
		for (int c : columns) {
			_board[c].Push(player);
			latestColPushed = c;
		}
	}
	
	public Move PeekLatestMove() {
		if (latestColPushed == -1)
			return null;
		
		FixedSizeStack latestColumn = _board[latestColPushed];
		return new Move(latestColPushed, latestColumn.size() - 1, latestColumn.Peek());
	}
	
	public int Peek(int col, int row) {
		return _board[col].Peek(row);
	}
}