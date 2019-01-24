package ru.unn.agile.RB.Model;

public class RBTree<K extends Comparable<K>, V extends Comparable<V>> {

    private RBNode<K, V> root = null;

    public void insert(final K key, final V value) {
        if (root == null) {
            root = new RBNode<>(key, value, RBNode.COLOR.BLACK);
            return;
        }

        RBNode<K, V> parent = root;
        RBNode<K, V> current = root;

        while (current != null) {
            parent = current;
            int cmp = key.compareTo(current.getKey());
            if (cmp < 0) {
                current = current.getLeft();
            } else if (cmp > 0) {
                current = current.getRight();
            } else {
                current.setVal(value);
                return;
            }
        }

        RBNode<K, V> node = new RBNode<>(key, value);
        node.setParent(parent);
        int cmp = key.compareTo(parent.getKey());
        if (cmp < 0) {
            parent.setLeft(node);
        } else if (cmp > 0) {
            parent.setRight(node);
        }

        fixTree(node);
    }

    private void fixTree(final RBNode<K, V> node) {
        if ((node.getParent() == null) || (node.getParent().getColor() == RBNode.COLOR.BLACK)) {
            return;
        }

        RBNode<K, V> uncle = getUncle(node);
        RBNode<K, V> parent = node.getParent();

        if ((uncle != null) && (uncle.getColor() == RBNode.COLOR.RED)) {
            parent.setColor(RBNode.COLOR.BLACK);
            parent.getParent().setColor(RBNode.COLOR.RED);
            uncle.setColor(RBNode.COLOR.BLACK);
            fixTree(parent.getParent());
        } else if (parentInLeftSubTree(node)) {
            if (isRightChild(node)) {
                rotateLeft(node);
            }
            parent.setColor(RBNode.COLOR.BLACK);
            parent.getParent().setColor(RBNode.COLOR.RED);
            rotateRight(parent);
        } else {
            if (isLeftChild(node)) {
                rotateRight(node);
            }
            parent.setColor(RBNode.COLOR.BLACK);
            parent.getParent().setColor(RBNode.COLOR.RED);
            rotateLeft(parent);
        }

        root.setColor(RBNode.COLOR.BLACK);

    }

    private void rotateRight(final RBNode<K, V> node) {
        RBNode<K, V> parent = node.getParent();
        RBNode<K, V> rightChild = node.getRight();

        swapNodeWithParent(node);

        node.setRight(parent);
        parent.setLeft(rightChild);
        if (rightChild != null) {
            rightChild.setParent(parent);
        }
    }

    private void rotateLeft(final RBNode<K, V> node) {
        RBNode<K, V> parent = node.getParent();
        RBNode<K, V> leftChild = node.getLeft();

        swapNodeWithParent(node);

        node.setLeft(parent);
        parent.setRight(leftChild);
        if (leftChild != null) {
            leftChild.setParent(parent);
        }
    }

    private void swapNodeWithParent(final RBNode<K, V> node) {
        RBNode<K, V> parent = node.getParent();
        RBNode<K, V> grandParent = parent.getParent();
        node.setParent(grandParent);

        if (grandParent != null) {
            if (isLeftChild(parent)) {
                grandParent.setLeft(node);
            } else {
                grandParent.setRight(node);
            }
        } else {
            this.root = node;
        }
        parent.setParent(node);

    }

    private boolean isLeftChild(final RBNode<K, V> node) {
        return node.getParent().getLeft() == node;
    }

    private boolean isRightChild(final RBNode<K, V> node) {
        return node.getParent().getRight() == node;
    }

    private RBNode<K, V> getUncle(final RBNode<K, V> node) {
        if ((node.getParent() == null) || (node.getParent().getParent() == null)) {
            return null;
        }

        RBNode<K, V> parent = node.getParent();
        RBNode<K, V> grandParent = parent.getParent();

        return (parent == grandParent.getLeft()) ? grandParent.getRight() : grandParent.getLeft();
    }

    private boolean parentInLeftSubTree(final RBNode<K, V> node) {
        return (node.getParent() == node.getParent().getParent().getLeft());
    }

    public RBNode<K, V> find(final K key) {
        RBNode<K, V> node = root;
        while (node != null) {
            int cmp = key.compareTo(node.getKey());
            if (cmp < 0) {
                node = node.getLeft();
            } else if (cmp > 0) {
                node = node.getRight();
            } else {
                return node;
            }
        }
        return null;
    }
}
