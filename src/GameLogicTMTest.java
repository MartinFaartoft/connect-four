import static org.junit.Assert.*;

import org.junit.Test;


public class GameLogicTMTest {

	@Test
	public void test() {
		GameLogicTM gl = new GameLogicTM();
		gl.initializeGame(3, 3, 1);
		assertArrayEquals(gl.precomputedActions[0], new int[]{0,1,2});
		assertArrayEquals(gl.precomputedActions[1], new int[]{1,2,0});
		assertArrayEquals(gl.precomputedActions[2], new int[]{2,0,1});
	}

}
