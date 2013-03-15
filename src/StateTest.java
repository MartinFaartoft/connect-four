import static org.junit.Assert.*;

import org.junit.Test;


public class StateTest {
	
	@Test
	public void lastStateShouldBeRight() {
		State s = new State(10,10);
		s.insertCoins(1, 1,2,3);
		assertEquals(3, s.PeekLatestMove().column);
	}
	
	@Test
	public void hashForTranspositionsShouldBeEqual() {
		State s1 = new State(3,3);
		s1.insertCoin(0,1);
		s1.insertCoin(1,2);
		s1.insertCoin(2,1);
		
		int h1 = s1.hashCode();
		
		s1.undoLastMove();
		s1.undoLastMove();
		s1.undoLastMove();
		
		s1.insertCoin(2,1);
		s1.insertCoin(1,2);
		s1.insertCoin(0,1);
		
		assertEquals(h1, s1.hashCode());
	}
	
	@Test
	public void undoShouldRollbackHash() {
		State s = new State(1,1);
		
		assertEquals(0, s.hashCode());
		
		s.insertCoin(0, 1);
		
		assertNotSame(0, s.hashCode());
		
		s.undoLastMove();
		
		assertEquals(0, s.hashCode());
	}
	
	@Test
	public void moveRowShouldBeCorrect() {
		State s = new State(1,1);
		s.insertCoin(0, 1);
		
		assertEquals(0, s.PeekLatestMove().row);
	}
}
