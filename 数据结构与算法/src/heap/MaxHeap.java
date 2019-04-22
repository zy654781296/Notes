package heap;

import array.Array;

/**
 * 基于动态数组实现的 大顶堆
 *
 * @param <E> 泛型
 * @author zhangy
 */
public class MaxHeap<E extends Comparable<E>> {

    /**
     * 元素数组
     */
    private Array<E> data;

    /**
     * 构造函数
     *
     * @param capacity 容量大小
     */
    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    /**
     * 默认构造函数
     */
    public MaxHeap() {
        data = new Array<>();
    }

    /**
     * 构造函数(heapify)
     *
     * @param arr 元素数组
     */
    public MaxHeap(E[] arr) {
        data = new Array<>(arr);
        for (int i = parent(arr.length - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 返回堆中的元素个数
     *
     * @return 元素个数
     */
    public int size() {
        return data.getSize();
    }

    /**
     * 返回一个布尔值, 表示堆中是否为空
     *
     * @return true 空
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * 一个索引所表示的元素的父亲节点的索引
     *
     * @param index 索引
     * @return 索引
     */
    private int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("根节点没有父节点");
        }
        return (index - 1) / 2;
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
     *
     * @param index 索引
     * @return 索引
     */
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
     *
     * @param index 索引
     * @return 索引
     */
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    /**
     * 向堆中添加元素
     *
     * @param e 元素
     */
    public void add(E e) {
        data.addLast(e);
        //维护堆的性质，上浮
        siftUp(data.getSize() - 1);
    }

    /**
     * 上浮
     *
     * @param index 索引
     */
    private void siftUp(int index) {
        //当前元素和父亲元素比较
        while (index > 0 && data.get(parent(index)).compareTo(data.get(index)) < 0) {
            data.swap(index, parent(index));
            index = parent(index);
        }
    }

    /**
     * 堆中的最大元素
     *
     * @return 最大元素
     */
    public E findMax() {
        if (data.getSize() == 0) {
            throw new IllegalArgumentException("为空");
        }
        return data.get(0);
    }

    /**
     * 取出堆中最大元素
     *
     * @return 最大元素
     */
    public E extractMax() {
        E ret = findMax();

        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);

        return ret;
    }

    /**
     * 下沉
     *
     * @param index 索引
     */
    private void siftDown(int index) {

        //左节点的index小于数组的长度
        while (leftChild(index) < data.getSize()) {
            //左节点的索引
            int j = leftChild(index);
            //j+1为右节点的索引。右节点不为空 并且 右节点大于左节点的值
            if (j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) > 0) {
                //data[j]是leftChild和rightChild中最大的值
                j = j + 1;
            }

            //当前节点和 leftChild和rightChild中最大的 比较
            if (data.get(index).compareTo(data.get(j)) >= 0) {
                break;
            }

            data.swap(index, j);
            index = j;
        }
    }

    /**
     * 取出最大的元素，并且替换成元素e
     *
     * @param e 元素e
     * @return 最大的元素
     */
    public E replace(E e) {

        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }
}
