package ru.unn.agile.RB.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class RBNodeTest {
    @Test
    public void canCreateRBNode() {
        RBNode<String, Integer> node = new RBNode<>("K", 4, RBNode.COLOR.BLACK);

        assertNotNull(node);
    }

    @Test
    public void areEqualRBNodes() {
        RBNode<String, Integer> node1 = new RBNode<>("A", 1);
        RBNode<String, Integer> node2 = new RBNode<>("A", 1);

        assertEquals(0, node1.compareTo(node2));
    }

    @Test
    public void areNotEqualRBNodesWithEqualKey() {
        RBNode<String, Integer> node1 = new RBNode<>("A", 1);
        RBNode<String, Integer> node2 = new RBNode<>("A", 2);

        assertNotEquals(0, node1.compareTo(node2));
    }

    @Test
    public void areNotEqualRBNodesWithEqualValue() {
        RBNode<String, Integer> node1 = new RBNode<>("A", 1);
        RBNode<String, Integer> node2 = new RBNode<>("B", 1);

        assertNotEquals(0, node1.compareTo(node2));
    }
}
