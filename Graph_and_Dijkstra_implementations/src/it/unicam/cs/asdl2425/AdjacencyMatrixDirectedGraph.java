package it.unicam.cs.asdl2425;

import java.util.*;

/**
 * <h3>
 * Class that implements a directed graph using an adjacency matrix. Null node
 * labels are not accepted, and duplicate node labels are not allowed (in that case,
 * they are considered the same node).</h3>
 * <br/>
 * <p>
 * Nodes are indexed from 0 to nodeCount() - 1 following their insertion order
 * (0 is the index of the first inserted node, 1 of the second, and so on),
 * so at any moment, the adjacency matrix has dimensions nodeCount() *
 * nodeCount(). The matrix, always square, must therefore increase in size
 * with each node insertion. For this reason, it is not represented using arrays
 * but with ArrayList.</p>
 * <br/>
 * <p>
 * The GraphNode<L> objects, i.e., the nodes, are stored in a map that associates
 * each node with its assigned index (which can change over time). The domain of
 * the map thus represents the set of nodes.</p>
 * <br/>
 * <p>
 * Edges are stored in the adjacency matrix. Unlike the standard representation
 * with an adjacency matrix, the position i,j of the matrix does not contain a
 * presence flag but is null if nodes i and j are not connected by an edge and
 * contains an object of the GraphEdge<L> class if they are. This object represents
 * the edge.</p>
 * <br/>
 * This class supports methods for deleting nodes and edges and supports all
 * methods that use indices, utilizing the index assigned to each node during
 * insertion and possibly modified later.
 *
 * @author Luca Tesei (template)
 * Pilade Jr Tomassini
 * piladejr.tomassini@studenti.unicam.it (implementation)
 */

public class AdjacencyMatrixDirectedGraph<L> extends Graph<L> {
    /*
     * The following instance variables are protected solely to facilitate
     * JUnit testing.
     */

    /*
     * Set of nodes and association of each node with its index in the
     * adjacency matrix.
     */
    protected Map<GraphNode<L>, Integer> nodesIndex;

    /*
     * Adjacency matrix, the elements are either null or objects of the class
     * GraphEdge<L>. The use of ArrayList allows the matrix to gradually increase
     * in size with each new node insertion and to resize if a node is removed.
     */
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

    /**
     * Create an empty graph.
     */
    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
    }

    /**
     * Returns the number of nodes currently in the graph.
     *
     * @return the number of nodes in the graph
     */
    @Override
    public int nodeCount() {
        return this.matrix.size();
    }

    /**
     * <h3>Returns the number of edges currently in the graph.</h3>
     * <br/>
     * This method iterates through the adjacency matrix and counts all non-null
     * entries, which represent edges in the graph.
     *
     * @return the number of edges in the graph
     */
    @Override
    public int edgeCount() {
        int count = 0;
        for (ArrayList<GraphEdge<L>> row : this.matrix) { // Iterate through each row of the adjacency matrix
            for (GraphEdge<L> edge : row) { // Iterate through each edge in the row
                if (edge != null) {
                    count++; // Increment the count for each non-null edge
                }
            }
        }
        return count; // Returns the total count of edges
    }

    /**
     * Removes all nodes and edges from the graph, effectively clearing it.
     * The adjacency matrix and the node index map are emptied.
     */
    @Override
    public void clear() {
        this.matrix.clear();
        this.nodesIndex.clear();
    }

    /**
     * Checks if the graph is directed.
     *
     * @return true since the class implements a directed graph
     */
    @Override
    public boolean isDirected() {
        // This graph is implemented as a directed graph.
        return true;
    }

    /**
     * <h3>Adds a new node to the graph.</h3>
     * <br/><p>
     * This method assigns an index to the node based on the order of insertion,
     * starting from zero. It updates the adjacency matrix to accommodate the new node
     * by adding a new row and column initialized with null values.
     * </p><br/>
     * <p>
     * Duplicate nodes are not allowed, and the method returns false if the node
     * already exists in the graph.
     * </p><br/>
     *
     * @param node the node to be added
     * @return true if the node was successfully added, false if the node already exists
     * @throws NullPointerException if the node is null
     **/
    @Override
    public boolean addNode(GraphNode<L> node) {
        if (node == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        if (this.nodesIndex.containsKey(node)) { // Check if the node already exists (the class does not accept duplicated nodes)
            return false; // Node already exists
        }
        // Assign the next index to the new node
        int newIndex = this.nodesIndex.size();
        this.nodesIndex.put(node, newIndex);

        // Add a new row to the adjacency matrix
        this.matrix.add(new ArrayList<>());
        // Initialize the new row with null values
        for (int i = 0; i < this.matrix.size(); i++) {
            this.matrix.get(newIndex).add(null);
        }

        // Add a new column to each existing row
        for (ArrayList<GraphEdge<L>> graphEdges : this.matrix) {
            graphEdges.add(null);
        }

        return true; // Node successfully added
    }

    /**
     * Adds a new node to the graph using the provided label.
     * <br/><p>
     * This method creates a new `GraphNode` with the given label and delegates
     * the addition process to the other `addNode` method.
     * </p><br/>
     * <p>
     * Duplicate labels are not allowed, and the method returns false if a node
     * with the same label already exists in the graph.
     * </p><br/>
     *
     * @param label the label of the node to be added
     * @return true if the node was successfully added, false if a node with the same label already exists
     * @throws NullPointerException if the label is null
     */
    @Override
    public boolean addNode(L label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null.");
        }
        return this.addNode(new GraphNode<L>(label)); //create a new node with the given label and delegate to the other addNode method
    }

    /**
     * Removes a node from the graph.
     * <br/><p>
     * This method removes the specified node from the graph, along with its associated edges.
     * The adjacency matrix is updated by removing the row and column corresponding to the node.
     * Indices of nodes with values greater than the removed node's index are decremented by one.
     * </p><br/>
     *
     * @param node the node to be removed
     * @throws NullPointerException     if the node is null
     * @throws IllegalArgumentException if the node is not found in the graph
     */
    @Override
    public void removeNode(GraphNode<L> node) {
        if (node == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        Integer index = this.nodesIndex.remove(node); // Remove the node from the index map
        if (index == null) {
            throw new IllegalArgumentException("Node not found in the graph.");
        }
        // Remove the corresponding row from the adjacency matrix
        this.matrix.remove((int) index);
        // Remove the corresponding column from each remaining row
        for (ArrayList<GraphEdge<L>> row : this.matrix) {
            if (index < row.size()) {
                row.remove((int) index); // Remove the column at the specified index
            }
        }
        // Update indices of remaining nodes
        for (Map.Entry<GraphNode<L>, Integer> entry : this.nodesIndex.entrySet()) {
            if (entry.getValue() > index) {
                this.nodesIndex.put(entry.getKey(), entry.getValue() - 1);
            }
        }
    }

    /**
     * Removes a node from the graph based on its label.
     * <br/><p>
     * This method retrieves the node by its label and delegates the removal process
     * to the other `removeNode` method.
     * </p><br/>
     *
     * @param label the label of the node to be removed
     * @throws NullPointerException     if the label is null
     * @throws IllegalArgumentException if no node with the specified label exists in the graph
     */
    @Override
    public void removeNode(L label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null.");
        }
        GraphNode<L> node = this.getNode(label); // Retrieve the node by label
        if (node == null) {
            throw new IllegalArgumentException("Node with label " + label + " not found in the graph.");
        }
        this.removeNode(node); // Delegate to the other removeNode method
    }

    /**
     * Removes a node from the graph based on its index.
     * <br/><p>
     * This method retrieves the node by its index and delegates the removal process
     * to the other `removeNode` method.
     * </p><br/>
     *
     * @param i the index of the node to be removed
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException  if no node exists at the specified index
     */
    @Override
    public void removeNode(int i) {
        if (i < 0 || i >= this.nodeCount()) { // Check if the index is out of bounds
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
        GraphNode<L> node = this.getNode(i); // Retrieve the node by index
        if (node == null) {
            throw new IllegalArgumentException("Node at index " + i + " not found in the graph.");
        }
        this.removeNode(node); // Delegate to the other removeNode method
    }

    /**
     * Retrieves a node from the graph based on the given node object.
     *
     * @param node the node to retrieve
     * @return the node itself if it exists in the graph
     * @throws NullPointerException     if the node is null
     * @throws IllegalArgumentException if the node is not found in the graph
     */
    @Override
    public GraphNode<L> getNode(GraphNode<L> node) {
        if (getNodeIndexOf(node) != -1)
            return node; // Return the node itself, as it is already the correct object
        else {
            return null;
        }
    }

    /**
     * Retrieves a node from the graph based on its label.
     *
     * @param label the label of the node to retrieve
     * @return the node with the specified label, or null if no such node exists
     * @throws NullPointerException if the label is null
     */
    @Override
    public GraphNode<L> getNode(L label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null.");
        }
        for (GraphNode<L> node : this.nodesIndex.keySet()) { // Iterate through the nodes
            if (node.getLabel().equals(label)) {
                return node; // Return the node with the matching label
            }
        }
        return null; // Node with the specified label not found
    }

    /**
     * Retrieves the node at the specified index in the graph.
     *
     * @param i the index of the node to retrieve
     * @return the node at the specified index, or null if no node exists at that index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public GraphNode<L> getNode(int i) {
        if (i < 0 || i >= this.nodeCount()) { // Check if the index is out of bounds
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
        for (Map.Entry<GraphNode<L>, Integer> entry : this.nodesIndex.entrySet()) { // Iterate through the nodes
            if (entry.getValue() == i) {
                return entry.getKey(); // Return the node with the matching index
            }
        }
        return null; // Node at the specified index not found
    }

    /**
     * Retrieves the index of the specified node in the graph.
     * <br/><p>
     * This method checks if the given node exists in the graph and returns its index.
     * If the node is not found, it returns -1.
     * </p><br/>
     *
     * @param node the node whose index is to be retrieved
     * @return the index of the node, or -1 if the node is not found
     * @throws NullPointerException if the node is null
     */
    @Override
    public int getNodeIndexOf(GraphNode<L> node) {
        if (node == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        Integer index = this.nodesIndex.get(node); // Get the index of the node
        if (index == null) {
            return -1; // If the node is not found, return -1
        }
        return index; // Return the index of the node
    }

    /**
     * Retrieves the index of a node based on its label.
     * <br/><p>
     * This method iterates through the nodes in the graph and checks if any node
     * has a label matching the provided label. If a match is found, the index
     * of the node is returned. If no match is found, an exception is thrown.
     * </p><br/>
     *
     * @param label the label of the node whose index is to be retrieved
     * @return the index of the node with the specified label
     * @throws NullPointerException     if the label is null
     * @throws IllegalArgumentException if no node with the specified label exists in the graph
     */
    @Override
    public int getNodeIndexOf(L label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null.");
        }
        for (Map.Entry<GraphNode<L>, Integer> entry : this.nodesIndex.entrySet()) { // Iterate through the nodes
            if (entry.getKey().getLabel().equals(label)) {
                return entry.getValue(); // Return the index of the node with the matching label
            }
        }
        throw new IllegalArgumentException("Node with label '" + label + "' not found in the graph."); // Throw exception if not found
    }

    /**
     * Retrieves all nodes currently in the graph.
     * <br/><p>
     * This method returns a set containing all nodes that have been added to the graph.
     * </p><br/>
     *
     * @return a set of all nodes in the graph
     */
    @Override
    public Set<GraphNode<L>> getNodes() {
        // Return the set of nodes currently in the graph
        return this.nodesIndex.keySet();
    }

    /**
     * Adds a directed edge to the graph.
     * <br/><p>
     * This method adds the specified edge to the graph, ensuring that the edge is directed
     * and that both nodes of the edge exist in the graph. If the edge already exists, the method
     * returns false. Otherwise, the edge is added to the adjacency matrix.
     * </p><br/>
     * <p>
     * The adjacency matrix is updated to include the edge at the position corresponding
     * to the indices of the two nodes.
     * </p><br/>
     *
     * @param edge the directed edge to be added
     * @return true if the edge was successfully added, false if the edge already exists
     * @throws NullPointerException     if the edge is null
     * @throws IllegalArgumentException if the edge is not directed or if one or both nodes of the edge are not found in the graph
     */
    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        if (edge == null) {
            throw new NullPointerException("Edge cannot be null.");
        }
        if (!edge.isDirected())
            throw new IllegalArgumentException("è un grafo orientato");

        GraphNode<L> node1 = edge.getNode1();
        GraphNode<L> node2 = edge.getNode2();
        Integer index1 = this.nodesIndex.get(node1);
        Integer index2 = this.nodesIndex.get(node2);

        if (index1 == null || index2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }

        // Check if the edge already exists
        if (this.matrix.get(index1).get(index2) != null) {
            return false; // Edge already exists
        }

        // Add the edge to the adjacency matrix
        this.matrix.get(index1).set(index2, edge);
        return true; // Edge successfully added
    }

    /**
     * Adds a directed edge between two nodes in the graph.
     * <br/><p>
     * This method creates a directed edge between the specified nodes and adds it to the graph.
     * If the edge already exists, the method returns false.
     * </p><br/>
     *
     * @param node1 the source node of the edge
     * @param node2 the destination node of the edge
     * @return true if the edge was successfully added, false if the edge already exists
     * @throws NullPointerException if either node is null
     */
    @Override
    public boolean addEdge(GraphNode<L> node1, GraphNode<L> node2) {
        if (node1 == null || node2 == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        GraphEdge<L> edge = new GraphEdge<>(node1, node2, true); // Create a directed edge
        return this.addEdge(edge); // Delegate to the other addEdge method
    }

    /**
     * Adds a weighted directed edge between two nodes in the graph.
     * <br/><p>
     * This method creates a directed edge with the specified weight between the given nodes
     * and adds it to the graph. If the edge already exists, the method returns false.
     * </p><br/>
     *
     * @param node1  the source node of the edge
     * @param node2  the destination node of the edge
     * @param weight the weight of the edge
     * @return true if the edge was successfully added, false if the edge already exists
     * @throws NullPointerException     if either node is null
     * @throws IllegalArgumentException if the weight is negative
     */
    @Override
    public boolean addWeightedEdge(GraphNode<L> node1, GraphNode<L> node2, double weight) {
        if (node1 == null || node2 == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
        GraphEdge<L> edge = new GraphEdge<>(node1, node2, true, weight); // Create a directed edge with weight
        return this.addEdge(edge); // Delegate to the other addEdge method
    }

    /**
     * Adds a directed edge between two nodes in the graph using their labels.
     * <br/><p>
     * This method retrieves the nodes corresponding to the provided labels and
     * delegates the addition process to the other `addEdge` method. If one or
     * both nodes are not found in the graph, an exception is thrown.
     * </p><br/>
     *
     * @param label1 the label of the source node
     * @param label2 the label of the destination node
     * @return true if the edge was successfully added, false if the edge already exists
     * @throws NullPointerException     if either label is null
     * @throws IllegalArgumentException if one or both nodes are not found in the graph
     */
    @Override
    public boolean addEdge(L label1, L label2) {
        nullLabelCheck(label1, label2);
        GraphNode<L> node1 = this.getNode(label1);
        GraphNode<L> node2 = this.getNode(label2);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }
        return this.addEdge(node1, node2); // Delegate to the other addEdge method
    }

    private void nullLabelCheck(L label1, L label2) {
        if (label1 == null || label2 == null) {
            throw new NullPointerException("Label cannot be null.");
        }
    }

    /**
     * Adds a weighted directed edge between two nodes in the graph using their labels.
     * <br/><p>
     * This method retrieves the nodes corresponding to the provided labels and creates
     * a directed edge with the specified weight. The edge is then added to the graph.
     * If one or both nodes are not found in the graph, an exception is thrown.
     * </p><br/>
     *
     * @param label1 the label of the source node
     * @param label2 the label of the destination node
     * @param weight the weight of the edge
     * @return true if the edge was successfully added, false if the edge already exists
     * @throws NullPointerException     if either label is null
     * @throws IllegalArgumentException if one or both nodes are not found in the graph
     */
    @Override
    public boolean addWeightedEdge(L label1, L label2, double weight) {
        nullLabelCheck(label1, label2);
        GraphNode<L> node1 = this.getNode(label1);
        GraphNode<L> node2 = this.getNode(label2);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }
        GraphEdge<L> edge = new GraphEdge<>(node1, node2, true, weight); // Create a directed edge with weight
        return this.addEdge(edge); // Delegate to the other addEdge method
    }

    /**
     * Adds a directed edge between two nodes in the graph using their indices.
     * <br/><p>
     * This method retrieves the nodes corresponding to the provided indices and
     * delegates the addition process to the other `addEdge` method. If one or
     * both indices are out of bounds or the nodes are not found in the graph,
     * an exception is thrown.
     * </p><br/>
     *
     * @param i the index of the source node
     * @param j the index of the destination node
     * @return true if the edge was successfully added, false if the edge already exists
     * @throws IndexOutOfBoundsException if either index is out of bounds
     * @throws IllegalArgumentException  if one or both nodes are not found in the graph
     */
    @Override
    public boolean addEdge(int i, int j) {
        if (i < 0 || j < 0 || i >= this.nodeCount() || j >= this.nodeCount()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i + ", " + j);
        }
        GraphNode<L> node1 = this.getNode(i);
        GraphNode<L> node2 = this.getNode(j);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }
        return this.addEdge(node1, node2); // Delegate to the other addEdge method
    }

    /**
     * Adds a weighted directed edge between two nodes in the graph using their indices.
     * <br/><p>
     * This method retrieves the nodes corresponding to the provided indices and creates
     * a directed edge with the specified weight. The edge is then added to the graph.
     * If one or both indices are out of bounds or the nodes are not found in the graph,
     * an exception is thrown.
     * </p><br/>
     *
     * @param i      the index of the source node
     * @param j      the index of the destination node
     * @param weight the weight of the edge
     * @return true if the edge was successfully added, false if the edge already exists
     * @throws IndexOutOfBoundsException if either index is out of bounds
     * @throws IllegalArgumentException  if one or both nodes are not found in the graph or if the weight is negative
     */
    @Override
    public boolean addWeightedEdge(int i, int j, double weight) {
        if (i < 0 || j < 0 || i >= this.nodeCount() || j >= this.nodeCount()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i + ", " + j);
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
        GraphNode<L> node1 = this.getNode(i);
        GraphNode<L> node2 = this.getNode(j);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }
        GraphEdge<L> edge = new GraphEdge<>(node1, node2, true, weight); // Create a directed edge with weight
        return this.addEdge(edge); // Delegate to the other addEdge method
    }

    /**
     * Removes a directed edge from the graph.
     * <br/><p>
     * This method removes the specified edge from the graph by updating the adjacency matrix.
     * It ensures that the edge exists in the graph and that both nodes of the edge are valid.
     * If the edge does not exist or one of the nodes is not found, an exception is thrown.
     * </p><br/>
     *
     * @param edge the directed edge to be removed
     * @throws NullPointerException     if the edge is null
     * @throws IllegalArgumentException if one or both nodes of the edge are not found in the graph
     * @throws IllegalArgumentException if the edge does not exist in the graph
     */
    @Override
    public void removeEdge(GraphEdge<L> edge) {
        if (edge == null) {
            throw new NullPointerException("Edge cannot be null.");
        }
        GraphNode<L> node1 = edge.getNode1();
        GraphNode<L> node2 = edge.getNode2();
        Integer index1 = this.nodesIndex.get(node1);
        Integer index2 = this.nodesIndex.get(node2);

        if (index1 == null || index2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }

        if (this.matrix.get(index1).get(index2) == null) {
            throw new IllegalArgumentException("Edge does not exist in the graph.");
        }

        // Remove the edge from the adjacency matrix
        this.matrix.get(index1).set(index2, null);
    }

    /**
     * Removes a directed edge between two nodes in the graph.
     * <br/><p>
     * This method removes the edge connecting the specified source and destination nodes
     * from the adjacency matrix. It ensures that both nodes exist in the graph and that
     * the edge exists before attempting removal. If any of these conditions are not met,
     * an exception is thrown.
     * </p><br/>
     *
     * @param node1 the source node of the edge
     * @param node2 the destination node of the edge
     * @throws NullPointerException     if either node is null
     * @throws IllegalArgumentException if one or both nodes are not found in the graph
     * @throws IllegalArgumentException if the edge does not exist in the graph
     */
    @Override
    public void removeEdge(GraphNode<L> node1, GraphNode<L> node2) {
        if (node1 == null || node2 == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        Integer index1 = this.nodesIndex.get(node1);
        Integer index2 = this.nodesIndex.get(node2);

        if (index1 == null || index2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }

        if (this.matrix.get(index1).get(index2) == null) {
            throw new IllegalArgumentException("Edge does not exist in the graph.");
        }

        // Remove the edge from the adjacency matrix
        this.matrix.get(index1).set(index2, null);
    }

    /**
     * Removes a directed edge between two nodes in the graph using their labels.
     * <br/><p>
     * This method retrieves the nodes corresponding to the provided labels and
     * delegates the removal process to the other `removeEdge` method. If one or
     * both nodes are not found in the graph, an exception is thrown.
     * </p><br/>
     *
     * @param label1 the label of the source node
     * @param label2 the label of the destination node
     * @throws NullPointerException     if either label is null
     * @throws IllegalArgumentException if one or both nodes are not found in the graph
     */
    @Override
    public void removeEdge(L label1, L label2) {
        nullLabelCheck(label1, label2);
        GraphNode<L> node1 = this.getNode(label1);
        GraphNode<L> node2 = this.getNode(label2);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }
        this.removeEdge(node1, node2); // Delegate to the other removeEdge method
    }

    /**
     * Removes a directed edge between two nodes in the graph using their indices.
     * <br/><p>
     * This method retrieves the nodes corresponding to the provided indices and
     * delegates the removal process to the other `removeEdge` method. If one or
     * both indices are out of bounds or the nodes are not found in the graph,
     * an exception is thrown.
     * </p><br/>
     *
     * @param i the index of the source node
     * @param j the index of the destination node
     * @throws IndexOutOfBoundsException if either index is out of bounds
     * @throws IllegalArgumentException  if one or both nodes are not found in the graph
     */
    @Override
    public void removeEdge(int i, int j) {
        if (i < 0 || j < 0 || i >= this.nodeCount() || j >= this.nodeCount()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i + ", " + j);
        }
        GraphNode<L> node1 = this.getNode(i);
        GraphNode<L> node2 = this.getNode(j);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }
        this.removeEdge(node1, node2); // Delegate to the other removeEdge method
    }

    /**
     * Retrieves a directed edge from the graph.
     * <br/><p>
     * This method retrieves the specified edge from the adjacency matrix, ensuring
     * that both nodes of the edge exist in the graph. If either node is not found,
     * an exception is thrown.
     * </p><br/>
     *
     * @param edge the directed edge to retrieve
     * @return the edge from the adjacency matrix, or null if the edge does not exist
     * @throws NullPointerException     if the edge is null
     * @throws IllegalArgumentException if one or both nodes of the edge are not found in the graph
     */
    @Override
    public GraphEdge<L> getEdge(GraphEdge<L> edge) {
        if (edge == null) {
            throw new NullPointerException("Edge cannot be null.");
        }
        GraphNode<L> node1 = edge.getNode1();
        GraphNode<L> node2 = edge.getNode2();
        Integer index1 = this.nodesIndex.get(node1);
        Integer index2 = this.nodesIndex.get(node2);

        if (index1 == null || index2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }

        return this.matrix.get(index1).get(index2); // Return the edge from the adjacency matrix
    }

    /**
     * Retrieves a directed edge between two nodes in the graph.
     * <br/><p>
     * This method retrieves the edge connecting the specified source and destination nodes
     * from the adjacency matrix. It ensures that both nodes exist in the graph before
     * attempting retrieval. If any of these conditions are not met, an exception is thrown.
     * </p><br/>
     *
     * @param node1 the source node of the edge
     * @param node2 the destination node of the edge
     * @return the edge connecting the two nodes, or null if the edge does not exist
     * @throws NullPointerException     if either node is null
     * @throws IllegalArgumentException if one or both nodes are not found in the graph
     */
    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
        if (node1 == null || node2 == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        Integer index1 = this.nodesIndex.get(node1);
        Integer index2 = this.nodesIndex.get(node2);

        if (index1 == null || index2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }

        return this.matrix.get(index1).get(index2); // Return the edge from the adjacency matrix
    }

    /**
     * Retrieves a directed edge between two nodes in the graph using their labels.
     * <br/><p>
     * This method retrieves the nodes corresponding to the provided labels and
     * delegates the retrieval process to the other `getEdge` method. If one or
     * both nodes are not found in the graph, an exception is thrown.
     * </p><br/>
     *
     * @param label1 the label of the source node
     * @param label2 the label of the destination node
     * @return the edge connecting the two nodes, or null if the edge does not exist
     * @throws NullPointerException     if either label is null
     * @throws IllegalArgumentException if one or both nodes are not found in the graph
     */
    @Override
    public GraphEdge<L> getEdge(L label1, L label2) {
        nullLabelCheck(label1, label2);
        GraphNode<L> node1 = this.getNode(label1);
        GraphNode<L> node2 = this.getNode(label2);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }
        return this.getEdge(node1, node2); // Delegate to the other getEdge method
    }

    /**
     * Retrieves a directed edge between two nodes in the graph using their indices.
     * <br/><p>
     * This method retrieves the nodes corresponding to the provided indices and
     * delegates the retrieval process to the other `getEdge` method. If one or
     * both indices are out of bounds or the nodes are not found in the graph,
     * an exception is thrown.
     * </p><br/>
     *
     * @param i the index of the source node
     * @param j the index of the destination node
     * @return the edge connecting the two nodes, or null if the edge does not exist
     * @throws IndexOutOfBoundsException if either index is out of bounds
     * @throws IllegalArgumentException  if one or both nodes are not found in the graph
     */
    @Override
    public GraphEdge<L> getEdge(int i, int j) {
        if (i < 0 || j < 0 || i >= this.nodeCount() || j >= this.nodeCount()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + i + ", " + j);
        }
        GraphNode<L> node1 = this.getNode(i);
        GraphNode<L> node2 = this.getNode(j);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found in the graph.");
        }
        return this.getEdge(node1, node2); // Delegate to the other getEdge method
    }

    /**
     * Retrieves the adjacent nodes of a specified node in the graph.
     * <br/><p>
     * This method finds all nodes that are directly reachable from the given node
     * by iterating through the adjacency matrix. It ensures that the node exists
     * in the graph before attempting to retrieve its adjacent nodes. If the node
     * is null or not found in the graph, an exception is thrown.
     * </p><br/>
     *
     * @param node the node whose adjacent nodes are to be retrieved
     * @return a set of nodes adjacent to the specified node
     * @throws NullPointerException     if the node is null
     * @throws IllegalArgumentException if the node is not found in the graph
     */
    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        if (node == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        Integer index = this.nodesIndex.get(node);
        if (index == null) {
            throw new IllegalArgumentException("Node not found in the graph.");
        }
        Set<GraphNode<L>> adjacentNodes = new HashSet<>();
        for (int j = 0; j < this.matrix.size(); j++) {
            GraphEdge<L> edge = this.matrix.get(index).get(j);
            if (edge != null) {
                adjacentNodes.add(edge.getNode2()); // Add the destination node of the edge
            }
        }
        return adjacentNodes; // Return the set of adjacent nodes
    }

    /**
     * Retrieves the adjacent nodes of a node based on its label.
     * <br/><p>
     * This method retrieves the node corresponding to the provided label and delegates
     * the process of finding adjacent nodes to the other `getAdjacentNodesOf` method.
     * If the label is null or no node with the specified label exists in the graph,
     * an exception is thrown.
     * </p><br/>
     *
     * @param label the label of the node whose adjacent nodes are to be retrieved
     * @return a set of nodes adjacent to the node with the specified label
     * @throws NullPointerException     if the label is null
     * @throws IllegalArgumentException if no node with the specified label exists in the graph
     */
    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(L label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null.");
        }
        GraphNode<L> node = this.getNode(label); // Retrieve the node by label
        if (node == null) {
            throw new IllegalArgumentException("Node with label " + label + " not found in the graph.");
        }
        return this.getAdjacentNodesOf(node); // Delegate to the other getAdjacentNodesOf method
    }

    /**
     * Retrieves the adjacent nodes of a node based on its index.
     * <br/><p>
     * This method retrieves the node corresponding to the provided index and delegates
     * the process of finding adjacent nodes to the other `getAdjacentNodesOf` method.
     * If the index is out of bounds or no node exists at the specified index, an exception
     * is thrown.
     * </p><br/>
     *
     * @param i the index of the node whose adjacent nodes are to be retrieved
     * @return a set of nodes adjacent to the node at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException  if no node exists at the specified index
     */
    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(int i) {
        if (i < 0 || i >= this.nodeCount()) { // Check if the index is out of bounds
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
        GraphNode<L> node = this.getNode(i); // Retrieve the node by index
        if (node == null) {
            throw new IllegalArgumentException("Node at index " + i + " not found in the graph.");
        }
        return this.getAdjacentNodesOf(node); // Delegate to the other getAdjacentNodesOf method
    }

    /**
     * Retrieves the predecessor nodes of a specified node in the graph.
     * <br/><p>
     * This method finds all nodes that have directed edges pointing to the given node
     * by iterating through the adjacency matrix. It ensures that the node exists
     * in the graph before attempting to retrieve its predecessor nodes. If the node
     * is null or not found in the graph, an exception is thrown.
     * </p><br/>
     *
     * @param node the node whose predecessor nodes are to be retrieved
     * @return a set of nodes that have directed edges pointing to the specified node
     * @throws NullPointerException     if the node is null
     * @throws IllegalArgumentException if the node is not found in the graph
     */
    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        if (node == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        Integer index = this.nodesIndex.get(node);
        if (index == null) {
            throw new IllegalArgumentException("Node not found in the graph.");
        }
        Set<GraphNode<L>> predecessorNodes = new HashSet<>();
        for (ArrayList<GraphEdge<L>> graphEdges : this.matrix) {
            GraphEdge<L> edge = graphEdges.get(index);
            if (edge != null) {
                predecessorNodes.add(edge.getNode1()); // Add the source node of the edge
            }
        }
        return predecessorNodes; // Return the set of predecessor nodes
    }

    /**
     * Retrieves the predecessor nodes of a node based on its label.
     * <br/><p>
     * This method retrieves the node corresponding to the provided label and delegates
     * the process of finding predecessor nodes to the other `getPredecessorNodesOf` method.
     * If the label is null or no node with the specified label exists in the graph,
     * an exception is thrown.
     * </p><br/>
     *
     * @param label the label of the node whose predecessor nodes are to be retrieved
     * @return a set of nodes that have directed edges pointing to the node with the specified label
     * @throws NullPointerException     if the label is null
     * @throws IllegalArgumentException if no node with the specified label exists in the graph
     */
    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(L label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null.");
        }
        GraphNode<L> node = this.getNode(label); // Retrieve the node by label
        if (node == null) {
            throw new IllegalArgumentException("Node with label " + label + " not found in the graph.");
        }
        return this.getPredecessorNodesOf(node); // Delegate to the other getPredecessorNodesOf method
    }

    /**
     * Retrieves the predecessor nodes of a node based on its index.
     * <br/><p>
     * This method retrieves the node corresponding to the provided index and delegates
     * the process of finding predecessor nodes to the other `getPredecessorNodesOf` method.
     * If the index is out of bounds or no node exists at the specified index, an exception
     * is thrown.
     * </p><br/>
     *
     * @param i the index of the node whose predecessor nodes are to be retrieved
     * @return a set of nodes that have directed edges pointing to the node at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException  if no node exists at the specified index
     */
    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(int i) {
        if (i < 0 || i >= this.nodeCount()) { // Check if the index is out of bounds
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
        GraphNode<L> node = this.getNode(i); // Retrieve the node by index
        if (node == null) {
            throw new IllegalArgumentException("Node at index " + i + " not found in the graph.");
        }
        return this.getPredecessorNodesOf(node); // Delegate to the other getPredecessorNodesOf method
    }

    /**
     * Retrieves all outgoing edges of a specified node in the graph.
     * <br/><p>
     * This method finds all edges originating from the given node by iterating
     * through the adjacency matrix. It ensures that the node exists in the graph
     * before attempting to retrieve its edges. If the node is null or not found
     * in the graph, an exception is thrown.
     * </p><br/>
     *
     * @param node the node whose outgoing edges are to be retrieved
     * @return a set of edges originating from the specified node
     * @throws NullPointerException     if the node is null
     * @throws IllegalArgumentException if the node is not found in the graph
     */
    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        if (node == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        Integer index = this.nodesIndex.get(node);
        if (index == null) {
            throw new IllegalArgumentException("Node not found in the graph.");
        }
        Set<GraphEdge<L>> edges = new HashSet<>();
        for (int j = 0; j < this.matrix.size(); j++) {
            GraphEdge<L> edge = this.matrix.get(index).get(j);
            if (edge != null) {
                edges.add(edge); // Add the edge to the set
            }
        }
        return edges; // Return the set of edges
    }

    /**
     * Retrieves all outgoing edges of a node based on its label.
     * <br/><p>
     * This method retrieves the node corresponding to the provided label and delegates
     * the process of finding outgoing edges to the other `getEdgesOf` method.
     * If the label is null or no node with the specified label exists in the graph,
     * an exception is thrown.
     * </p><br/>
     *
     * @param label the label of the node whose outgoing edges are to be retrieved
     * @return a set of edges originating from the node with the specified label
     * @throws NullPointerException     if the label is null
     * @throws IllegalArgumentException if no node with the specified label exists in the graph
     */
    @Override
    public Set<GraphEdge<L>> getEdgesOf(L label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null.");
        }
        GraphNode<L> node = this.getNode(label); // Retrieve the node by label
        if (node == null) {
            throw new IllegalArgumentException("Node with label " + label + " not found in the graph.");
        }
        return this.getEdgesOf(node); // Delegate to the other getEdgesOf method
    }

    /**
     * Retrieves all outgoing edges of a node based on its index.
     * <br/><p>
     * This method retrieves the node corresponding to the provided index and delegates
     * the process of finding outgoing edges to the other `getEdgesOf` method.
     * If the index is out of bounds or no node exists at the specified index, an exception
     * is thrown.
     * </p><br/>
     *
     * @param i the index of the node whose outgoing edges are to be retrieved
     * @return a set of edges originating from the node at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException  if no node exists at the specified index
     */
    @Override
    public Set<GraphEdge<L>> getEdgesOf(int i) {
        if (i < 0 || i >= this.nodeCount()) { // Check if the index is out of bounds
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
        GraphNode<L> node = this.getNode(i); // Retrieve the node by index
        if (node == null) {
            throw new IllegalArgumentException("Node at index " + i + " not found in the graph.");
        }
        return this.getEdgesOf(node); // Delegate to the other getEdgesOf method
    }

    /**
     * Retrieves all ingoing edges of a specified node in the graph.
     * <br/><p>
     * This method finds all edges that point to the given node by iterating
     * through the adjacency matrix. It ensures that the node exists in the graph
     * before attempting to retrieve its ingoing edges. If the node is null or not
     * found in the graph, an exception is thrown.
     * </p><br/>
     *
     * @param node the node whose ingoing edges are to be retrieved
     * @return a set of edges pointing to the specified node
     * @throws NullPointerException     if the node is null
     * @throws IllegalArgumentException if the node is not found in the graph
     */
    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        if (node == null) {
            throw new NullPointerException("Node cannot be null.");
        }
        Integer index = this.nodesIndex.get(node);
        if (index == null) {
            throw new IllegalArgumentException("Node not found in the graph.");
        }
        Set<GraphEdge<L>> ingoingEdges = new HashSet<>();
        for (ArrayList<GraphEdge<L>> graphEdges : this.matrix) {
            GraphEdge<L> edge = graphEdges.get(index);
            if (edge != null) {
                ingoingEdges.add(edge); // Add the edge to the set
            }
        }
        return ingoingEdges; // Return the set of ingoing edges
    }

    /**
     * Retrieves all ingoing edges of a node based on its label.
     * <br/><p>
     * This method retrieves the node corresponding to the provided label and delegates
     * the process of finding ingoing edges to the other `getIngoingEdgesOf` method.
     * If the label is null or no node with the specified label exists in the graph,
     * an exception is thrown.
     * </p><br/>
     *
     * @param label the label of the node whose ingoing edges are to be retrieved
     * @return a set of edges pointing to the node with the specified label
     * @throws NullPointerException     if the label is null
     * @throws IllegalArgumentException if no node with the specified label exists in the graph
     */
    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(L label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null.");
        }
        GraphNode<L> node = this.getNode(label); // Retrieve the node by label
        if (node == null) {
            throw new IllegalArgumentException("Node with label " + label + " not found in the graph.");
        }
        return this.getIngoingEdgesOf(node); // Delegate to the other getIngoingEdgesOf method
    }

    /**
     * Retrieves all ingoing edges of a node based on its index.
     * <br/><p>
     * This method retrieves the node corresponding to the provided index and delegates
     * the process of finding ingoing edges to the other `getIngoingEdgesOf` method.
     * If the index is out of bounds or no node exists at the specified index, an exception
     * is thrown.
     * </p><br/>
     *
     * @param i the index of the node whose ingoing edges are to be retrieved
     * @return a set of edges pointing to the node at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException  if no node exists at the specified index
     */
    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(int i) {
        if (i < 0 || i >= this.nodeCount()) { // Check if the index is out of bounds
            throw new IndexOutOfBoundsException("Index out of bounds: " + i);
        }
        GraphNode<L> node = this.getNode(i); // Retrieve the node by index
        if (node == null) {
            throw new IllegalArgumentException("Node at index " + i + " not found in the graph.");
        }
        return this.getIngoingEdgesOf(node); // Delegate to the other getIngoingEdgesOf method
    }

    /**
     * Retrieves all edges currently in the graph.
     * <p></p>
     * This method iterates through the adjacency matrix and collects all non-null
     * entries, which represent edges in the graph.
     *
     * @return a set containing all edges in the graph
     */
    @Override
    public Set<GraphEdge<L>> getEdges() {
        // Create a set to hold all edges
        Set<GraphEdge<L>> edges = new HashSet<>();
        // Iterate through the adjacency matrix
        for (ArrayList<GraphEdge<L>> graphEdges : this.matrix) {
            for (GraphEdge<L> edge : graphEdges) {
                if (edge != null) {
                    edges.add(edge); // Add non-null edges to the set
                }
            }
        }
        return edges; // Return the set of edges
    }
}
