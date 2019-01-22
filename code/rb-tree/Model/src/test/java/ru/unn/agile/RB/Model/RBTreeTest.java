package ru.unn.agile.RB.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class RBTreeTest {
    @Test
    public void canCreateRBTree() {
        RBTree<String, Integer> tree = new RBTree<>();

        assertNotNull(tree);
    }

    @Test
    public void canInsertNewNode() {
        RBTree<String, Integer> tree = new RBTree<>();

        tree.insert("B", 1);

        assertNotNull(tree.find("B"));
    }

    @Test
    public void canInsertNewNodes() {
        RBTree<String, Integer> tree = new RBTree<>();

        tree.insert("A", 1);
        tree.insert("B", 1);
        tree.insert("C", 1);
        tree.insert("D", 1);
        tree.insert("E", 1);
        tree.insert("F", 1);

        assertNotNull(tree.find("D"));
    }

    @Test
    public void canInsertNodesWithEqualKeys() {
        RBTree<String, Integer> tree = new RBTree<>();

        tree.insert("A", 1);
        tree.insert("A", 2);
        tree.insert("B", 2);
        tree.insert("B", 3);

        assertNotNull(tree.find("A"));
        assertEquals(new Integer(2), tree.find("A").getVal());
    }

    @Test
    public void canInsertNodesWithEqualValues() {
        RBTree<Integer, Integer> tree = new RBTree<>();

        tree.insert(10, 2);
        tree.insert(9, 2);
        tree.insert(8, 2);
        tree.insert(7, 2);
        tree.insert(6, 2);
        tree.insert(5, 2);
        tree.insert(4, 2);
        tree.insert(3, 2);
        tree.insert(2, 2);
        tree.insert(1, 2);

        assertNotNull(tree.find(1));
        assertEquals(new Integer(2), tree.find(1).getVal());
    }

    @Test
    public void canNotFindNodeWithNotExistingKey() {
        RBTree<String, Integer> tree = new RBTree<>();

        tree.insert("A", 1);
        tree.insert("B", 2);
        tree.insert("C", 3);

        assertNull(tree.find("D"));
    }

}
