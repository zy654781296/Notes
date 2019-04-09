package queue;

/**
 * @param <E>
 * @author zhangy
 * 基于链表实现的队列
 */
public class LinkedListQueue<E> implements Queue<E> {

    /**
     * 队列的大小
     */
    private int size;

    /**
     * 队首指针
     */
    private Node head;

    /**
     * 队尾指针
     */
    private Node tail;

    /**
     * 构造函数
     */
    public LinkedListQueue() {
        head = null;
        tail = null;
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
     * 入队
     *
     * @param e 入队的元素
     */
    @Override
    public void enqueue(E e) {
        if (tail == null) {
            tail = new Node(e);
            head = tail;
        } else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size++;
    }

    /**
     * 出队
     *
     * @return 出队的元素
     */
    @Override
    public E dequeue() {

        if (isEmpty()) {
            throw new IllegalArgumentException("队列为空");
        }

        Node delNode = head;
        head = head.next;
        delNode.next = null;
        if (head == null) {
            tail = null;
        }

        size--;
        return delNode.e;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("队列为空");
        }

        return head.e;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Queue: front ");

        Node cur = head;
        while(cur != null) {
            sb.append(cur + "->");
            cur = cur.next;
        }
        sb.append("NULL tail");
        return sb.toString();
    }

    /**
     * @author zhangy
     * <p>
     * 节点类
     */
    private class Node {

        /**
         * 当前元素
         */
        public E e;

        /**
         * next指针
         */
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }
}
