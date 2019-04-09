package queue;

/**
 * @author zhangy
 * 队列的接口
 */
public interface Queue<E> {

    /**
     * 队列的大小
     * @return 大小
     */
   int getSize();

    /**
     * 是否为空
     *
     * @return true 空
     */
    boolean isEmpty();


    /**
     * 入队
     *
     * @param e 入队的元素
     */
    void enqueue(E e);

    /**
     * 出队
     *
     * @return 出队的元素
     */
    E dequeue();

    /**
     * 获取队首元素
     *
     * @return 元素
     */
    E getFront();
}
