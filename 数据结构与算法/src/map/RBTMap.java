package map;

import tree.rbtree.RBTree;

/**
 * 基于红黑树实现的映射
 *
 * @param <K> 键
 * @param <V> 值
 * @author zhangy
 */
public class RBTMap<K extends Comparable<K>, V> implements Map<K, V> {

    private RBTree<K, V> tree;

    public RBTMap() {
        tree = new RBTree<>();
    }

    @Override
    public void add(K key, V value) {
        tree.add(key, value);
    }

    @Override
    public V remove(K key) {
        return tree.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return tree.contains(key);
    }

    @Override
    public V get(K key) {
        return tree.get(key);
    }

    @Override
    public void set(K key, V value) {
        tree.set(key, value);
    }

    @Override
    public int getSize() {
        return tree.getSize();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }
}
