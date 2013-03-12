import static org.junit.Assert.*;

import org.junit.Test;


public class MoveOrderingTest {

	@Test
	public void shouldTakeBestMoveFirst() {
		MoveOrderingGameLogic g = new MoveOrderingGameLogic();
		g.initializeGame(3, 3, 1);
		int[] result = g.getOrderedActions(2);
		int[] expected = new int[] {2, 0, 1};
		
		assertArrayEquals(expected, result);
	}

}
