package implementations;

import interfaces.IDirectedGraph;

public class AdjacencyMatrixDirectedGraph implements IDirectedGraph {
	private int[][] matrix;
	private int nbNodes;
	private int nbArcs;

	public AdjacencyMatrixDirectedGraph(int[][] matrix) {
		this.matrix = matrix;
		this.nbNodes = matrix.length;
		this.nbArcs = nbArcs();
	}

	public AdjacencyMatrixDirectedGraph(IDirectedGraph graph) {
		this.matrix = graph.toAdjacencyMatrix();
		this.nbNodes = graph.getNbNodes();
		this.nbArcs = graph.getNbArcs();
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
	public int getNbArcs() {
		return nbArcs;
	}

	@Override
	public boolean isArc(int x, int y) {
		if (x < getNbNodes() && y < getNbNodes())
			return matrix[x][y] == 1;
		return false;
	}

	@Override
	public void removeArc(int x, int y) {
		if (isArc(x, y)) {
			matrix[x][y] = 0;
			nbArcs--;
		}
	}

	@Override
	public void addArc(int x, int y) {
		if (!isArc(x, y) && x != y) {
			matrix[x][y] = 1;
			nbArcs++;
		}
	}

	@Override
	public int[] getSuccessors(int x) {
		int cpt = 0;
		int[] temp = new int[getNbNodes()];
		for (int i = 0; i < getNbNodes(); i++) {
			if (isArc(x, i)) {
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

	@Override
	public int[] getPredecessors(int x) {
		int cpt = 0;
		int[] temp = new int[getNbNodes()];
		for (int i = 0; i < getNbNodes(); i++) {
			if (isArc(i, x)) {
				temp[cpt] = i;
				cpt++;
			}
		}
		int[] prec = new int[cpt];
		for (int i = 0; i < cpt; i++) {
			prec[i] = temp[i];
		}
		return prec;
	}

	@Override
	public IDirectedGraph computeInverse() {
		int[][] graph = new int[getNbNodes()][getNbNodes()];
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = 0; j < getNbNodes(); j++) {
				graph[j][i] = matrix[i][j];
			}
		}
		return new AdjacencyMatrixDirectedGraph(graph);
	}

	private int nbArcs() {
		int nbArcs = 0;
		for (int i = 0; i < getNbNodes(); i++) {
			for (int j = 0; j < getNbNodes(); j++) {
				if (isArc(i, j)) {
					nbArcs++;
				}
			}
		}
		return nbArcs;
	}

}
