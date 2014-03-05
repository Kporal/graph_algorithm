package implementations;

import interfaces.IWeightedGraph;

public class AdjacencyListDirectedWeightedGraph extends
		AdjacencyListDirectedGraph implements IWeightedGraph {

	private final int[][] listWeighted;

	public AdjacencyListDirectedWeightedGraph(int[][] matrixWeigthed) {
		super(toAjacencyMatrix(matrixWeigthed));
		this.listWeighted = new int[this.getNbNodes()][];
		// on init la list de poid
		toListWeighted(matrixWeigthed);
	}

	public AdjacencyListDirectedWeightedGraph(IWeightedGraph graph) {
		super(graph.toAdjacencyMatrix());
		this.listWeighted = new int[this.getNbNodes()][];
		// on init la list de poid
		toListWeighted(graph.toAdjacencyMatrix());
	}

	@Override
	public int getWeigth(int x, int y) {
		int[] neighbors = this.getSuccessors(x);
		for (int i = 0; i < neighbors.length; i++) {
			if (neighbors[i] == y) {
				return this.listWeighted[x][i];
			}
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public int getNextNode(int[] predecessors, boolean[] visited) {
		int min = Integer.MAX_VALUE;
		int node = -1;
		// je parcours mes groupes de sommets
		for (int pred : predecessors) {
			// pour chaque successeur
			for (int j : this.getSuccessors(pred)) {
				// si le sommet n'est pas visité et est de poids minimum
				if (!visited[j] && min > getWeigth(pred, j)) {
					min = getWeigth(pred, j);
					node = j;
				}
			}
		}
		return node;
	}

	/**
	 * Permet de transformer une matrice pondere en matrice d'ajacence. La
	 * matrice pondere indique s'il n'y a pas de relation entre deux sommets
	 * avec un nombre infini.
	 * 
	 * @param matrixWeigthed
	 *            matrice pondere
	 * @return int[][] matrice d'adjacence
	 */
	private static int[][] toAjacencyMatrix(int[][] matrixWeigthed) {
		int[][] matrix = new int[matrixWeigthed.length][matrixWeigthed[0].length];
		for (int i = 0; i < matrixWeigthed.length; i++) {
			for (int j = 0; j < matrixWeigthed[0].length; j++) {
				if (matrixWeigthed[i][j] == 0) {
					matrix[i][j] = 1;
				} else if (matrixWeigthed[i][j] >= Integer.MAX_VALUE) {
					matrix[i][j] = 0;
				} else {
					matrix[i][j] = 1;
				}
			}
		}

		return matrix;
	}

	/**
	 * Permet de remplir la liste pondere depuis la matrice pondere
	 * 
	 * @param matrix
	 *            matrice pondere
	 */
	private void toListWeighted(int[][] matrix) {
		for (int i = 0; i < this.getNbNodes(); i++) {
			int[] neighbor = this.getSuccessors(i);
			this.listWeighted[i] = new int[neighbor.length];
			for (int j = 0; j < neighbor.length; j++) {
				this.listWeighted[i][j] = matrix[i][neighbor[j]];
			}
		}
	}

}
