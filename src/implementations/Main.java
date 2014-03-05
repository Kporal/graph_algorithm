package implementations;

public class Main {

	public static void main(String[] args) {

		int[][] matrix = { { 0, 1, 1, 0, 0 }, { 1, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 }, { 0, 0, 1, 1, 0 } };

		AdjacencyMatrixUndirectedGraph graph = new AdjacencyMatrixUndirectedGraph(
				matrix);

		GraphTools.toPrint(graph.toAdjacencyMatrix());
	}
}
