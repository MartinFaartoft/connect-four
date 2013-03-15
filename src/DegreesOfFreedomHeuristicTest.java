import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class DegreesOfFreedomHeuristicTest {

	State s;
	DegreesOfFreedomHeuristic dof = new DegreesOfFreedomHeuristic();
	
	@Before
	public void Setup() {
		s = new State(4,4);
	}
	
	@Test
	public void testLeftScenario() {
		s.insertCoin(0, 1);
		assertEquals(9, dof.Evaluate(s, 1));
	}
	
	@Test
	public void testRightScenario() {
		s.insertCoin(3, 1);
		assertEquals(9, dof.Evaluate(s, 1));
	}
	
	@Test
	public void testBothScenario() {
		s.insertCoin(0, 1);
		s.insertCoin(0, 2);
		assertEquals(3, dof.Evaluate(s, 1));
	}

}
