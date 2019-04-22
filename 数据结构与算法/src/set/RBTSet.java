package set;

import tree.avl.AVLTree;
import tree.rbtree.RBTree;

/**
 * 基于红黑树实现的集合
 *
 * @author zhangy
 */
public class RBTSet<K extends Comparable<K>, V> implements Set<K> {


    private RBTree<K, V> tree;

    public RBTSet() {
        this.tree = new RBTree<>();
    }

    @Override
    public void add(K k) {
        tree.add(k, null);
    }

    @Override
    public int getSize() {
        return tree.getSize();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public boolean contains(K k) {
        return tree.contains(k);
    }

    @Override
    public void remove(K k) {
        tree.remove(k);
    }
}
