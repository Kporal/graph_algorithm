package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import implementations.AdjacencyListDirectedWeightedGraph;
import implementations.AdjacencyListUndirectedWeigthedGraph;
import implementations.AdjacencyMatrixDirectedGraph;
import implementations.AdjacencyMatrixDirectedWeightedGraph;
import implementations.AdjacencyMatrixUndirectedGraph;
import implementations.AdjacencyMatrixUndirectedWeightedGraph;
import implementations.GraphTools;
import implementations.IncidentMatrixDirectedGraph;
import implementations.IncidentMatrixUndirectedGraph;
import interfaces.IDirectedGraph;
import interfaces.IUndirectedGraph;
import interfaces.IWeightedGraph;

import org.junit.Test;

public class GraphToolsTest {
	private final int[][] matrix = { { 0, 1, 1, 0 }, { 1, 0, 0, 1 },
			{ 1, 0, 0, 0 }, { 0, 1, 0, 0 } };

	private final int[][] matrixDirected = { { 0, 1, 0, 0, 0, 1 },
			{ 0, 0, 1, 1, 1, 0 }, { 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 1, 0 } };

	private final int[][] composantesFortementConnexe = new int[][] {
			{ 0, 2, 1 }, { 4, 5 }, { 3 } };

	private final int[] startTab = { 1, 2, 3, 5, 7, 8 };
	private final int[] endTab = { 12, 11, 4, 6, 10, 9 };

	private final int maxVal = Integer.MAX_VALUE;
	private final int[][] matrixUndirectedWeighted = {
			{ maxVal, 4, 5, maxVal }, { 4, maxVal, 2, 3 },
			{ 5, 2, maxVal, maxVal }, { maxVal, 3, maxVal, maxVal } };

	private final int[][] matrixDirectedWeighted = { { maxVal, 4, 5, maxVal },
			{ maxVal, maxVal, maxVal, 3 }, { maxVal, 2, maxVal, maxVal },
			{ maxVal, maxVal, maxVal, maxVal } };

	private final int[] depthResult = { 0, 1, 3, 2 };
	private final int[] depthResult1 = { 1, 0, 2, 3 };
	private final int[] widthResult = { 0, 1, 2, 3 };
	private final int n = 4;
	private final int m = 3;

	@Test
	public void generateGraphDataTest() throws Exception {
		int[][] adjacencyMatrix = GraphTools.generateGraphData(this.n, this.m,
				false);
		IDirectedGraph directedGraph = new IncidentMatrixDirectedGraph(
				adjacencyMatrix);
		assertEquals(this.n, directedGraph.getNbNodes());
		assertEquals(this.m, directedGraph.getNbArcs());

		adjacencyMatrix = GraphTools.generateGraphData(this.n, this.m, true);
		IUndirectedGraph undirectedGraph = new AdjacencyMatrixUndirectedGraph(
				adjacencyMatrix);
		assertEquals(this.n, undirectedGraph.getNbNodes());
		assertEquals(this.m, undirectedGraph.getNbEdges());
	}

	@Test
	public void startEndTabTest() throws Exception {
		// lancement d'une exploration de graphe
		GraphTools.exploreDepthGraph(new AdjacencyMatrixDirectedGraph(
				this.matrixDirected));
		assertArrayEquals(this.startTab, GraphTools.getStartTab());
		assertArrayEquals(this.endTab, GraphTools.getEndTab());
	}

	@Test
	public void exploreDepthGraphTest() throws Exception {
		assertArrayEquals(this.depthResult,
				GraphTools.exploreDepthGraph(new IncidentMatrixUndirectedGraph(
						this.matrix)));
		assertArrayEquals(this.depthResult,
				GraphTools.exploreDepthGraph(new IncidentMatrixDirectedGraph(
						this.matrix)));
		assertArrayEquals(this.depthResult1, GraphTools.exploreDepthGraph(
				new IncidentMatrixUndirectedGraph(this.matrix), 1));
		assertArrayEquals(this.depthResult1, GraphTools.exploreDepthGraph(
				new IncidentMatrixDirectedGraph(this.matrix), 1));
	}

	@Test
	public void exploreWidthGraphTest() throws Exception {
		assertArrayEquals(this.widthResult,
				GraphTools
						.exploreBreadthGraph(new IncidentMatrixUndirectedGraph(
								this.matrix)));
		assertArrayEquals(this.widthResult,
				GraphTools.exploreBreadthGraph(new IncidentMatrixDirectedGraph(
						this.matrix)));
	}

	@Test
	public void composantesFortementConnexeTest() throws Exception {
		assertArrayEquals(
				composantesFortementConnexe,
				GraphTools
						.getComposanteFortementConnexe(new AdjacencyMatrixDirectedGraph(
								matrixDirected)));
	}

	@Test
	public void primUndirectedTest() throws Exception {
		int[] predecessors = { 0, 1, 2, 3 };
		// avec liste d'adjacence
		IWeightedGraph weightedGraph = new AdjacencyListUndirectedWeigthedGraph(
				this.matrixUndirectedWeighted);
		assertArrayEquals(predecessors, GraphTools.prim(weightedGraph, 0));
		// avec matrice d'adjacence
		weightedGraph = new AdjacencyMatrixUndirectedWeightedGraph(
				this.matrixUndirectedWeighted);
		assertArrayEquals(predecessors, GraphTools.prim(weightedGraph, 0));
	}

	@Test
	public void primDirectedTest() throws Exception {
		int[] predecessors = { 0, 1, 3, 2 };
		// avec liste d'adjacence
		IWeightedGraph weightedGraph = new AdjacencyListDirectedWeightedGraph(
				this.matrixDirectedWeighted);
		assertArrayEquals(predecessors, GraphTools.prim(weightedGraph, 0));
		// avec matrice d'adjacence
		weightedGraph = new AdjacencyMatrixDirectedWeightedGraph(
				this.matrixDirectedWeighted);
		assertArrayEquals(predecessors, GraphTools.prim(weightedGraph, 0));
	}

	@Test
	public void bellmanUndirectedTest() throws Exception {
		int[] predecessors = { 0, 4, 5, 7 };
		// avec liste d'adjacence
		IWeightedGraph weightedGraph = new AdjacencyListUndirectedWeigthedGraph(
				this.matrixUndirectedWeighted);
		assertArrayEquals(predecessors, GraphTools.bellman(weightedGraph, 0));
		// avec matrice d'adjacence
		weightedGraph = new AdjacencyMatrixUndirectedWeightedGraph(
				this.matrixUndirectedWeighted);
		assertArrayEquals(predecessors, GraphTools.bellman(weightedGraph, 0));
	}

	@Test
	public void bellmanDirectedTest() throws Exception {
		int[] predecessors = { 0, 4, 5, 7 };
		// avec liste d'adjacence
		IWeightedGraph weightedGraph = new AdjacencyListDirectedWeightedGraph(
				this.matrixDirectedWeighted);
		assertArrayEquals(predecessors, GraphTools.bellman(weightedGraph, 0));
		// avec matrice d'adjacence
		weightedGraph = new AdjacencyMatrixDirectedWeightedGraph(
				this.matrixDirectedWeighted);
		assertArrayEquals(predecessors, GraphTools.bellman(weightedGraph, 0));
	}

	@Test
	public void dijkstraDirectedTest() throws Exception {
		int[] couts = { 0, 1, 4, 4, 5 };
		int[][] matrixDirected = { { maxVal, 1, maxVal, 5, maxVal },
				{ maxVal, maxVal, 3, maxVal, maxVal },
				{ maxVal, maxVal, maxVal, 0, 2 },
				{ maxVal, 1, maxVal, maxVal, 1 },
				{ maxVal, maxVal, maxVal, maxVal, maxVal } };
		// avec liste d'adjacence
		IWeightedGraph weightedGraph = new AdjacencyListDirectedWeightedGraph(
				matrixDirected);
		assertArrayEquals(couts, GraphTools.dijkstra(weightedGraph));
		// avec matrice d'adjacence
		weightedGraph = new AdjacencyMatrixDirectedWeightedGraph(matrixDirected);
		assertArrayEquals(couts, GraphTools.dijkstra(weightedGraph));
	}
}
