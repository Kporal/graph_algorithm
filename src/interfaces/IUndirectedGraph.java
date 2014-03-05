package interfaces;

public interface IUndirectedGraph extends IGraph {

	/**
	 * Returns the number of edges in the graph
	 * 
	 * @return int nbEdge
	 */
	public int getNbEdges();

	/**
	 * Returns true if there is an edge between x and y
	 * 
	 * @param x
	 *            from
	 * @param y
	 *            to
	 * @return boolean
	 */
	public boolean isEdge(int x, int y);

	/**
	 * Removes edge (x, y) if exists
	 * 
	 * @param x
	 *            from
	 * @param y
	 *            to
	 */
	public void removeEdge(int x, int y);

	/**
	 * Adds edge (x, y) if not already present, requires x != y
	 * 
	 * @param x
	 *            from
	 * @param y
	 *            to
	 */
	public void addEdge(int x, int y);

	/**
	 * Returns a new int array representing neighbors of node x
	 * 
	 * @param x
	 *            node
	 * @return int[] neighbors of x
	 */
	public int[] getNeighbors(int x);

}
