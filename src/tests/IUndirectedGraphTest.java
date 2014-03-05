package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import implementations.AdjacencyListUndirectedGraph;
import implementations.AdjacencyMatrixUndirectedGraph;
import implementations.IncidentMatrixUndirectedGraph;
import interfaces.IUndirectedGraph;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
public class IUndirectedGraphTest {
	private final Class<IUndirectedGraph> implClass;
	private final IUndirectedGraph graph;
	private final int[][] matrix = { { 0, 1, 1 }, { 1, 0, 0 }, { 1, 0, 0 } };
	private final int nbNodes = 3;
	private final int nbEdges = 2;
	private final int[] neighborsOf0 = { 1, 2 };
	private final int[] neighborsOf1 = { 0 };

	public IUndirectedGraphTest(Class<IUndirectedGraph> cl) throws Exception {
		this.implClass = cl;
		this.graph = this.implClass.getDeclaredConstructor(
				new Class[] { int[][].class }).newInstance((Object) matrix);
	}

	@Test
	public void testGetNbEdges() throws Exception {
		assertEquals(nbEdges, this.graph.getNbEdges());
	}

	@Test
	public void testIsEdge() throws Exception {
		assertTrue(this.graph.isEdge(0, 1));
		assertFalse(this.graph.isEdge(0, 0));
	}

	@Test
	public void testRemoveEdge() throws Exception {
		this.graph.removeEdge(0, 1);
		assertFalse(this.graph.isEdge(0, 1));
		assertEquals(this.nbEdges - 1, this.graph.getNbEdges());

		this.graph.removeEdge(0, 1);
		assertEquals(this.nbEdges - 1, this.graph.getNbEdges());
	}

	@Test
	public void testAddEdge() throws Exception {
		this.graph.addEdge(1, 2);
		assertTrue(this.graph.isEdge(1, 2));
		assertEquals(this.nbEdges + 1, this.graph.getNbEdges());

		this.graph.addEdge(1, 2);
		assertEquals(this.nbEdges + 1, this.graph.getNbEdges());

		this.graph.addEdge(0, 0);
		assertEquals(this.nbEdges + 1, this.graph.getNbEdges());
		assertFalse(this.graph.isEdge(0, 0));
	}

	@Test
	public void testGetNeighbors() throws Exception {
		assertArrayEquals(this.neighborsOf0, this.graph.getNeighbors(0));
		assertArrayEquals(this.neighborsOf1, this.graph.getNeighbors(1));
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
		return Arrays.asList(
				new Object[] { AdjacencyListUndirectedGraph.class },
				new Object[] { AdjacencyMatrixUndirectedGraph.class },
				new Object[] { IncidentMatrixUndirectedGraph.class });
	}
}
