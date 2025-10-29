/**
 *
 */
package it.unicam.cs.asdl2425;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * <h3>Implementation of a priority queue using a binary heap. </h3>
 * <br/>
 * The objects inserted into the queue implement the PriorityQueueElement interface, which
 * allows managing the priority and a handle of the element. The handle is
 * essential to perform the decreasePriority operation in logarithmic time,
 * which, without the handle, would require searching for the element within
 * the heap and then updating its position. In the case of a binary heap
 * represented with an ArrayList, the handle is simply the index where the
 * element is located in the ArrayList. This field must naturally be kept
 * updated if the element is moved to another position.
 *
 * @author Luca Tesei (template)
 *         Pilade Jr Tomassini
 *         piladejr.tomassini@studenti.unicam.it (implementation)
 *
 */
public class BinaryHeapMinPriorityQueue {

    /*
     * ArrayList per la rappresentazione dello heap. Vengono usate tutte le
     * posizioni (la radice dello heap è quindi in posizione 0).
     */
    private final ArrayList<PriorityQueueElement> heap;

    /**
     * Crea una coda con priorità vuota.
     *
     */
    public BinaryHeapMinPriorityQueue() {
        this.heap = new ArrayList<PriorityQueueElement>();
    }

    /**
     * Returns the root of the binary heap, i.e., the element with minimum
     * priority.
     *
     * @return the root of the binary heap
     */
    protected PriorityQueueElement root() {
        if (this.heap.isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty.");
        }
        return this.heap.get(0);
    }

    /**
     * Add an element to this min-priority queue. The current priority
     * associated with the element will be used to place it in the correct
     * position in the heap. The handle of the element will also be set
     * accordingly.
     *
     * @param element
     *                    the new element to add
     * @throws NullPointerException
     *                                  if the element passed is null
     */
    public void insert(PriorityQueueElement element) {
        if (element == null) {
            throw new NullPointerException("The element passed is null.");
        }
        // Add the element to the end of the heap
        this.heap.add(element);
        // Set the handle of the element to its index in the heap
        element.setHandle(this.heap.size() - 1);
        // Restore the heap property by bubbling up the new element
        heapifyUp(this.heap.size() - 1);
    }

    /**
     * Restores the heap property by bubbling up the element at the specified index.
     * This method ensures that the binary heap maintains its min-heap property
     * after an element's priority has been decreased or a new element has been added.
     *
     * @param i the index of the element to bubble up
     */
    private void heapifyUp(int i) {
        // Bubble up the element at index i to restore the heap property
        while (i > 0) {
            int parentIndex = parentIndex(i);
            if (heap.get(parentIndex).getPriority() > heap.get(i).getPriority()) {
                // Swap with parent
                swap(i, parentIndex);
                i = parentIndex; // Move up to the parent's index
            } else {
                break; // The heap property is restored
            }
        }
    }


    /**
     * Swaps the elements at the specified indices in the binary heap.
     * This operation also updates the handles of the swapped elements
     * to reflect their new positions in the heap.
     *
     * @param index1 the index of the first element to swap
     * @param index2 the index of the second element to swap
     */
    private void swap(int index1, int index2) {
        // Swap the elements at indices index1 and index2
        PriorityQueueElement temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);

        // Update the handles of the swapped elements
        heap.get(index1).setHandle(index1);
        heap.get(index2).setHandle(index2);
    }


    /**
     * Returns the current minimum element of this min-priority queue without
     * extracting it. This operation does not affect the heap.
     *
     * @return the current minimum element of this min-priority queue
     *
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement minimum() {

        return root(); //the element with min priority in a minPriorityQueue is always the root
    }

    /**
     * Extract the current minimum element from this min-priority queue. The
     * ternary heap will be updated accordingly.
     *
     * @return the current minimum element
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement extractMinimum() {
        if (this.heap.isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty.");
        }
        // Get the minimum element (root)
        PriorityQueueElement min = root();

        PriorityQueueElement lastElement = this.heap.get(size() - 1); // copies the last element
        this.heap.set(0, lastElement);  //replaces the root with the last element
        this.heap.remove(this.heap.size() - 1); // removes the last element
        // Restore the heap property by bubbling down the new root
        heapifyDown(0);
        min.setHandle(-1); // Set the handle of the extracted element to -1 to indicate it is no longer in the queue
        return min;
    }

    /**
     * Restores the heap property by bubbling down the element at the specified index.
     * This method ensures that the binary heap maintains its min-heap property
     * after an element has been removed or its priority has been increased.
     *
     * @param i the index of the element to bubble down
     */
    private void heapifyDown(int i) {
        int left = leftChildIndex(i); //get the left child of the node
        int right = rightChildIndex(i);  //get the right child of the node
        int smallest = i;
        // Find the smallest among the current node and its children
        smallest = getSmallest(left, smallest);
        smallest = getSmallest(right, smallest);
        if (smallest != i) {
            // Swap the current node with the smallest child
            swap(i, smallest);
            // Recursively heapify down the affected subtree
            heapifyDown(smallest);
        }
    }

    /**
     * Compares the priority of the current element with the priority of the element
     * at the specified index and determines the smallest element. If the priorities
     * are equal, the element with the smaller handle is considered smaller.
     *
     * @param provided the index of the element to compare
     * @param smallest the index of the current smallest element
     * @return the index of the smallest element between the current and the specified index
     */
    private int getSmallest(int provided, int smallest) {
        if (provided != -1) {
            PriorityQueueElement providedNode = heap.get(provided);
            PriorityQueueElement current = heap.get(smallest);

            if (providedNode.getPriority() < current.getPriority() ||
                    (providedNode.getPriority() == current.getPriority() && providedNode.getHandle() < current.getHandle())) {
                smallest = provided;
            }
        }
        return smallest;
    }

    /**
     * Decrease the priority associated to an element of this min-priority
     * queue. The position of the element in the heap must be changed
     * accordingly. The changed element may become the minimum element. The
     * handle of the element will also be changed accordingly.
     *
     * @param element
     *                        the element whose priority will be decreased, it
     *                        must currently be inside this min-priority queue
     * @param newPriority
     *                        the new priority to assign to the element
     *
     * @throws NoSuchElementException
     *                                      if the element is not currently
     *                                      present in this min-priority queue
     * @throws IllegalArgumentException
     *                                      if the specified newPriority is not
     *                                      strictly less than the current
     *                                      priority of the element
     */
    public void decreasePriority(PriorityQueueElement element,
                                 double newPriority) {
        int handle = element.getHandle();
        if (handle < 0 || handle >= this.heap.size() || this.heap.get(handle) != element) {
            throw new NoSuchElementException("The element is not currently present in this min-priority queue.");
        }
        // Check if the new priority is strictly less than the current priority
        if (newPriority >= element.getPriority()) {
            throw new IllegalArgumentException("The new priority must be strictly less than the current priority.");
        }
        // Update the priority of the element
        element.setPriority(newPriority);
        // Restore the heap property by bubbling up the element
        heapifyUp(handle);
    }

    /**
     * Determines if this priority queue is empty.
     *
     * @return true if this priority queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Return the current size of this queue.
     *
     * @return the number of elements currently in this queue.
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * Erase all the elements from this min-priority queue. After this operation
     * this min-priority queue is empty.
     */
    public void clear() {
        this.heap.clear();
    }

    /**
     * Returns the index of the parent of the element at index i.
     *
     * @param i the index of the child element
     * @return the index of the parent element, or -1 if it is the root
     */
    private int parentIndex(int i) {
        if (i == 0) {
            return -1; // No parent for the root
        }
        return (i - 1) / 2;
    }

    /**
     * Returns the index of the left child of the element at index i.
     *
     * @param i the index of the parent element
     * @return the index of the left child element, or -1 if it has no left child
     */
    private int leftChildIndex(int i) {
        int left = 2 * i + 1;
        if (left < this.heap.size())
            return left;
        else
            return -1; // Return -1 if no left child
    }

    /**
     * Returns the index of the right child of the element at index i.
     *
     * @param i the index of the parent element
     * @return the index of the right child element, or -1 if it has no right child
     */
    private int rightChildIndex(int i) {
        int right = 2 * i + 2;
        if (right < this.heap.size())
            return right;
        else
            return -1; // Return -1 if no right child
    }

    /*
     * Method inserted for junit testing purposes only.
     */
    protected ArrayList<PriorityQueueElement> getBinaryHeap() {
        return this.heap;
    }

}
