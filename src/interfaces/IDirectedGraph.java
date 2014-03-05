package interfaces;

public interface IDirectedGraph extends IGraph {

	/**
	 * Returns the number of arcs in the graph
	 * 
	 * @return int nbArcs
	 */
	public int getNbArcs();

	/**
	 * Returns true if arc (from, to) figures in the graph
	 * 
	 * @param x
	 *            from
	 * @param y
	 *            to
	 * @return boolean
	 */
	public boolean isArc(int x, int y);

	/**
	 * Removes arc (x, y) if exists
	 * 
	 * @param x
	 *            from
	 * @param y
	 *            to
	 */
	public void removeArc(int x, int y);

	/**
	 * Adds arc (x, y) if not already present, requires x != y
	 * 
	 * @param x
	 *            from
	 * @param y
	 *            to
	 */
	public void addArc(int x, int y);

	/**
	 * Returns a new int array representing successors of node x
	 * 
	 * @param x
	 *            node
	 * @return int[] successors of x
	 */
	public int[] getSuccessors(int x);

	/**
	 * Returns a new int array representing predecessors of node x
	 * 
	 * @param x
	 *            node
	 * @return int[] predecessors of x
	 */
	public int[] getPredecessors(int x);

	/**
	 * Computes the inverse graph
	 * 
	 * @return IDirectedGraph new graph
	 */
	public IDirectedGraph computeInverse();

}
