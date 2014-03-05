package implementations;

import interfaces.IDirectedGraph;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyListDirectedGraph implements IDirectedGraph {
	private final int[][] list;
	private final int nbNodes;
	private int nbArcs;

	public AdjacencyListDirectedGraph(int[][] matrix) {
		this.nbNodes = matrix.length;
		this.nbArcs = 0;
		this.list = toAdjacencyList(matrix);
	}

	public AdjacencyListDirectedGraph(IDirectedGraph graph) {
		this.nbNodes = graph.getNbNodes();
		this.list = toAdjacencyList(graph.toAdjacencyMatrix());
		this.nbArcs = graph.getNbArcs();
	}

	@Override
	public int getNbNodes() {
		return nbNodes;
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
	public int getNbArcs() {
		return nbArcs;
	}

	@Override
	public boolean isArc(int x, int y) {
		for (int i = 0; i < list[x].length; i++) {
			if (list[x][i] == y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeArc(int x, int y) {
		if (isArc(x, y)) {
			int[] array = new int[list[x].length];
			int compteur = 0;
			for (int i = 0; i < list[x].length; i++) {
				if (list[x][i] != y) {
					array[compteur] = list[x][i];
					compteur++;
				}
			}
			list[x] = array;

			nbArcs--;
		}
	}

	@Override
	public void addArc(int x, int y) {
		if (!isArc(x, y) && x != y) {
			int[] temp = new int[list[x].length + 1];
			for (int i = 0; i < list[x].length; i++) {
				temp[i] = list[x][i];
			}
			temp[list[x].length] = y;
			list[x] = temp;

			nbArcs++;
		}
	}

	@Override
	public int[] getSuccessors(int x) {
		return list[x].clone();
	}

	@Override
	public int[] getPredecessors(int x) {
		List<Integer> listPredecessors = new ArrayList<Integer>();
		for (int i = 0; i < getNbNodes(); i++) {
			if (i != x) { // si on est pas sur le meme index
				for (int j = 0; j < list[i].length; j++) {
					if (list[i][j] == x) {
						listPredecessors.add(i);
					}
				}
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
			// on inverse avec les predecesseurs
			int[] predecessors = getPredecessors(i);
			for (int j = 0; j < predecessors.length; j++) {
				graph[i][predecessors[j]] = 1;
			}
		}
		return new AdjacencyListDirectedGraph(graph);
	}

	private int[][] toAdjacencyList(int[][] matrix) {
		int[][] list = new int[getNbNodes()][];
		for (int i = 0; i < getNbNodes(); i++) {
			int nbSuccessors = 0;
			for (int j = 0; j < getNbNodes(); j++) {
				if (matrix[i][j] != 0) {
					nbSuccessors++;
					nbArcs++;
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
