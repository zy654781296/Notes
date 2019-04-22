package linkedlist;

/**
 * @author zhangy
 *
 * 单向链表
 */
public class LinkedList<E> {

    /*---------------------------------------------
    链表： 动态数据结构

    时间复杂度：

    addLast:            O(n)
    addFirst:           O(1)
    add:                O(n/solution)=O(n)

    removeLast:         O(n)
    removeFirst:        O(1)
    remove:             O(n/solution)=O(n)

    set:                O(n)

    getFirst:           O(1)
    getLast:            O(n)
    get:                O(n/solution)=O(n)

    contains:           O(n)

    综合：
    增： O(n)
    删： O(n)
    查： O(n)
    改： O(n)

    只对链表头进行操作的话： O(1)



    ---------------------------------------------*/

    /**
     * 大小
     */
    private int size;

    /**
     * 虚拟头节点
     */
    private Node virtualHead;

    /**
     * 构造函数
     */
    public LinkedList() {
        virtualHead = new Node(null, null);
        size = 0;
    }

    /**
     * 获取链表的大小
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
     * 在链表的index(0-based)位置添加新的元素e
     * 在链表中不是一个常用的操作，仅练习用
     *
     * @param index 索引
     * @param e     要添加的元素
     */
    public void add(int index, E e) {

        if (index < 0 || index > size) {
            throw new IllegalArgumentException("索引越界");
        }

        Node prev = virtualHead;
        //找到index位置的前一个节点
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        //Node node = new Node(e);
        //node.next = prev.next;
        //prev.next = node;

        //等价写法
        prev.next = new Node(e, prev.next);

        size++;

    }

    /**
     * 在链表头添加新的元素e
     *
     * @param e 要添加的元素
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 在链表末尾添加新的元素e
     *
     * @param e 要添加的元素
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 获得链表的第index(0-based)个位置的元素
     *
     * @param index 索引
     * @return 元素
     */
    public E get(int index) {

        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("索引越界");
        }

        //找到index位置的节点
        Node current = virtualHead.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.e;
    }


    /**
     * 获得链表的第一个元素
     *
     * @return 元素
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获得链表的最后一个元素
     *
     * @return 元素
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * 修改链表的第index(0-based)个位置的元素为e
     *
     * @param index 索引
     * @param e     要修改的元素的值
     */
    public void set(int index, E e) {

        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("索引越界");
        }

        Node cur = virtualHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        cur.e = e;
    }

    /**
     * 查找链表中是否有元素e
     *
     * @param e 元素e
     * @return true 包含
     */
    public boolean contains(E e) {

        Node cur = virtualHead.next;
        while (cur != null) {
            if (cur.e.equals(e)) {
                return true;
            }

            cur = cur.next;
        }

        return false;
    }

    /**
     * 从链表中删除index(0-based)位置的元素, 返回删除的元素
     *
     * @param index 索引
     * @return 删除的元素
     */
    public E remove(int index) {

        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("索引越界");
        }

        //找到index位置的节点
        Node prev = virtualHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node delNode = prev.next;
        prev.next = delNode.next;
        delNode.next = null;
        size--;

        return delNode.e;
    }

    /**
     * 从链表中删除第一个元素
     *
     * @return 删除的元素
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 从链表中删除最后一个元素
     *
     * @return 删除的元素
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 从链表中删除元素e
     *
     * @param e 要删除的元素e
     */
    public void removeElement(E e) {

        Node prev = virtualHead;
        while (prev.next != null) {
            if(prev.next.e.equals(e)) {
                break;
            }

            prev = prev.next;
        }

        if(prev.next != null) {
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
        }
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Node cur = virtualHead.next;
        while (cur != null) {
            sb.append(cur + "-->");
            cur = cur.next;
        }
        sb.append("NULL");

        return sb.toString();
    }

    public static void main(String[] args) {

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 111);
        System.out.println(linkedList);

        linkedList.remove(2);
        System.out.println(linkedList);

        linkedList.removeFirst();
        System.out.println(linkedList);

        linkedList.removeLast();
        System.out.println(linkedList);
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
