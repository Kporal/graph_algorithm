package implementations;

import java.util.ArrayList;
import java.util.List;

import interfaces.IUndirectedGraph;

public class IncidentMatrixUndirectedGraph implements IUndirectedGraph {
	private int[][] matrix;
	private int nbNodes;
	private int nbEdges;

	public IncidentMatrixUndirectedGraph(int[][] matrix) {
		this.nbNodes = matrix.length;
		this.nbEdges = 0;
		this.matrix = toIncidentMatrix(matrix);
	}

	public IncidentMatrixUndirectedGraph(IUndirectedGraph graph) {
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
		int[][] adjacencyMatrix = new int[getNbNodes()][getNbNodes()];
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = 0; j < getNbNodes(); j++) {
				if (isEdge(i, j)) {
					adjacencyMatrix[i][j] = 1;
				}
			}
		}
		return adjacencyMatrix;
	}

	@Override
	public int getNbEdges() {
		return nbEdges;
	}

	@Override
	public boolean isEdge(int x, int y) {
		for (int i = 0; i < getNbEdges(); i++) {
			if (matrix[x][i] != 0 && matrix[y][i] != 0 && x != y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeEdge(int x, int y) {
		if (isEdge(x, y) && isEdge(y, x)) {
			int[][] incidentMatrix = new int[getNbNodes()][getNbEdges()];
			for (int i = 0; i < getNbEdges(); i++) {
				if (matrix[x][i] == 0 || matrix[y][i] == 0) {
					for (int j = 0; j < getNbNodes(); j++) {
						incidentMatrix[j][i] = matrix[j][i];
					}
				}
			}

			matrix = incidentMatrix;
			nbEdges--;
		}
	}

	@Override
	public void addEdge(int x, int y) {
		if (!isEdge(x, y) && !isEdge(y, x) && x != y) {
			int[][] incidentMatrix = new int[getNbNodes()][getNbEdges() + 1];
			for (int i = 0; i < getNbNodes(); i++) {
				for (int j = 0; j < getNbEdges(); j++) {
					incidentMatrix[i][j] = matrix[i][j];
				}
			}

			incidentMatrix[x][getNbEdges()] = 1;
			incidentMatrix[y][getNbEdges()] = 1;

			matrix = incidentMatrix;
			nbEdges++;
		}
	}

	@Override
	public int[] getNeighbors(int x) {
		List<Integer> listNeighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNbEdges(); i++) {
			if (matrix[x][i] != 0) {
				for (int j = 0; j < getNbNodes(); j++) {
					if (j != x && matrix[j][i] != 0) {
						listNeighbors.add(j);
					}
				}
			}
		}

		int[] neighbors = new int[listNeighbors.size()];
		for (int i = 0; i < listNeighbors.size(); i++) {
			neighbors[i] = listNeighbors.get(i);
		}

		return neighbors;
	}

	private int[][] toIncidentMatrix(int[][] adjacencyMatrix) {
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = 0; j < getNbNodes(); j++) {
				if (adjacencyMatrix[i][j] == 1 && i >= j) {
					nbEdges++;
				}
			}
		}

		int[][] incidentMatrix = new int[getNbNodes()][getNbEdges()];
		int nbEdge = 0;
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = 0; j < getNbNodes(); j++) {
				if (adjacencyMatrix[i][j] != 0 && i >= j) {
					incidentMatrix[i][nbEdge] = 1;
					incidentMatrix[j][nbEdge] = 1;
					nbEdge++;
				}
			}
		}

		return incidentMatrix;
	}

}
