package tests;

import static org.junit.Assert.assertEquals;
import implementations.AdjacencyListDirectedWeightedGraph;
import implementations.AdjacencyMatrixDirectedWeightedGraph;
import interfaces.IWeightedGraph;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class IDirectedWeightedGraphTest {
	private final Class<IWeightedGraph> implClass;
	private final IWeightedGraph graph;
	private final int[][] matrix = {
			{ Integer.MAX_VALUE, 4, 5, Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3 },
			{ Integer.MAX_VALUE, 2, Integer.MAX_VALUE, Integer.MAX_VALUE },
			{ Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
					Integer.MAX_VALUE } };
	private final int from = 0;
	private final int to = 1;
	private final int weight = 4;
	private final int nextNode = 1;

	public IDirectedWeightedGraphTest(Class<IWeightedGraph> cl)
			throws Exception {
		this.implClass = cl;
		this.graph = this.implClass.getDeclaredConstructor(
				new Class[] { int[][].class })
				.newInstance((Object) this.matrix);
	}

	@Test
	public void testGetWeigth() throws Exception {
		assertEquals(this.weight, this.graph.getWeigth(this.from, this.to));
	}

	@Test
	public void testGetNextNode() throws Exception {
		int[] predecessors = new int[this.graph.getNbNodes()];
		predecessors[0] = this.from;
		boolean[] visited = new boolean[this.graph.getNbNodes()];
		visited[this.from] = true;
		assertEquals(this.nextNode,
				this.graph.getNextNode(predecessors, visited));
	}

	@Parameterized.Parameters
	public static Collection<Object[]> implList() {
		Collection<Object[]> collection = new HashSet<Object[]>();
		collection
				.add(new Object[] { AdjacencyListDirectedWeightedGraph.class });
		collection
				.add(new Object[] { AdjacencyMatrixDirectedWeightedGraph.class });
		return collection;
	}
}
