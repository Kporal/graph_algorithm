package interfaces;

public interface IWeightedGraph extends IGraph {

	/**
	 * Returns the weight of the edge in the graph
	 * 
	 * @param x
	 *            from
	 * @param y
	 *            to
	 * @return int weight
	 */
	public int getWeigth(int x, int y);

	/**
	 * Returns the next node of minimum weight
	 * 
	 * @param predecessors
	 * @param visited
	 * @return int the next node
	 */
	public int getNextNode(int[] predecessors, boolean[] visited);

}
