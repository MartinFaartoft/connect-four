import java.util.Stack;

public class State
{
	private FixedSizeStack[] _board;
	private Stack<Move> _moves;
	private int _boardSize;
	public State(int width, int height) {
		_board = new FixedSizeStack[width];
		_boardSize = width * height;
		for(int i = 0; i < width; i++) {
			_board[i] = new FixedSizeStack(height);
		}
		
		_moves = new Stack<Move>();
	}
	
	public void insertCoins(int player, int... columns) {
		for (int c : columns) {
			_board[c].Push(player);
			_moves.push(new Move(c, player));
		}
	}
	
	public void insertCoin(int column, int player) {
		insertCoins(player, column);
	}
	
	public Move PeekLatestMove() {
		if (_moves.isEmpty())
			return null;
		
		return _moves.peek();
	}
	
	public int Peek(int col, int row) {
		return _board[col].peek(row);
	}

	public boolean isColumnFull(int col) {
		return _board[col].isFull();
	}

	public void undoLastMove() {
		if(_moves.isEmpty()) {
			return;
		}
		
		Move m = _moves.pop();
		undo(m);
	}

	private void undo(Move m) {
		_board[m.column].pop();
	}

	public int columnHeight(int col) {
		return _board[col].size();
	}
}