package implementations;

import java.util.ArrayList;
import java.util.List;

import interfaces.IDirectedGraph;

public class IncidentMatrixDirectedGraph implements IDirectedGraph {
	private int[][] matrix;
	private int nbNodes;
	private int nbArcs;

	public IncidentMatrixDirectedGraph(int[][] matrix) {
		this.nbNodes = matrix.length;
		this.nbArcs = 0;
		this.matrix = toIncidentMatrix(matrix);
	}

	public IncidentMatrixDirectedGraph(IDirectedGraph graph) {
		this.matrix = toIncidentMatrix(graph.toAdjacencyMatrix());
		this.nbNodes = graph.getNbNodes();
		this.nbArcs = graph.getNbArcs();
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
				if (isArc(i, j)) {
					adjacencyMatrix[i][j] = 1;
				}
			}
		}

		return adjacencyMatrix;
	}

	@Override
	public int getNbArcs() {
		return nbArcs;
	}

	@Override
	public boolean isArc(int x, int y) {
		for (int i = 0; i < getNbArcs(); i++) {
			if (matrix[x][i] == 1 && matrix[y][i] == -1 && x != y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeArc(int x, int y) {
		if (isArc(x, y)) {
			int[][] incidentMatrix = new int[getNbNodes()][getNbArcs()];
			for (int i = 0; i < getNbArcs(); i++) {
				if (matrix[x][i] == 0 || matrix[y][i] == 0) {
					for (int j = 0; j < getNbNodes(); j++) {
						incidentMatrix[j][i] = matrix[j][i];
					}
				}
			}

			matrix = incidentMatrix;
			nbArcs--;
		}
	}

	@Override
	public void addArc(int x, int y) {
		if (!isArc(x, y) && !isArc(y, x) && x != y) {
			int[][] incidentMatrix = new int[getNbNodes()][getNbArcs() + 1];
			for (int i = 0; i < getNbNodes(); i++) {
				for (int j = 0; j < getNbArcs(); j++) {
					incidentMatrix[i][j] = matrix[i][j];
				}
			}

			incidentMatrix[x][getNbArcs()] = 1;
			incidentMatrix[y][getNbArcs()] = -1;

			matrix = incidentMatrix;
			nbArcs++;
		}
	}

	@Override
	public int[] getSuccessors(int x) {
		List<Integer> listSuccessors = new ArrayList<Integer>();
		for (int i = 0; i < getNbArcs(); i++) {
			if (matrix[x][i] == 1) { // s'il a un successeur
				int j = 0;
				while (matrix[j][i] != -1) { // tant qu'on a pas trouvé le
												// successeur
					j++;
				}
				listSuccessors.add(j);
			}
		}

		int[] successors = new int[listSuccessors.size()];
		for (int i = 0; i < listSuccessors.size(); i++) {
			successors[i] = listSuccessors.get(i);
		}

		return successors;
	}

	@Override
	public int[] getPredecessors(int x) {
		List<Integer> listPredecessors = new ArrayList<Integer>();
		for (int i = 0; i < getNbArcs(); i++) {
			if (matrix[x][i] == -1) { // s'il a un predecesseur
				int j = 0;
				while (matrix[j][i] != 1) { // tant qu'on a pas trouvé le
											// predecesseur
					j++;
				}
				listPredecessors.add(j);
			}
		}

		int[] predecessors = new int[listPredecessors.size()];
		for (int i = 0; i < listPredecessors.size(); i++) {
			predecessors[i] = listPredecessors.get(i);
		}

		return predecessors;
	}

	@Override
	public IDirectedGraph computeInverse() {
		int[][] graph = new int[getNbNodes()][getNbNodes()];
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = 0; j < getNbNodes(); j++) {
				if (isArc(i, j)) {
					graph[j][i] = 1;
				}
			}
		}
		return new IncidentMatrixDirectedGraph(graph);
	}

	private int[][] toIncidentMatrix(int[][] adjacencyMatrix) {
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = 0; j < getNbNodes(); j++) {
				if (adjacencyMatrix[i][j] == 1) {
					nbArcs++;
				}
			}
		}

		int[][] incidentMatrix = new int[getNbNodes()][getNbArcs()];
		int nbArc = 0;
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = 0; j < getNbNodes(); j++) {
				if (adjacencyMatrix[i][j] != 0) {
					incidentMatrix[i][nbArc] = 1;
					incidentMatrix[j][nbArc] = -1;
					nbArc++;
				}
			}
		}

		return incidentMatrix;
	}

}
