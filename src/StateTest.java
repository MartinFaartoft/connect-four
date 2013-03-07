import static org.junit.Assert.*;

import org.junit.Test;


public class StateTest {
	
	@Test
	public void lastStateShouldBeRight() {
		State s = new State(10,10);
		s.insertCoins(1, 1,2,3);
		assertEquals(3, s.PeekLatestMove().column);
	}
}
