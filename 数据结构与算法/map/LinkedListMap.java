package map;

/**
 * @param <K>
 * @param <V>
 * @author zhangy
 * 基于链表实现的映射
 */
public class LinkedListMap<K, V> implements Map<K, V> {

    /**
     * 大小
     */
    private int size;

    /**
     * 虚拟头节点
     */
    private Node dummyHead;

    /**
     * 构造函数
     */
    public LinkedListMap() {
        dummyHead = new Node();
        size = 0;
    }

    /**
     * 查找键为key的节点
     *
     * @param key 键
     * @return 节点
     */
    private Node getNode(K key) {
        Node cur = dummyHead.next;
        while (cur != null) {
            if(cur.key.equals(key)) {
                return cur;
            }

            cur = cur.next;
        }
        return null;
    }

    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if(node == null) {
            //如果当前映射没有键为key的映射
            dummyHead.next = new Node(key, value, dummyHead.next);
            size++;
        } else {
            //直接覆盖新的值
            node.value = value;
        }
    }

    @Override
    public V remove(K key) {
        //查找键为key的节点的前一个节点
        Node pre = dummyHead;
        while (pre.next != null) {
            if(pre.next.key.equals(key)) {
                break;
            }
            pre = pre.next;
        }

        if(pre.next != null) {
            Node delNode = pre.next;
            pre.next = delNode.next;
            delNode.next = null;
            size--;
            return delNode.value;
        }

        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(key);
        if(node == null) {
            throw new IllegalArgumentException("没有找到");
        }

        node.value = value;
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
         * next指针
         */
        public Node next;

        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key, V value) {
            this(key, value, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return key.toString() + ":" + value.toString();
        }
    }
}
