
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
		is(IGameLogic.Winner.NOT_FINISHED);
	}
	
	@Test
	public void shouldReturnPlayer1ForSimpleHorizontalWin() {
		s.insertCoins(1,0,1,2,3);
		is(IGameLogic.Winner.PLAYER1);
	}
	
	@Test 
	public void shouldReturnPlayer1ForSimpleVerticalWin() {
		s.insertCoins(1,0,0,0,0);
		is(IGameLogic.Winner.PLAYER1);
	}
	
	@Test
	public void shouldReturnPlayer1ForMiddleCoinInFour() {
		s.insertCoins(1, 0, 1, 3, 2);
		is(IGameLogic.Winner.PLAYER1);
	}
	
	@Test
	public void shouldReturnP1ForDiagonalUpWin() {
		s.insertCoins(2, 1, 2, 2, 3, 3, 3);
		s.insertCoins(1, 0, 1, 2, 3);
		is(IGameLogic.Winner.PLAYER1);
	}
	
	@Test
	public void shouldReturnP1ForDiagonalDownWin() {
		s.insertCoins(2, 1, 1, 1, 2, 2, 3);
		s.insertCoins(1, 1, 2, 3, 4);
		is(IGameLogic.Winner.PLAYER1);
	}
	
	@Test
	public void shouldReturnTIEForFullBoardWithNoWinner() {
		s = new State(1, 1);
		s.insertCoin(0, 1);
		is(IGameLogic.Winner.TIE);
	}
	
	private void is(IGameLogic.Winner w) {
		assertEquals(w, c.check(s));
	}
}
