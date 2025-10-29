package test.java.it.unicam.cs.asdl2425;

import it.unicam.cs.asdl2425.BinaryHeapMinPriorityQueue;
import it.unicam.cs.asdl2425.GraphNode;
import it.unicam.cs.asdl2425.PriorityQueueElement;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
public class BinaryHeapMinPriorityQueueTest {

    @Test
    public final void testBinaryHeapMinPriorityQueue() {
    	BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement node0 = new GraphNode<>(13);
        PriorityQueueElement node1 = new GraphNode<>(7);
        PriorityQueueElement node2 = new GraphNode<>(8);
        PriorityQueueElement node3 = new GraphNode<>(9);
        PriorityQueueElement node4 = new GraphNode<>(20);
        PriorityQueueElement node5 = new GraphNode<>(21);
        PriorityQueueElement node6 = new GraphNode<>(44);
        PriorityQueueElement node7 = new GraphNode<>(1);
        assertTrue(heap.isEmpty());
        node0.setPriority(13);
        heap.insert(node0);
        assertFalse(heap.isEmpty());
        node1.setPriority(7);
        heap.insert(node1);
        node2.setPriority(8);
        heap.insert(node2);
        node3.setPriority(9);
        heap.insert(node3);
        node4.setPriority(20);
        heap.insert(node4);
        node5.setPriority(21);
        heap.insert(node5);
        node6.setPriority(44);
        heap.insert(node6);
        node7.setPriority(1);
        heap.insert(node7);
        heap.clear();
        assertTrue(heap.isEmpty());
    }

    @Test
    public final void testInsert() {
    	 BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
         PriorityQueueElement node0 = new GraphNode<>(13);
         node0.setPriority(13);
         PriorityQueueElement node1 = new GraphNode<>(7);
         node1.setPriority(7);
         PriorityQueueElement node2 = new GraphNode<>(8);
         node2.setPriority(8);

         // Check delle Exceptions
         assertThrows(NullPointerException.class, ()-> heap.insert(null));
         heap.insert(node0);
         assertEquals(1, heap.size());
         heap.insert(node1);
         assertEquals(2, heap.size());
         heap.insert(node2);
         assertEquals(3, heap.size());
    }

    @Test
    public final void testMinimum() {
    	 BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
         PriorityQueueElement node0 = new GraphNode<>(13);
         node0.setPriority(13);
         PriorityQueueElement node1 = new GraphNode<>(7);
         node1.setPriority(7);
         PriorityQueueElement node2 = new GraphNode<>(8);
         node2.setPriority(8);
         PriorityQueueElement node3 = new GraphNode<>(9);
         node3.setPriority(9);
         PriorityQueueElement node4 = new GraphNode<>(20);
         node4.setPriority(20);
         PriorityQueueElement node5 = new GraphNode<>(21);
         node5.setPriority(21);
         PriorityQueueElement node6 = new GraphNode<>(44);
         node6.setPriority(44);
         PriorityQueueElement node7 = new GraphNode<>(1);
         node7.setPriority(1);

         heap.insert(node0);
         assertEquals(node0, heap.minimum());
         heap.insert(node1);
         assertEquals(node1, heap.minimum());
         heap.insert(node2);
         assertEquals(node1, heap.minimum());
         heap.insert(node3);
         assertEquals(node1, heap.minimum());
         heap.insert(node4);
         assertEquals(node1, heap.minimum());
         heap.insert(node5);
         assertEquals(node1, heap.minimum());
         heap.insert(node6);
         assertEquals(node1, heap.minimum());
         heap.insert(node7);
         assertEquals(node7, heap.minimum());
    }

    @Test
    public final void testExtractMinimum() {
    	 BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
         PriorityQueueElement node0 = new GraphNode<>(13);
         node0.setPriority(13);
         PriorityQueueElement node1 = new GraphNode<>(7);
         node1.setPriority(7);
         PriorityQueueElement node2 = new GraphNode<>(8);
         node2.setPriority(8);
         PriorityQueueElement node3 = new GraphNode<>(9);
         node3.setPriority(9);
         PriorityQueueElement node4 = new GraphNode<>(20);
         node4.setPriority(20);
         PriorityQueueElement node5 = new GraphNode<>(21);
         node5.setPriority(21);
         PriorityQueueElement node6 = new GraphNode<>(44);
         node6.setPriority(44);
         PriorityQueueElement node7 = new GraphNode<>(1);
         node7.setPriority(1);

         heap.insert(node0);
         heap.insert(node1);
         heap.insert(node2);
         heap.insert(node3);
         heap.insert(node4);
         heap.insert(node5);
         heap.insert(node6);
         heap.insert(node7);
         assertEquals(node7, heap.extractMinimum());
         assertEquals(node1, heap.extractMinimum());
         assertEquals(node2, heap.extractMinimum());
         assertEquals(node3, heap.extractMinimum());
         assertEquals(node0, heap.extractMinimum());
         assertEquals(node4, heap.extractMinimum());
         assertEquals(node5, heap.extractMinimum());
         assertEquals(node6, heap.extractMinimum());
    }

    @Test
    public final void testDecreasePriority() {
    	BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement node0 = new GraphNode<>(13);
        node0.setPriority(13);
        heap.insert(node0);
        PriorityQueueElement node1 = new GraphNode<>(7);
        node1.setPriority(7);
        heap.insert(node1);
        PriorityQueueElement node2 = new GraphNode<>(8);
        node2.setPriority(8);
        heap.insert(node2);
        PriorityQueueElement node3 = new GraphNode<>(9);
        node3.setPriority(9);
        heap.insert(node3);
        PriorityQueueElement node4 = new GraphNode<>(20);
        node4.setPriority(20);
        heap.insert(node4);
        PriorityQueueElement node5 = new GraphNode<>(21);
        node5.setPriority(21);
        heap.insert(node5);
        PriorityQueueElement node6 = new GraphNode<>(44);
        node6.setPriority(44);
        heap.insert(node6);
        PriorityQueueElement node7 = new GraphNode<>(1);
        node7.setPriority(1);
        heap.insert(node7);

        // Check delle Exceptions
        @SuppressWarnings("unused")
        PriorityQueueElement nodeX = new GraphNode<>(111);
        
        assertThrows(IllegalArgumentException.class, ()-> heap.decreasePriority(node0, 14));

        // Essendo che decreasePriority non ha ritorno non posso fare confronti
    }

    @Test
    public final void testIsEmpty() {
    	BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        assertTrue(heap.isEmpty());

        PriorityQueueElement node0 = new GraphNode<>(13);
        node0.setPriority(13);
        heap.insert(node0);
        PriorityQueueElement node1 = new GraphNode<>(7);
        node1.setPriority(7);
        heap.insert(node1);
        PriorityQueueElement node2 = new GraphNode<>(8);
        node2.setPriority(8);
        heap.insert(node2);

        assertFalse(heap.isEmpty());
    }

    @Test
    public final void testSize() {
    	BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement node0 = new GraphNode<>(13);
        node0.setPriority(13);
        heap.insert(node0);
        assertEquals(1, heap.size());
        PriorityQueueElement node1 = new GraphNode<>(7);
        node1.setPriority(7);
        heap.insert(node1);
        assertEquals(2, heap.size());
        PriorityQueueElement node2 = new GraphNode<>(8);
        node2.setPriority(8);
        heap.insert(node2);
        assertEquals(3, heap.size());
        PriorityQueueElement node3 = new GraphNode<>(9);
        node3.setPriority(9);
        heap.insert(node3);
        assertEquals(4, heap.size());
        PriorityQueueElement node4 = new GraphNode<>(20);
        node4.setPriority(20);
        heap.insert(node4);
        assertEquals(5, heap.size());
        PriorityQueueElement node5 = new GraphNode<>(21);
        node5.setPriority(21);
        heap.insert(node5);
        assertEquals(6, heap.size());
        PriorityQueueElement node6 = new GraphNode<>(44);
        node6.setPriority(44);
        heap.insert(node6);
        assertEquals(7, heap.size());
        PriorityQueueElement node7 = new GraphNode<>(1);
        node7.setPriority(1);
        heap.insert(node7);
        assertEquals(8, heap.size());
        heap.extractMinimum();
        assertEquals(7, heap.size());
        heap.extractMinimum();
        assertEquals(6, heap.size());
        heap.extractMinimum();
        assertEquals(5, heap.size());
        heap.extractMinimum();
        assertEquals(4, heap.size());
        heap.extractMinimum();
        assertEquals(3, heap.size());
        heap.extractMinimum();
        assertEquals(2, heap.size());
        heap.extractMinimum();
        assertEquals(1, heap.size());
        heap.extractMinimum();
        assertEquals(0, heap.size());
    }

    @Test
    public final void testClear() {
    	 BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();

         PriorityQueueElement node0 = new GraphNode<>(13);
         node0.setPriority(13);
         heap.insert(node0);
         PriorityQueueElement node1 = new GraphNode<>(7);
         node1.setPriority(7);
         heap.insert(node1);
         PriorityQueueElement node2 = new GraphNode<>(8);
         node2.setPriority(8);
         heap.insert(node2);

         assertFalse(heap.isEmpty());
         heap.clear();
         assertTrue(heap.isEmpty());
    }
    
    @Test
    public final void testLargeDataSet() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();

        int numElements = 10000;
        PriorityQueueElement[] elements = new PriorityQueueElement[numElements];

        // Inserimento di elementi con priorità crescente
        for (int i = 0; i < numElements; i++) {
            elements[i] = new GraphNode<>(i);
            elements[i].setPriority(i);
            heap.insert(elements[i]);
        }

        // Verifica dell'ordine di estrazione
        for (int i = 0; i < numElements; i++) {
            assertEquals(elements[i], heap.extractMinimum());
        }

        // Verifica che la coda sia vuota
        assertTrue(heap.isEmpty());
    }
    
    @Test
    public final void testStability() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();

        PriorityQueueElement node1 = new GraphNode<>("A");
        node1.setPriority(10);
        PriorityQueueElement node2 = new GraphNode<>("B");
        node2.setPriority(10);
        PriorityQueueElement node3 = new GraphNode<>("C");
        node3.setPriority(10);

        heap.insert(node1);
        heap.insert(node2);
        heap.insert(node3);

        // Verifica che gli elementi vengano estratti nell'ordine di inserimento
        assertEquals(node1, heap.extractMinimum());
        assertEquals(node2, heap.extractMinimum());
        assertEquals(node3, heap.extractMinimum());
    }
    
    @Test
    public final void testSingleElementHeap() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        PriorityQueueElement node = new GraphNode<>("SingleNode");
        node.setPriority(50);

        heap.insert(node);

        // Verifica che l'unico elemento sia il minimo
        assertEquals(node, heap.minimum());
        assertEquals(node, heap.extractMinimum());

        // Verifica che la coda sia vuota dopo l'estrazione
        assertTrue(heap.isEmpty());
    }
    
    @Test
    public final void testReinsertAfterExtraction() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        
        PriorityQueueElement node1 = new GraphNode<>("A");
        node1.setPriority(20);
        PriorityQueueElement node2 = new GraphNode<>("B");
        node2.setPriority(10);
        PriorityQueueElement node3 = new GraphNode<>("C");
        node3.setPriority(30);

        heap.insert(node1);
        heap.insert(node2);
        heap.insert(node3);

        // Estrarre il minimo (node2)
        assertEquals(node2, heap.extractMinimum());

        // Reinserire un nuovo nodo con priorità più bassa
        PriorityQueueElement node4 = new GraphNode<>("D");
        node4.setPriority(5);
        heap.insert(node4);

        // Verifica dell'ordine
        assertEquals(node4, heap.extractMinimum());
        assertEquals(node1, heap.extractMinimum());
        assertEquals(node3, heap.extractMinimum());
    }
}
