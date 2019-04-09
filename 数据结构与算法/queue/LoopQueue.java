package queue;

/**
 * @param <E>
 * @author zhangy
 * <p>
 * 循环队列
 */
public class LoopQueue<E> implements Queue<E> {

    /*---------------------------------------------

    基于动态数组实现的队列的 出队操作的 时间复杂度为 O(n)，
    为了提高效率，使用 循环队列

    有2个指针，front-->指向 队首元素，  tail-->指向 队尾元素
    front == tail 队列为空        （tail + 1）% c == front 队列满
    当队列容量为 capacity时，可放 capacity-1  个元素，浪费一个空间


    时间复杂度:

    enqueue:          O(1)  均摊
    dequeue:          O(1)  均摊
    getFront:         O(1)
    getSize:          O(1)
    isEmpty:          O(1)


    ----------------------------------------------*/

    /**
     * 队列元素的数组
     */
    private E[] data;

    /**
     * 指针 指向队首
     */
    private int front;

    /**
     * 指针 指向队尾
     */
    private int tail;

    /**
     * 队列的大小
     */
    private int size;

    /**
     * 默认构造函数
     */
    public LoopQueue() {
        this(10);
    }

    /**
     * 构造函数
     *
     * @param capacity 容量大小
     */
    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
    }

    /**
     * 队列的大小
     *
     * @return 大小
     */
    public int getCapacity() {
        return data.length - 1;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    /**
     * 入队
     *
     * @param e 入队的元素
     */
    @Override
    public void enqueue(E e) {

        //队列已满
        if ((tail + 1) % data.length == front) {
            resize(getCapacity() * 2);
        }

        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }

    /**
     * 扩容或缩容
     *
     * @param capacity 大小
     */
    private void resize(int capacity) {

        E[] newData = (E[]) new Object[capacity + 1];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(i + front) % data.length];
        }

        data = newData;
        front = 0;
        tail = size;
    }

    /**
     * 出队
     *
     * @return 出队的元素
     */
    @Override
    public E dequeue() {

        if(isEmpty()) {
            throw new IllegalArgumentException("队列为空");
        }

        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;

        if(size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }

        return ret;
    }

    @Override
    public E getFront() {

        if(isEmpty()) {
            throw new IllegalArgumentException("队列为空");
        }

        return data[front];
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        sb.append("front [");
        for(int i = front ; i != tail ; i = (i + 1) % data.length){
            sb.append(data[i]);
            if((i + 1) % data.length != tail)
                sb.append(", ");
        }
        sb.append("] tail");
        return sb.toString();
    }
}
