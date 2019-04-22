package tree.bst;

import java.util.*;

/**
 * 二分搜索树
 *
 * @author zhangy
 */
public class BST<E extends Comparable<E>> {

    /**
     * 根节点
     */
    private Node root;

    /**
     * 树的大小
     */
    private int size;

    /**
     * 默认构造函数
     */
    public BST() {
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
     * 向二分搜索树中添加新的元素e
     *
     * @param e 要添加的元素
     */
    public void add(E e) {
        root = add(root, e);
    }

    /**
     * 向以node为根的二分搜索树中插入元素e，递归算法
     *
     * @param node 节点
     * @param e    元素
     * @return 返回插入新节点后二分搜索树的根
     */
    private Node add(Node node, E e) {

        if (node == null) {
            size++;
            return new Node(e);
        }

        if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e);
        }

        return node;
    }

    /**
     * 看二分搜索树中是否包含元素e
     *
     * @param e 元素
     * @return 元素
     */
    public boolean contains(E e) {
        return contains(root, e);
    }

    /**
     * 看以node为根的二分搜索树中是否包含元素e, 递归算法
     *
     * @param node 节点
     * @param e    元素e
     * @return true 包含
     */
    private boolean contains(Node node, E e) {

        if (node == null) {
            return false;
        }

        if (e.compareTo(node.e) == 0) {
            return true;
        } else if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    /**
     * 二分搜索树的前序遍历
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 前序遍历以node为根的二分搜索树, 递归算法
     *
     * @param node 根节点
     */
    private void preOrder(Node node) {
        if (node == null) {
            return;
        }

        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 二分搜索树的非递归前序遍历
     */
    public void preOderNR() {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.println(cur.e);

            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
    }

    /**
     * 二分搜索树的中序遍历
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 中序遍历以node为根的二分搜索树, 递归算法
     *
     * @param node 根节点
     */
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    /**
     * 二分搜索树的后序遍历
     */
    public void postOrder() {
        postOrder(root);
    }

    /**
     * 后序遍历以node为根的二分搜索树, 递归算法
     *
     * @param node 根节点
     */
    private void postOrder(Node node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    /**
     * 层序遍历（广度优先）
     */
    public void levelOrder() {
        if (root == null) {
            return;
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node cur = q.remove();
            System.out.println(cur.e);

            if (cur.left != null) {
                q.add(cur.left);
            }
            if (cur.right != null) {
                q.add(cur.right);
            }
        }
    }

    /**
     * 寻找二分搜索树的最小元素
     *
     * @return 最小元素
     */
    public E minimum() {
        if (size == 0) {
            throw new IllegalArgumentException("节点为空");
        }

        return minimum(root).e;
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
     * 寻找二分搜索树的最小元素 非递归
     *
     * @return 最小元素
     */
    public E minimumNR() {
        if (size == 0) {
            throw new IllegalArgumentException("节点为空");
        }

        Node cur = root.left;
        while (cur != null) {
            if (cur.left != null) {
                cur = cur.left;
            } else {
                break;
            }
        }
        return cur.e;
    }

    /**
     * 寻找二分搜索树的最大元素
     *
     * @return 最大元素
     */
    public E maximum() {
        if (size == 0) {
            throw new IllegalArgumentException("节点为空");
        }

        return maximum(root).e;
    }

    /**
     * 从二分搜索树中删除最小值所在节点, 返回最大值
     *
     * @param node 根节点
     * @return 最大值
     */
    private Node maximum(Node node) {
        if (node.right == null) {
            return node;
        }

        return maximum(node.right);
    }

    /**
     * 寻找二分搜索树的最大元素 非递归
     *
     * @return 最大元素
     */
    public E maximumNR() {
        if (size == 0) {
            throw new IllegalArgumentException("节点为空");
        }

        Node cur = root.right;
        while (cur != null) {
            if (cur.right != null) {
                cur = cur.right;
            } else {
                break;
            }
        }

        return cur.e;
    }

    /**
     * 从二分搜索树中删除最小值所在节点, 返回最小值
     *
     * @return 最小值
     */
    public E removeMin() {
        E ret = minimum();
        root = removeMin(root);
        return ret;
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

    /**
     * 从二分搜索树中删除最大值所在节点, 返回最大值
     *
     * @return 最大值
     */
    public E removeMax() {
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    /**
     * 删除掉以node为根的二分搜索树中的最大节点
     *
     * @param node 根节点
     * @return 最大节点
     */
    private Node removeMax(Node node) {

        if (node.right == null) {
            Node leftNode = node.left;
            //删除当前node节点
            node.left = null;
            size--;
            return leftNode;
        }

        node.right = removeMin(node.right);
        return node;
    }

    /**
     * 从二分搜索树中删除元素为e的节点
     *
     * @param e 要删除的元素e
     */
    public void remove(E e) {
        root = remove(root, e);
    }

    /**
     * 删除掉以node为根的二分搜索树中值为e的节点, 递归算法
     *
     * @param node 返回删除节点后新的二分搜索树的根
     * @param e    要删除的元素e
     * @return
     */
    private Node remove(Node node, E e) {

        if (node == null) {
            return null;
        }

        //在node的左子树去查找
        if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        } else if (e.compareTo(node.e) > 0) {
            //在node的左子树去查找
            node.right = remove(node.right, e);
            return node;
        } else {
            //找到了值为e的Node节点
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

            //让删除的节点左右都指向null
            node.left = node.right = null;
            return successor;
        }
    }

    /**
     * 测试函数
     *
     * @param args
     */
    public static void main(String[] args) {

//        BST<Integer> tree.bst = new BST<>();
//        int[] nums = {5, 3, 6, 8, 4, 2};
//        for (int num : nums)
//            tree.bst.add(num);
//
//        /////////////////
//        //      5      //
//        //    /   \    //
//        //   3    6    //
//        //  / \    \   //
//        // 2  4     8  //
//        /////////////////
//
//        tree.bst.preOrder();
//        System.out.println();
//
//        tree.bst.inOrder();
//        System.out.println();
//
//        tree.bst.postOrder();
//        System.out.println();
//
//        tree.bst.levelOrder();
//        System.out.println();
//
//        System.out.println(tree.bst.minimum());
//        System.out.println(tree.bst.minimumNR());
//
//        System.out.println(tree.bst.maximum());
//        System.out.println(tree.bst.maximumNR());


        BST<Integer> bst = new BST<>();
        Random random = new Random();

        int n = 1000;

        // test removeMin
        for (int i = 0; i < n; i++)
            bst.add(random.nextInt(10000));

        ArrayList<Integer> nums = new ArrayList<>();
        while (!bst.isEmpty())
            nums.add(bst.removeMin());

        System.out.println(nums);
        for (int i = 1; i < nums.size(); i++)
            if (nums.get(i - 1) > nums.get(i))
                throw new IllegalArgumentException("Error!");
        System.out.println("removeMin test completed.");


        // test removeMax
        for (int i = 0; i < n; i++)
            bst.add(random.nextInt(10000));

        nums = new ArrayList<>();
        while (!bst.isEmpty())
            nums.add(bst.removeMax());

        System.out.println(nums);
        for (int i = 1; i < nums.size(); i++)
            if (nums.get(i - 1) < nums.get(i))
                throw new IllegalArgumentException("Error!");
        System.out.println("removeMax test completed.");
    }

    /**
     * 节点类
     */
    private class Node {
        /**
         * 元素
         */
        public E e;
        /**
         * 左子树
         */
        public Node left;
        /**
         * 右子树
         */
        public Node right;

        public Node(E e) {
            this.e = e;
        }

    }
}
