package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = { GraphToolsTest.class, IDirectedGraphTest.class,
		IUndirectedGraphTest.class, IDirectedWeightedGraphTest.class,
		IUndirectedWeightedGraphTest.class })
public class SuiteTests {
}