package map;

/**
 * @param <K> 键
 * @param <V> 值
 * @author zhangy
 * 基于BST实现的映射
 */
public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {

    /**
     * 大小
     */
    private int size;

    /**
     * 根节点
     */
    private Node root;

    /**
     * 构造函数
     */
    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回以node为根节点的二分搜索树中，key所在的节点
     *
     * @param node 节点
     * @param key  键
     * @return key所在的节点
     */
    private Node getNode(Node node, K key) {

        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return getNode(node.right, key);
        } else {
            return node;
        }
    }

    @Override
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    /**
     * 向以node为根的二分搜索树中插入元素(key, value)，递归算法
     *
     * @param node  根节点
     * @param key   键
     * @param value 值
     * @return 节点
     */
    private Node add(Node node, K key, V value) {

        if (node == null) {
            size++;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else {
            node.value = value;
        }

        return node;
    }

    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node != null ? node.value : null;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException("没有找到");
        }

        node.value = value;
    }

    /**
     * 返回以node为根的二分搜索树的最小值所在的节点
     *
     * @param node 根节点
     * @return 最小值所在的节点
     */
    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }

        return minimum(node.left);
    }

    /**
     * 删除掉以node为根的二分搜索树中的最小节点
     *
     * @param node 根节点
     * @return 最小节点
     */
    private Node removeMin(Node node) {

        if (node.left == null) {
            Node rightNode = node.right;
            //删除当前node节点
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
        }
        return null;
    }

    /**
     * 删除元素 递归
     *
     * @param node 节点
     * @param key  键
     * @return 节点
     */
    private Node remove(Node node, K key) {

        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {
            // 待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况
            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            Node successor = minimum(node.right);
            // 用这个节点顶替待删除节点的位置
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }

    /**
     * 节点类
     */
    private class Node {

        /**
         * 键
         */
        public K key;
        /**
         * 值
         */
        public V value;
        /**
         * 左子树
         */
        public Node left;
        /**
         * 右子树
         */
        public Node right;

        /**
         * 构造函数
         *
         * @param key   键
         * @param value 值
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }
}
