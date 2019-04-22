package tree.segment;

/**
 * 线段树
 *
 * @param <E> 泛型
 * @author zhangy
 */
public class SegmentTree<E> {

    /**
     * 数据数组
     */
    private E[] data;

    /**
     * 线段树
     */
    private E[] tree;

    /**
     * 融合器
     */
    private Merger<E> merger;

    /**
     * 构造函数
     *
     * @param arr    元素数组
     * @param merger 融合器
     */
    public SegmentTree(E[] arr, Merger<E> merger) {

        this.merger = merger;

        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }

        tree = (E[]) new Object[4 * arr.length];
        buildSegmentTree(0, 0, arr.length - 1);
    }

    /**
     * 在treeIndex的位置创建表示区间[l...r]的线段树
     *
     * @param treeIndex 开始位置
     * @param l         左区间
     * @param r         右区间
     */
    private void buildSegmentTree(int treeIndex, int l, int r) {

        // 当左右索引相等
        if (l == r) {
            tree[treeIndex] = data[l];
            return;
        }

        // 左区间索引
        int leftTreeIndex = leftChild(treeIndex);
        // 右区间索引
        int rightTreeIndex = rightChild(treeIndex);
        // 中间索引
        int mid = l + (r - l) / 2;

        buildSegmentTree(leftTreeIndex, l, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    /**
     * 获取线段树大小
     *
     * @return 大小
     */
    public int getSize() {
        return data.length;
    }

    /**
     * 获取索引为index的值
     *
     * @param index 索引
     * @return 值
     */
    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("索引越界");
        }
        return data[index];
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
     *
     * @param index 索引
     * @return 索引
     */
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
     *
     * @param index 索引
     * @return 索引
     */
    private int rightChild(int index) {
        return 2 * index + 2;
    }

    /**
     * 返回区间[queryL, queryR]的值
     *
     * @param queryL 左边界索引
     * @param queryR 右边界索引
     * @return 值
     */
    public E query(int queryL, int queryR) {

        if (queryL < 0 || queryL >= data.length || queryR < 0 ||
                queryR >= data.length || queryL > queryR) {
            throw new IllegalArgumentException("索引越界");
        }

        return query(0, 0, data.length - 1, queryL, queryR);
    }

    /**
     * 在以treeIndex为根的线段树中[l...r]的范围里，搜索区间[queryL...queryR]的值
     * 也可以把treeIndex,l,r 封装为一个节点类，表示当前节点的信息
     *
     * @param treeIndex 根节点索引
     * @param l         左区间索引
     * @param r         右区间索引
     * @param queryL    要查询的左区间索引
     * @param queryR    要查询的右区间索引
     * @return 值
     */
    private E query(int treeIndex, int l, int r, int queryL, int queryR) {

        // 要查询的边界和当前节点的边界重合的时候，直接返回当前节点的值
        if (l == queryL && r == queryR) {
            return tree[treeIndex];
        }

        // 中间索引
        int mid = l + (r - l) / 2;
        // 当前treeIndex节点的左孩子的索引
        int lefeTreeIndex = leftChild(treeIndex);
        // 当前treeIndex节点的右孩子的索引
        int rightTreeIndex = rightChild(treeIndex);

        // 如果用户要查询的左边界的索引 比 中间索引 要大，则直接去 右子树查询
        if (queryL >= mid + 1) {
            return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        } else if (queryR <= mid) {
            // 如果用户要查询的右边界的索引 比 中间索引 要小，则直接去 左右子树查
            return query(lefeTreeIndex, l, mid, queryL, queryR);
        }

        // 用户要查询的边界 一部分在左子树，一部分再有子树上
        E leftResult = query(lefeTreeIndex, l, mid, queryL, mid);
        E rightResult = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
        // 合并左右子树 查找到的结果
        return merger.merge(leftResult, rightResult);
    }

    /**
     * 将index位置的值，更新为e
     *
     * @param index 索引
     * @param e     值
     */
    public void set(int index, E e) {

        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("索引越界");
        }

        // 修改index位置的值
        data[index] = e;
        // 递归修改index的左右子树的值
        set(0, 0, data.length - 1, index, e);
    }

    /**
     * 在以treeIndex为根的线段树中更新index的值为e
     *
     * @param treeIndex 根节点的索引
     * @param l         根节点左子树的索引
     * @param r         根节点右子树的索引
     * @param index     需要修改位置的索引
     * @param e         值
     */
    private void set(int treeIndex, int l, int r, int index, E e) {

        // 当左右索引相等
        if (l == r) {
            tree[treeIndex] = e;
            return;
        }

        // 中间的索引
        int mid = l + (r - l) / 2;
        // 当前treeIndex节点的左孩子的索引
        int leftTreeIndex = leftChild(treeIndex);
        // 当前treeIndex节点的右孩子的索引
        int rightTreeIndex = rightChild(treeIndex);

        // treeIndex的节点分为[l...mid]和[mid+1...r]左右两部分
        if (index >= mid + 1) {
            //当index大于中间索引时，去右子树中修改
            set(rightTreeIndex, mid + 1, r, index, e);
        } else {
            // 当index小于中间索引时，去左子树中修改
            set(leftTreeIndex, l, mid, index, e);
        }

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                sb.append(tree[i]);
            } else {
                sb.append("null");
            }
            if (i != tree.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
