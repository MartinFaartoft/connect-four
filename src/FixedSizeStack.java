
public class FixedSizeStack {
	private int[] _values;
	private int _size = 0;
	private int _capacity;
	public FixedSizeStack(int capacity) {
		_capacity = capacity;
		_values = new int[_capacity];
	}
	
	public void push(int n) {
		if(_size == _capacity) {
			throw new IllegalStateException("cannot push to full stack");
		}
		
		_values[_size++] = n;
	}
	
	public int pop() {
		if(_size == 0) {
			throw new IllegalStateException("cannot pop from empty stack");
		}
		int index = _size-- - 1;
		int val = _values[index];
		_values[index] = 0;
		
		return val;
	}
	
	public int peek(int index) {
		return _values[index];
	}
	
	public int peek() {
		return _values[_size-1];
	}

	public int size() {
		return _size;
	}

	public boolean isFull() {
		return _size == _capacity;
	}
}
