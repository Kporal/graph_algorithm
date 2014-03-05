package implementations;

import interfaces.IUndirectedGraph;

public class AdjacencyMatrixUndirectedGraph implements IUndirectedGraph {
	private int[][] matrix;
	private int nbNodes;
	private int nbEdges;

	public AdjacencyMatrixUndirectedGraph(int[][] matrix) {
		this.matrix = matrix;
		this.nbNodes = matrix.length;
		this.nbEdges = nbEdge();
	}

	public AdjacencyMatrixUndirectedGraph(IUndirectedGraph graph) {
		this.matrix = graph.toAdjacencyMatrix();
		this.nbNodes = graph.getNbNodes();
		this.nbEdges = graph.getNbEdges();
	}

	@Override
	public int getNbNodes() {
		return nbNodes;
	}

	@Override
	public int[][] toAdjacencyMatrix() {
		return matrix;
	}

	@Override
	public int getNbEdges() {
		return nbEdges;
	}

	@Override
	public boolean isEdge(int x, int y) {
		if (x < getNbNodes() && y < getNbNodes())
			return matrix[x][y] == 1 && matrix[y][x] == 1;
		return false;
	}

	@Override
	public void removeEdge(int x, int y) {
		if (isEdge(x, y)) {
			matrix[x][y] = 0;
			matrix[y][x] = 0;
			nbEdges--;
		}
	}

	@Override
	public void addEdge(int x, int y) {
		if (!isEdge(x, y) && x != y) {
			matrix[x][y] = 1;
			matrix[y][x] = 1;
			nbEdges++;
		}
	}

	@Override
	public int[] getNeighbors(int x) {
		int cpt = 0;
		int[] temp = new int[getNbNodes()];
		for (int i = 0; i < getNbNodes(); i++) {
			if (isEdge(x, i)) {
				temp[cpt] = i;
				cpt++;
			}
		}
		int[] succ = new int[cpt];
		for (int i = 0; i < cpt; i++) {
			succ[i] = temp[i];
		}
		return succ;
	}

	private int nbEdge() {
		int nbEdge = 0;
		int cpt = 0;
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = cpt; j < getNbNodes(); j++) {
				if (isEdge(i, j)) {
					nbEdge++;
				}
			}
			cpt++;
		}
		return nbEdge;
	}

}
