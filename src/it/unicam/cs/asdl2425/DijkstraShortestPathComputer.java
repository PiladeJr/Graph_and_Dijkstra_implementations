package it.unicam.cs.asdl2425;

import java.util.*;


/**
 * The objects of this class are calculators for single-source shortest paths
 * on a given directed and weighted graph. The graph to work on must be passed
 * when the calculator object is created and cannot contain edges with negative weights.
 * The calculator implements the classic Dijkstra algorithm for single-source shortest paths
 * using a priority queue that extracts the element with the minimum priority and updates
 * the priorities with the decreasePriority operation in logarithmic time (queue
 * implemented with a binary heap). In this case, the execution time of Dijkstra's algorithm
 * is {@code O(n log m)} where {@code n} is the number of nodes in the graph and {@code m}
 * is the number of edges.
 *
 * @param <L> the type of the labels of the graph nodes
 * @author Luca Tesei (template)
 * Pilade Jr Tomassini
 * piladejr.tomassini@studenti.unicam.it (implementation)
 */
public class DijkstraShortestPathComputer<L>
        implements SingleSourceShortestPathComputer<L> {

    private GraphNode<L> lastSource;

    private final Graph<L> graph;

    private boolean isComputed = false;

    // priority queue used in the algorithm
    private final BinaryHeapMinPriorityQueue queue;

    /**
     * Creates a single-source shortest path calculator for a directed and weighted
     * graph without negative weights.
     *
     * @param graph the graph on which the shortest path calculator operates
     * @throws NullPointerException     if the provided graph is null
     * @throws IllegalArgumentException if the provided graph is empty, null, not weighted,
     *                                  i.e., there exists at least one edge
     *                                  whose weight is {@code Double.NaN}, or if it contains                                     at least one negative weight
     */
    public DijkstraShortestPathComputer(Graph<L> graph) {
        if (graph == null) {
            throw new NullPointerException("The graph cannot be null.");
        }
        if (graph.nodeCount() == 0) {
            throw new IllegalArgumentException("The graph cannot be empty.");
        }
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("The graph must be directed.");
        }
        for (GraphEdge<L> edge : graph.getEdges()) {
            if (Double.isNaN(edge.getWeight())) {
                throw new IllegalArgumentException("The graph must be weighted. Found an edge with weight NaN.");
            }
            if (edge.getWeight() < 0) {
                throw new IllegalArgumentException("The graph cannot contain negative weights.");
            }
        }
        this.graph = graph;
        this.queue = new BinaryHeapMinPriorityQueue();
    }

    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        if (sourceNode == null) {
            throw new IllegalArgumentException("Source node cannot be null.");
        }

        // Retrieve the actual node from the graph using its label
        GraphNode<L> realSource = graph.getNode(sourceNode.getLabel());
        if (realSource == null) {
            throw new IllegalArgumentException("Source node not found in the graph.");
        }
        this.lastSource = realSource;
        // Initialize priorities and previous nodes
        for (GraphNode<L> node : graph.getNodes()) {
            node.setPriority(Double.POSITIVE_INFINITY);
            node.setPrevious(null);
            queue.insert(node);
        }
        realSource.setPriority(0.0);
        this.queue.insert(realSource);
        //Set<GraphNode<L>> visited = new HashSet<>();
        while (!this.queue.isEmpty()) {
            GraphNode<L> currentNode = (GraphNode<L>) this.queue.extractMinimum();
            double currentPriority = currentNode.getPriority();

            // Update adjacent nodes
            for (GraphEdge<L> edge : graph.getEdgesOf(currentNode)) {
                GraphNode<L> neighbor = edge.getNode2();
                double newPriority = currentPriority + edge.getWeight();
                if (newPriority < neighbor.getPriority()) {
                    neighbor.setPriority(newPriority);
                    neighbor.setPrevious(currentNode);
                    // Insert the neighbor into the queue if not already present
                    if (neighbor.getHandle() == -1) {
                        neighbor.setPriority(newPriority);
                        neighbor.setIntegerDistance((int) newPriority);
                        this.queue.insert(neighbor);
                    } else if (newPriority < neighbor.getPriority()) {
                        this.queue.decreasePriority(neighbor, newPriority);

                    }
                }
            }
        }
        this.isComputed = true;
    }

    @Override
    public boolean isComputed() {
        return this.isComputed;
    }

    @Override
    public GraphNode<L> getLastSource() {
        if (!this.isComputed) {
            throw new IllegalStateException("Shortest paths have not been computed yet.");
        }
        return this.lastSource;
    }

    @Override
    public Graph<L> getGraph() {
        return this.graph;
    }

    @Override
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        if (targetNode == null) {
            throw new NullPointerException("Target node cannot be null.");
        }
        if (!this.isComputed) {
            throw new IllegalStateException("Shortest paths have not been computed yet.");
        }
        GraphNode<L> realTarget = this.graph.getNode(targetNode.getLabel());
        if (realTarget == null) {
            throw new IllegalArgumentException("Target node not found in the graph.");
        }
        List<GraphEdge<L>> path = new ArrayList<>();
        GraphNode<L> currentNode = realTarget;

        if (!currentNode.equals(this.lastSource)
                && currentNode.getPrevious() == null) {
            return null;
        }

        while (currentNode.getPrevious() != null) {
            GraphNode<L> previousNode = currentNode.getPrevious();
            GraphEdge<L> edge = this.graph.getEdge(previousNode, currentNode);
            if (edge == null) {
                throw new IllegalStateException("Edge between nodes not found.");
            }
            path.add(edge); // Aggiungo in fondo
            currentNode = previousNode;
        }
        Collections.reverse(path);
        return path;
    }

    /*
     * Method inserted for junit testing purposes only.
     */
    protected BinaryHeapMinPriorityQueue getQueue() {
        return this.queue;
    }

}
