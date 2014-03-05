package implementations;

import interfaces.IUndirectedGraph;

public class AdjacencyListUndirectedGraph implements IUndirectedGraph {
	protected final int[][] list;
	private final int nbNodes;
	private int nbEdges;

	public AdjacencyListUndirectedGraph(int[][] matrix) {
		this.nbNodes = matrix.length;
		this.nbEdges = 0;
		this.list = toAdjacencyList(matrix);
	}

	public AdjacencyListUndirectedGraph(IUndirectedGraph graph) {
		this.nbNodes = graph.getNbNodes();
		this.list = toAdjacencyList(graph.toAdjacencyMatrix());
		this.nbEdges = graph.getNbEdges();
	}

	@Override
	public int getNbNodes() {
		return nbNodes;
	}

	@Override
	public int getNbEdges() {
		return nbEdges;
	}

	@Override
	public int[][] toAdjacencyMatrix() {
		int[][] matrix = new int[getNbNodes()][getNbNodes()];

		for (int i = 0; i < getNbNodes(); i++) {
			// on place les edges
			for (int j = 0; j < list[i].length; j++) {
				matrix[i][list[i][j]] = 1;
			}
		}

		return matrix;
	}

	@Override
	public boolean isEdge(int x, int y) {
		for (int i = 0; i < list[x].length; i++) {
			if (list[x][i] == y && list[y][i] == x) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeEdge(int x, int y) {
		if (isEdge(x, y) && isEdge(y, x)) {
			int[] array = new int[list[x].length];
			int compteur = 0;
			for (int i = 0; i < list[x].length; i++) {
				if (list[x][i] != y) {
					array[compteur] = list[x][i];
					compteur++;
				}
			}
			list[x] = array;

			array = new int[list[y].length];
			compteur = 0;
			for (int i = 0; i < list[y].length; i++) {
				if (list[y][i] != y) {
					array[compteur] = list[y][i];
					compteur++;
				}
			}
			list[y] = array;

			nbEdges--;
		}
	}

	@Override
	public void addEdge(int x, int y) {
		if (!isEdge(x, y) && !isEdge(y, x) && x != y) {
			int[] temp = new int[list[x].length + 1];
			for (int i = 0; i < list[x].length; i++) {
				temp[i] = list[x][i];
			}
			temp[list[x].length] = y;
			list[x] = temp;

			temp = new int[list[y].length + 1];
			for (int i = 0; i < list[y].length; i++) {
				temp[i] = list[y][i];
			}
			temp[list[y].length] = x;
			list[y] = temp;

			nbEdges++;
		}
	}

	@Override
	public int[] getNeighbors(int x) {
		return list[x].clone();
	}

	private int[][] toAdjacencyList(int[][] matrix) {
		int[][] list = new int[getNbNodes()][];
		for (int i = 0; i < getNbNodes(); i++) {
			int nbSuccessors = 0;
			for (int j = 0; j < getNbNodes(); j++) {
				if (matrix[i][j] != 0 && matrix[j][i] != 0) {
					nbSuccessors++;
					// on prend uniquement que le haut de la matrice
					if (i >= j) {
						nbEdges++;
					}
				}
			}

			int[] succe = new int[nbSuccessors];
			int index = 0;
			for (int x = 0; x < getNbNodes(); x++) {
				if (matrix[i][x] != 0) {
					succe[index] = x;
					index++;
				}
			}
			list[i] = succe;
		}

		return list;
	}

}
