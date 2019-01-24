package ru.unn.agile.RB.Model;

public class RBNode<K extends Comparable<K>, V extends Comparable<V>>
        implements Comparable<RBNode<K, V>> {
    public COLOR getColor() {
        return color;
    }

    public enum COLOR { RED, BLACK }

    @Override
    public int compareTo(final RBNode<K, V> o) {
        int cmp = this.key.compareTo(o.getKey());
        if (cmp == 0) {
            return this.val.compareTo(o.getVal());
        }
        return cmp;
    }

    public RBNode(final K key, final V value, final COLOR color) {
        this.val = value;
        this.key = key;
        this.color = color;
        this.left = null;
        this.right = null;
    }

    public RBNode(final K key, final V value) {
        this(key, value, COLOR.RED);
    }

    public void setColor(final COLOR color) {
        this.color = color;
    }

    public V getVal() {
        return val;
    }

    public void setVal(final V val) {
        this.val = val;
    }

    public K getKey() {
        return key;
    }

    public void setKey(final K key) {
        this.key = key;
    }

    public RBNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(final RBNode<K, V> left) {
        this.left = left;
    }

    public RBNode<K, V> getRight() {
        return right;
    }

    public void setRight(final RBNode<K, V> right) {
        this.right = right;
    }

    public RBNode<K, V> getParent() {
        return parent;
    }

    public void setParent(final RBNode<K, V> parent) {
        this.parent = parent;
    }

    private COLOR color;
    private V val;
    private K key;
    private RBNode<K, V> left, right, parent;
}
