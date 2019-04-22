package leetcode.s677;

import java.util.TreeMap;

/**
 * LeetCode 677 键值映射
 * 实现一个 MapSum 类里的两个方法，insert 和 sum。
 * 对于方法 insert，你将得到一对（字符串，整数）的键值对。字符串表示键，整数表示值。如果键已经存在，那么原来的键值对将被替代成新的键值对。
 * 对于方法 sum，你将得到一个表示前缀的字符串，你需要返回所有以该前缀开头的键的值的总和。
 *
 * @author zhangy
 */
public class MapSum {

    private Node root;

    /**
     * Initialize your data structure here.
     */
    public MapSum() {
        root = new Node();
    }

    public void insert(String key, int val) {

        Node cur = root;
        char[] chars = key.toCharArray();
        for (char c : chars) {
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        cur.value = val;
    }

    public int sum(String prefix) {

        Node cur = root;
        char[] chars = prefix.toCharArray();
        for (char c : chars) {
            if (cur.next.get(c) == null) {
                return 0;
            }
            cur = cur.next.get(c);
        }

        return sum(cur);
    }

    private int sum(Node node) {

        int res = node.value;
        for (Character c : node.next.keySet()) {
            res += sum(node.next.get(c));
        }

        return res;
    }

    private class Node {

        public int value;
        public TreeMap<Character, Node> next;

        public Node() {
            this.value = 0;
            next = new TreeMap<>();
        }
    }
}
