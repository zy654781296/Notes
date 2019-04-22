package leetcode.s208;

import java.util.TreeMap;

/**
 * LeetCode208 实现 Trie (前缀树)
 *
 * @author zhangy
 */
public class Trie {

    private Node root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }

        if (!cur.isWord) {
            cur.isWord = true;
        }
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node cur = root;
        char[] chars = prefix.toCharArray();
        for (char c : chars) {
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        //cur就是word的最后一个字符的Node
        return true;
    }

    private class Node {

        /**
         * 是否是个单词
         */
        public boolean isWord;

        /**
         * Next指针
         */
        public TreeMap<Character, Node> next;

        /**
         * 构造函数
         *
         * @param isWord 是否是单词
         */
        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        /**
         * 默认构造函数
         */
        public Node() {
            this(false);
        }
    }

}
