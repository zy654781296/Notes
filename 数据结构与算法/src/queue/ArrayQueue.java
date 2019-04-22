package queue;

import array.Array;

/**
 * @author zhangy
 *
 * 基于动态数组实现的 队列
 */
public class ArrayQueue<E> implements Queue<E> {


    /*---------------------------------------------

    队列：线性结构；相比数组，队列对应的操作是数组的子集

    队列：先进先出（FIFO）

    时间复杂度分析：
    enqueue:        O(1)  均摊
    dequeue:        O(n)
    front:          O(1)
    getSize:        O(1)
    isEmpy:         O(1)

    ---------------------------------------------*/

    /**
     * 声明一个动态数组
     */
    private Array<E> array;

    /**
     * 默认构造函数
     */
    public ArrayQueue() {
        array = new Array<>();
    }

    /**
     * 带有容量大小的构造函数
     *
     * @param capacity 队列的容量
     */
    public ArrayQueue(int capacity) {
        array = new Array<>(capacity);
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    public int getCapacity() {
        return array.getCapacity();
    }

    /**
     * 入队
     *
     * @param e 入队的元素
     */
    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    /**
     * 出队
     *
     * @return 出队的元素
     */
    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    /**
     * 获取队首的元素
     *
     * @return 元素
     */
    @Override
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Queue：font[");
        for (int i = 0; i < array.getSize(); i++) {
            sb.append(array.get(i));
            if(i != array.getSize() - 1) {
                sb.append(", ");
            }
        }
        sb.append("] tail");

        return sb.toString();
    }
}
