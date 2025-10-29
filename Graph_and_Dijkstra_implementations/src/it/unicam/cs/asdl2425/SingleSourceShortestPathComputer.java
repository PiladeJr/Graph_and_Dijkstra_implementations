package it.unicam.cs.asdl2425;

import java.util.List;

/**
* This interface defines objects that are calculators for single-source shortest
* paths on a given directed and weighted graph. The graph to work on must be
* passed when the calculator object is constructed. The calculator implements
* an algorithm for computing single-source shortest paths using a priority queue
* whose functionalities are specified in the {@code MinPriorityQueue<V>} interface.
* The queue can be used for inserting and extracting nodes during the execution
* of the algorithm, as well as for updating their priority via the
* decreasePriority method.
*
* @author Luca Tesei
*
* @param <L>
*                the type of the labels of the graph nodes
*/
public interface SingleSourceShortestPathComputer<L> {

/**
* Initializes the necessary information associated with the nodes of the graph
* linked to this calculator and executes an algorithm to compute the shortest
* paths starting from a given source.
*
* @param sourceNode
*                       the source node from which to calculate the shortest
*                       paths to all other nodes in the graph
* @throws NullPointerException
*                                      if the passed node is null
*
* @throws IllegalArgumentException
*                                      if the passed node does not exist in the
*                                      graph associated with this calculator
* @throws IllegalStateException
*                                      if the computation cannot be performed
*                                      due to the weight values of the graph,
*                                      for example, if the graph contains
*                                      negative weight cycles.
*/
    public void computeShortestPathsFrom(GraphNode<L> sourceNode);

   /**
     * Determines whether the procedure for calculating the shortest paths
     * from a specified source node has been invoked at least once.
     *
     * @return true, if the shortest paths from a certain source node have been
     *         calculated at least once by this calculator
     */
    public boolean isComputed();

    /**
     * Returns the source node specified in the last call made to
     * {@code computeShortestPathsFrom(GraphNode<V>)}.
     *
     * @return the source node specified in the last shortest path computation
     *         performed
     *
     * @throws IllegalStateException
     *                                   if the shortest path computation has
     *                                   not been performed at least once from
     *                                   a source node
     */
    public GraphNode<L> getLastSource();

    /**
     * Returns the graph on which this calculator operates.
     *
     * @return the graph on which this calculator operates
     */
    public Graph<L> getGraph();

    /**
     * Returns a list of edges from the source node of the last shortest path
     * computation to the given node. This list corresponds to a shortest path
     * between the source node and the given target node.
     *
     * @param targetNode
     *                       the node to which the shortest path from the source
     *                       is to be returned
     * @return the list of edges corresponding to the shortest path; the list is
     *         empty if the given node is the source node. {@code null} is
     *         returned if the given node is not reachable from the source
     *
     * @throws NullPointerException
     *                                      if the given node is null
     *
     * @throws IllegalArgumentException
     *                                      if the given node does not exist
     *
     * @throws IllegalStateException
     *                                      if the shortest path computation has
     *                                      not been performed at least once
     *                                      from a source node
     *
     */
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode);

    /**
     * Generates a descriptive string of a path showing the nodes traversed and
     * the weights of the edges. In the case of an empty path, it generates only
     * the string {@code "[ ]"}.
     *
     * @param path
     *                 a shortest path
     * @return a descriptive string of the shortest path
     * @throws NullPointerException
     *                                  if the given path is null
     */
    default public String printPath(List<GraphEdge<L>> path) {
        if (path == null)
            throw new NullPointerException(
                    "Request to print a null path");
        if (path.isEmpty())
            return "[ ]";
        // Build the string
        StringBuffer s = new StringBuffer();
        s.append("[ " + path.get(0).getNode1().toString());
        for (int i = 0; i < path.size(); i++)
            s.append(" -- " + path.get(i).getWeight() + " --> "
                    + path.get(i).getNode2().toString());
        s.append(" ]");
        return s.toString();
    }

}
