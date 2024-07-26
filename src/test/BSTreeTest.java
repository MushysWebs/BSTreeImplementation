package test; // Ensure this matches your package structure

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import treeImplementation.BSTree;
import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTreeTest {

    private BSTreeADT<Integer> bst;

    @BeforeEach
    public void setUp() {
        bst = new BSTree<>();
    }

    @Test
    public void testGetRoot() {
        assertNull(bst.getRoot());
        bst.add(10);
        assertNotNull(bst.getRoot());
        assertEquals(10, bst.getRoot().getElement());
    }

    @Test
    public void testGetHeight() {
        assertEquals(0, bst.getHeight());
        bst.add(10);
        assertEquals(1, bst.getHeight());
        bst.add(5);
        bst.add(15);
        assertEquals(2, bst.getHeight());
    }

    @Test
    public void testSize() {
        assertEquals(0, bst.size());
        bst.add(10);
        assertEquals(1, bst.size());
        bst.add(5);
        bst.add(15);
        assertEquals(3, bst.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(bst.isEmpty());
        bst.add(10);
        assertFalse(bst.isEmpty());
    }

    @Test
    public void testClear() {
        bst.add(10);
        bst.add(5);
        bst.add(15);
        assertFalse(bst.isEmpty());
        bst.clear();
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    @Test
    public void testContains() {
        assertThrows(NullPointerException.class, () -> bst.contains(null));
        assertFalse(bst.contains(10));
        bst.add(10);
        assertTrue(bst.contains(10));
        assertFalse(bst.contains(5));
    }

    @Test
    public void testSearch() {
        assertThrows(NullPointerException.class, () -> bst.search(null));
        assertNull(bst.search(10));
        bst.add(10);
        assertNotNull(bst.search(10));
        assertEquals(10, bst.search(10).getElement());
        assertNull(bst.search(5));
    }

    @Test
    public void testAdd() {
        assertThrows(NullPointerException.class, () -> bst.add(null));
        assertTrue(bst.add(10));
        assertTrue(bst.add(5));
        assertTrue(bst.add(15));
        assertEquals(3, bst.size());
        assertFalse(bst.add(10)); 
    }

    @Test
    public void testRemoveMin() {
        assertNull(bst.removeMin());
        bst.add(10);
        bst.add(5);
        bst.add(15);
        assertEquals(5, bst.removeMin().getElement());
        assertFalse(bst.contains(5));
        assertEquals(2, bst.size());
        assertEquals(10, bst.removeMin().getElement());
    }

    @Test
    public void testRemoveMax() {
        assertNull(bst.removeMax());
        bst.add(10);
        bst.add(5);
        bst.add(15);
        assertEquals(15, bst.removeMax().getElement());
        assertFalse(bst.contains(15));
        assertEquals(2, bst.size());
        assertEquals(10, bst.removeMax().getElement());
    }

    @Test
    public void testInOrderIterator() {
        bst.add(10);
        bst.add(5);
        bst.add(15);
        bst.add(2);
        bst.add(7);
        Iterator<Integer> itr = bst.inorderIterator();
        assertTrue(itr.hasNext());
        assertEquals(Integer.valueOf(2), itr.next());
        assertEquals(Integer.valueOf(5), itr.next());
        assertEquals(Integer.valueOf(7), itr.next());
        assertEquals(Integer.valueOf(10), itr.next());
        assertEquals(Integer.valueOf(15), itr.next());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testPreOrderIterator() {
        bst.add(10);
        bst.add(5);
        bst.add(15);
        bst.add(2);
        bst.add(7);
        Iterator<Integer> itr = bst.preorderIterator();
        assertTrue(itr.hasNext());
        assertEquals(Integer.valueOf(10), itr.next());
        assertEquals(Integer.valueOf(5), itr.next());
        assertEquals(Integer.valueOf(2), itr.next());
        assertEquals(Integer.valueOf(7), itr.next());
        assertEquals(Integer.valueOf(15), itr.next());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testPostOrderIterator() {
        bst.add(10);
        bst.add(5);
        bst.add(15);
        bst.add(2);
        bst.add(7);
        Iterator<Integer> itr = bst.postorderIterator();
        assertTrue(itr.hasNext());
        assertEquals(Integer.valueOf(2), itr.next());
        assertEquals(Integer.valueOf(7), itr.next());
        assertEquals(Integer.valueOf(5), itr.next());
        assertEquals(Integer.valueOf(15), itr.next());
        assertEquals(Integer.valueOf(10), itr.next());
        assertFalse(itr.hasNext());
    }
}
