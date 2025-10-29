# Graph and Dijkstra Implementations

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
![Java](https://img.shields.io/badge/Java-8-red?logo=openjdk)

## 🧩 Description

This project provides my personal implementations of graph data structures and the famous Dijkstra’s algorithm for computing shortest paths in a weighted graph. You’ll find code to construct graphs, add edges, and run shortest-path calculations.

The entire project is an implementation of a template, provided by professor Luca Tesei from the University of Camerino, for the algorithm and data structures exam . 

This project is designed for clarity and educational use.

Important note: the Dijkstra algorithm that I implemented is based on a Binary Heap Min Priority Queue. The Graph itself uses an Adjacency matrix for its representation. You’ll find their implementations inside the project as well

## 📦 Installation

Follow these steps to get the project up and running on your local machine.

### Prerequisites

- Java 8 or later
- Git (to clone the repository)
- Maven or Gradle (for building and running tests)
- JUnit 5 (for running test cases)
- Basic familiarity with command line (optional but helpful)

### Steps

Clone the repository

```bash
 git clone https://github.com/PiladeJr/Graph_and_Dijkstra_implementations.git
cd Graph_and_Dijkstra_implementations
```
Build / Compile

Assuming a simple Java project (adjust as needed for your setup):

```bash
# if using Maven
mvn clean install

# if using plain javac
javac -d out src/**/*.java    
```

### 🚀 Running the Project

You can run or test the project directly from your IDE (IntelliJ, Eclipse, or VS Code) or through the command line.

the project itself does not provide a main class to run the entire project but there are multiple test classes to run the separate parts of the project. if your objective is to test the dijkstra algorithm in an oriented graph then all you have to do is run the DijkstraShortestPathComputerTest.java file with the following command 

```bash
java -cp out com.example.DijkstraShortestPathComputerTest
```

otherwise you'll see below on the test section all the specific classes
## 🧠 Usage/Examples

1- Graph creation

```java
Graph graph = new Graph(numberOfVertices);
graph.addEdge(0, 1, 4);
graph.addEdge(0, 2, 1);
graph.addEdge(2, 1, 2);
// ... add more edges
int source = 0;
int[] distances = Dijkstra.compute(graph, source);

System.out.println("Distances from source " + source + ":");
for (int i = 0; i < distances.length; i++) {
  System.out.println("Vertex " + i + " -> " + distances[i]);
}
```
2- Computing shortest paths
```java
DijkstraShortestPathComputer<String> dijkstra = new DijkstraShortestPathComputer<>(graph);
dijkstra.computeShortestPathsFrom(nodeA);
List<GraphEdge<String>> shortestPath = dijkstra.getShortestPathTo(nodeB);
```
3- Working with the Priority Queue
```java
BinaryHeapMinPriorityQueue queue = new BinaryHeapMinPriorityQueue();
PriorityQueueElement node = new GraphNode<>(10);
node.setPriority(10);
queue.insert(node);
PriorityQueueElement min = queue.extractMinimum();
```
## Use-cases

Educational: Use this code to learn how graph structures and shortest path algorithms work.

Reference: Use the implementation as a baseline for your own project or improvements.

Extension: You may extend the graph class (e.g., for directed graphs, negative weights, etc.).

## 🧪 Tests

All test cases are included in the repository under
src/test/java/it/unicam/cs/asdl2425

here are the test files provided:

AdjacencyMatrixDirectedGraphTest.java

BinaryHeapMinPriorityQueueTest.java

DijkstraShortestPathComputerTest.java

### Running Tests

you can run the tests directly on IDEs like INTELLIJ or on command line

```bash
  mvn test

  #on gradle
  gradle test
```

### Coverage Summary

All test suites are provided by Luca Tesei. Each class provides a specific suite of tests for their respective arguments.

### 🧱 1. AdjacencyMatrixDirectedGraphTest.java

AdjacencyMatrixDirectedGraphTest.java provides comprehensive coverage of graph functionality, edge management, and algorithm integrity.

| **Test Category**            | **Purpose**                                                                               |
| ---------------------------- | ----------------------------------------------------------------------------------------- |
| **Graph Initialization**     | Verifies correct setup of an empty directed graph.                                        |
| **Node Management**          | Adds/removes nodes, validates count and index consistency.                                |
| **Edge Operations**          | Tests edge addition, removal, and duplication handling.                                   |
| **Graph Size & Structure**   | Confirms accurate node/edge counts and adjacency integrity.                               |
| **Directed Behavior**        | Validates directed edge semantics (no reverse edges).                                     |
| **Adjacency & Connectivity** | Checks that neighbors and in-going/out-going edges are properly tracked.                  |
| **Complex Graph Scenarios**  | Includes cyclic, sparse, dense, disconnected, and loop graphs.                            |
| **Error Handling**           | Tests for expected exceptions (`NullPointerException`, `IllegalArgumentException`, etc.). |
| **Index Updates**            | Ensures adjacency matrix and indices remain valid after node removals.                    |


Run the full Graph test suite using:
```bash
mvn test -Dtest=AdjacencyMatrixDirectedGraphTest
```

or
```bash
gradle test --tests AdjacencyMatrixDirectedGraphTest
```

### ⚙️ 2. BinaryHeapMinPriorityQueueTest.java

This suite tests the Binary Heap Min Priority Queue, which underpins Dijkstra’s algorithm’s efficiency.

| **Category**                      | **Description**                                                                                                                      |
| --------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| **Construction & Initialization** | Verifies that a newly created heap starts empty and handles initial insertions properly.                                             |
| **Insertion**                     | Tests inserting multiple elements with increasing, decreasing, and duplicate priorities; checks for `NullPointerException` handling. |
| **Minimum & Extraction**          | Confirms that `minimum()` and `extractMinimum()` always return the correct element according to heap order.                          |
| **Decrease Priority**             | Ensures `decreasePriority()` correctly updates node priority and validates exception conditions.                                     |
| **IsEmpty & Size**                | Confirms heap emptiness after insertions/extractions and validates `size()` accuracy at each stage.                                  |
| **Clear Operation**               | Tests that `clear()` properly resets the heap.                                                                                       |
| **Large Data Set**                | Inserts and extracts **10,000+ elements**, validating heap performance and correctness under heavy load.                             |
| **Stability**                     | Verifies that elements with the same priority are extracted in the order of insertion.                                               |
| **Single Element Heap**           | Confirms correct behavior when the heap contains only one element.                                                                   |
| **Reinsert After Extraction**     | Ensures re-insertion after extraction maintains heap order.                                                                          |

Run the full Min Priority Queue test suite using:
```bash
mvn test -Dtest=BinaryHeapMinPriorityQueueTest
```

or
```bash
gradle test --tests BinaryHeapMinPriorityQueueTest
```

### 🧮 3.Dijkstra’s Algorithm Tests

This test suite ensures the correctness, robustness, and reliability of the Dijkstra shortest-path implementation.
It covers multiple scenarios including cyclic graphs, unreachable nodes, and multiple source updates.

| **Test Category**             | **Description**                                                                            |
| ----------------------------- | ------------------------------------------------------------------------------------------ |
| **Shortest Path Computation** | Validates that the algorithm correctly computes the shortest path between connected nodes. |
| **Unreachable Nodes**         | Ensures the algorithm returns `null` for nodes not reachable from the source.              |
| **Computation State**         | Checks the internal state before and after running the algorithm (using `isComputed()`).   |
| **Source Tracking**           | Verifies that the correct last source node is stored and retrieved (`getLastSource()`).    |
| **Graphs with Cycles**        | Tests robustness on cyclic graphs and multiple path updates.                               |
| **Multiple Path Updates**     | Confirms that repeated computations with different sources produce correct results.        |
| **Path Reconstruction**       | Asserts that the reconstructed edge list matches the expected shortest path sequence.      |
| **Floating-Point Accuracy**   | Uses tolerance-based comparisons to handle floating-point precision in path weights.       |

Run the full Dijkstra test suite using:
```bash
mvn test -Dtest=DijkstraShortestPathComputerTest
```

or
```bash
gradle test --tests DijkstraShortestPathComputerTest
```


The tests use JUnit 5 (org.junit.jupiter.api) and can be run via Maven or any IDE that supports JUnit.




## 👥 Credits
Implementation: PiladeJr

Template & Guidance: Professor Luca Tesei – University of Camerino

Special thanks to the University’s Algorithms and Data Structures course, and its professors, for the original framework.




## 📘 Summary

This project showcases a clean and modular implementation of graph theory structures and Dijkstra’s algorithm, complete with thorough testing and educational clarity.
It serves as both a learning resource and a foundation for future algorithmic projects.
