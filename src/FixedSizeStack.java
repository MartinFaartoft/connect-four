
public class FixedSizeStack {
	private int[] _values;
	private int _size = 0;
	private int _capacity;
	public FixedSizeStack(int capacity) {
		_capacity = capacity;
		_values = new int[_capacity];
	}
	
	public void Push(int n) {
		if(_size == _capacity) {
			throw new IllegalStateException("cannot push to full stack");
		}
		
		_values[_size++] = n;
	}
	
	public int Pop() {
		if(_size == 0) {
			throw new IllegalStateException("cannot pop from empty stack");
		}
		
		int val = _values[_size];
		_values[_size--] = 0;
		
		return val;
	}
	
	public int Peek(int index) {
		return _values[index];
	}
	
	public int Peek() {
		return _values[_size-1];
	}

	public int size() {
		return _size;
	}
}
