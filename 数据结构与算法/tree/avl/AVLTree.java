package tree.avl;

import array.Array;

import javax.crypto.Mac;
import java.util.ArrayList;

/**
 * AVL树
 *
 * @author zhangy
 */
public class AVLTree<K extends Comparable<K>, V> {

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
         * 左右子树节点
         */
        public Node left, right;

        /**
         * 每个节点的高度
         */
        public int height;

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
            height = 1;
        }
    }

    /**
     * 根节点
     */
    private Node root;

    /**
     * AVL树的大小
     */
    private int size;

    /**
     * 默认构造函数
     */
    public AVLTree() {
        root = null;
        size = 0;
    }

    /**
     * 获取树的大小
     *
     * @return 大小
     */
    public int getSize() {
        return size;
    }

    /**
     * 是否为空
     *
     * @return true 空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断该二叉树是否是一棵二分搜索树
     *
     * @return true 是BST
     */
    public boolean isBST() {
        //使用中序遍历之后 数据从小大小排序好了，所以只需要判断 前一个元素是否比当前元素大
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断该二叉树是否是一棵平衡二叉树
     *
     * @return true 是平衡二叉树
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    /**
     * 判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
     *
     * @param node 根节点
     * @return true 是平衡二叉树
     */
    private boolean isBalanced(Node node) {

        if (node == null) {
            return true;
        }

        // 平衡因子大于1 就不平衡了
        if (Math.abs(getBalanceFactor(node)) > 1) {
            return false;
        }

        return isBalanced(node.left) && isBalanced(node.right);
    }

    /**
     * 对节点y进行向右旋转操作，返回旋转后新的根节点x
     *         y                              x
     *       / \                           /   \
     *      x   T4     向右旋转 (y)        z     y
     *     / \       - - - - - - - ->    / \   / \
     *    z   T3                       T1  T2 T3 T4
     *   / \
     * T1   T2
     *
     * @param y 要旋转的节点
     * @return 旋转之后的新节点
     */
    private Node rightRotate(Node y) {

        Node x = y.left;
        Node t3 = x.right;

        // 向右旋转过程
        x.right = y;
        y.left = t3;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    /**
     * 对节点y进行向左旋转操作，返回旋转后新的根节点x
     *     y                             x
     *  /  \                          /   \
     * T1   x      向左旋转 (y)       y     z
     *     / \   - - - - - - - ->   / \   / \
     *   T2  z                     T1 T2 T3 T4
     *      / \
     *     T3 T4
     *
     * @param y 要旋转的节点
     * @return 旋转之后的新节点
     */
    private Node leftRotate(Node y) {

        Node x = y.right;
        Node t2 = x.left;

        // 向左旋转过程
        x.left = y;
        y.right = t2;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }


    /**
     * 二叉树的中序遍历
     *
     * @param node 节点
     * @param keys 集合
     */
    private void inOrder(Node node, ArrayList<K> keys) {

        if (node == null)
            return;

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    /**
     * 获得节点node的高度
     *
     * @param node 节点
     * @return 高度
     */
    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    /**
     * 获得节点node的平衡因子
     *
     * @param node 节点
     * @return 平衡因子
     */
    private int getBalanceFactor(Node node) {
        return node == null ? 0 : (getHeight(node.left) - getHeight(node.right));
    }

    /**
     * 向AVL树中添加新的元素(key, value)
     *
     * @param key   键
     * @param value 值
     */
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    /**
     * 向以node为根的二分搜索树中插入元素(key, value)，递归算法
     * 返回插入新节点后二分搜索树的根
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

        // 更新节点的高度height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // 计算平衡因子
        int balanceFactor = getBalanceFactor(node);
        // 平衡维护

        // LL 需要 右旋转
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // RR 需要 左旋转
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // LR 需要2次操作 ，先左旋转 变为LL，然后再 右旋转
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL 需要2次操作 ，先右旋转 变为RR，然后再 左旋转
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
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

    /**
     * AVL树种是否包含键为key的节点
     *
     * @param key 键
     * @return true 包含
     */
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    /**
     * 获取键为key的节点的值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    /**
     * 给键为key的节点赋新值newValue
     *
     * @param key      键
     * @param newValue 新值
     */
    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node != null) {
            node.value = newValue;
        }
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
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 从AVL树中删除键为key的节点
     *
     * @param key 值
     * @return 要删除节点的值
     */
    public V remove(K key) {

        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    /**
     * 删除以node为根的键为key的节点(递归写法)
     *
     * @param node 根节点
     * @param key  键
     * @return 要删除的节点
     */
    private Node remove(Node node, K key) {

        if (node == null) {
            return null;
        }

        Node retNode;

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {

            // 待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            }
            // 待删除节点右子树为空的情况
            else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            }
            // 待删除节点左右子树均不为空的情况
            else {
                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                Node successor = minimum(node.right);
                // 用这个节点顶替待删除节点的位置
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;

                node.left = node.right = null;

                retNode = successor;
            }

        }

        if (retNode == null) {
            return null;
        }

        // 更新height
        retNode.height = Math.max(getHeight(retNode.left), getHeight(retNode.right)) + 1;
        // 计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

        // 平衡维护
        // LL 需要 右旋转
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0) {
            return rightRotate(retNode);
        }

        // RR 需要 左旋转
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0) {
            return leftRotate(retNode);
        }

        // LR 需要2次操作 ，先左旋转 变为LL，然后再 右旋转
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        // RL 需要2次操作 ，先右旋转 变为RR，然后再 左旋转
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;

    }
}
