package tree.rbtree;

/**
 * 红黑树
 *
 * @param <K> 键
 * @param <V> 值
 * @author zhangy
 */
public class RBTree<K extends Comparable<K>, V> {

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
    public RBTree() {
        root = null;
        size = 0;
    }

    /**
     * 获取红黑树的大小
     *
     * @return 大小
     */
    public int getSize() {
        return size;
    }

    /**
     * 红黑树是否为空
     *
     * @return true 空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断节点node的颜色
     *
     * @param node 节点
     * @return true 红
     */
    private boolean isRed(Node node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * 对节点node进行向左旋转操作，返回旋转后新的根节点x
     *   node                           x
     *  /  \                          /   \
     * T1   x      向左旋转 (y)      node  T3
     *     / \   - - - - - - - ->   /  \
     *   T2  T3                   T1   T2
     *
     *
     * @param node 要旋转的节点
     * @return 旋转之后的新节点
     */
    private Node leftRotate(Node node) {

        Node x = node.right;

        // 左旋转
        node.right = x.left;
        x.left = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    /**
     * 对节点node进行向左旋转操作，返回旋转后新的根节点x
     *     node                   x
     *    /   \     右旋转       /  \
     *   x    T2   ------->   y   node
     *  / \                       /  \
     * y  T1                     T1  T2
     *
     *
     * @param node 要旋转的节点
     * @return 旋转之后的新节点
     */
    private Node rightRotate(Node node){

        Node x = node.left;

        // 右旋转
        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    /**
     * 颜色翻转
     *
     * @param node 节点
     */
    private void flipColors(Node node) {

        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    /**
     * 返回以node为根节点的红黑树中，key所在的节点
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

    /**
     * 向红黑树中添加元素（key,value）
     *
     * @param key
     * @param value
     */
    public void add(K key, V value) {
        root = add(root, key, value);
        // 最终根节点为黑色节点
        root.color = BLACK;
    }

    /**
     * 向以node为根的红黑树中插入元素(key, value)，递归算法
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

        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }

        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }

        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    /**
     * 红黑树中是否包含某个节点
     *
     * @param key 键
     * @return true 包含
     */
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }


    /**
     * 获取红黑树中键为key的值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        Node node = getNode(root, key);
        return node != null ? node.value : null;
    }

    /**
     * 向红黑树中修改键为key节点的值
     *
     * @param key   键
     * @param value 值
     */
    public void set(K key, V value) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException("没有找到");
        }

        node.value = value;
    }

    /**
     * 返回以node为根的红黑树的最小值所在的节点
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
     * 删除掉以node为根的红黑树中的最小节点
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

    /**
     * 从红黑树中删除键为key的节点
     *
     * @param key 键
     * @return 值
     */
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
     * 红色
     */
    private static final boolean RED = true;
    /**
     * 黑色
     */
    private static final boolean BLACK = false;

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
         * 红黑的标志
         */
        public boolean color;

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
            color = RED;
        }
    }
}
