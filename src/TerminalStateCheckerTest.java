import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TerminalStateCheckerTest {
	private TerminalStateChecker c;
	private State s;
	@Before
	public void SetUp() {
		c = new TerminalStateChecker();
		s = new State(10, 10);
	}
	
	@Test
	public void shouldReturnNotFinishedForEmptyState() {
		assertEquals(IGameLogic.Winner.NOT_FINISHED, c.check(s));
	}
	
	@Test
	public void shouldReturnPlayer1ForSimpleVerticalWin() {
		s.insertCoins(1,0,1,2,3);
		assertEquals(IGameLogic.Winner.PLAYER1, c.check(s));
	}
	
	@Test 
	public void shouldReturnPlayer1ForSimpleHorizontalWin() {
		s.insertCoins(1,0,0,0,0);
		assertEquals(IGameLogic.Winner.PLAYER1, c.check(s));
	}
	
	@Test
	public void shouldReturnPlayer1ForMiddleCoinInFour() {
		s.insertCoins(1, 0, 1, 4, 3);
		assertEquals(IGameLogic.Winner.PLAYER1, c.check(s));
	}
}
