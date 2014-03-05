package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import implementations.AdjacencyListDirectedGraph;
import implementations.AdjacencyMatrixDirectedGraph;
import implementations.IncidentMatrixDirectedGraph;
import interfaces.IDirectedGraph;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class IDirectedGraphTest {
	private final Class<IDirectedGraph> implClass;
	private final IDirectedGraph graph;

	private final int[][] matrix = { { 0, 1, 1, 0 }, { 0, 0, 1, 0 },
			{ 0, 0, 0, 0 }, { 0, 0, 1, 0 } };
	private final int[][] matrixInverse = { { 0, 0, 0, 0 }, { 1, 0, 0, 0 },
			{ 1, 1, 0, 1 }, { 0, 0, 0, 0 } };
	private final int nbNodes = 4;
	private final int nbArcs = 4;
	private final int[] successorsOf0 = { 1, 2 };
	private final int[] predecessorsOf0 = {};

	public IDirectedGraphTest(Class<IDirectedGraph> cl) throws Exception {
		this.implClass = cl;
		this.graph = this.implClass.getDeclaredConstructor(
				new Class[] { int[][].class })
				.newInstance((Object) this.matrix);
	}

	@Test
	public void testGetNbArcs() throws Exception {
		assertEquals(this.nbArcs, this.graph.getNbArcs());
	}

	@Test
	public void testIsArc() throws Exception {
		assertTrue(this.graph.isArc(0, 1));
		assertFalse(this.graph.isArc(0, 0));
	}

	@Test
	public void testRemoveArc() throws Exception {
		this.graph.removeArc(0, 1);
		assertFalse(this.graph.isArc(0, 1));
		assertEquals(this.nbArcs - 1, this.graph.getNbArcs());

		this.graph.removeArc(0, 1);
		assertEquals(this.nbArcs - 1, this.graph.getNbArcs());
	}

	@Test
	public void testAddArc() throws Exception {
		this.graph.addArc(3, 0);
		assertTrue(this.graph.isArc(3, 0));
		assertEquals(this.nbArcs + 1, this.graph.getNbArcs());

		this.graph.addArc(3, 0);
		assertEquals(this.nbArcs + 1, this.graph.getNbArcs());

		this.graph.addArc(0, 0);
		assertEquals(this.nbArcs + 1, this.graph.getNbArcs());
		assertFalse(this.graph.isArc(0, 0));
	}

	@Test
	public void testGetSuccessors() throws Exception {
		assertArrayEquals(this.successorsOf0, this.graph.getSuccessors(0));
	}

	@Test
	public void testGetPredecessors() throws Exception {
		assertArrayEquals(this.predecessorsOf0, this.graph.getPredecessors(0));
	}

	@Test
	public void testComputeInverse() throws Exception {
		assertArrayEquals(this.matrixInverse, this.graph.computeInverse()
				.toAdjacencyMatrix());
	}

	@Test
	public void testGetNbNodes() throws Exception {
		assertEquals(this.nbNodes, this.graph.getNbNodes());
	}

	@Test
	public void testToAdjacencyMatrix() throws Exception {
		assertArrayEquals(this.matrix, this.graph.toAdjacencyMatrix());
	}

	@Parameterized.Parameters
	public static Collection<Object[]> implList() {
		return Arrays.asList(new Object[] { AdjacencyListDirectedGraph.class },
				new Object[] { AdjacencyMatrixDirectedGraph.class },
				new Object[] { IncidentMatrixDirectedGraph.class });
	}
}
