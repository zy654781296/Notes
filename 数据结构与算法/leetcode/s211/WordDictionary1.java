package leetcode.s211;


/**
 * LeetCode 211. 添加与搜索单词 - 数据结构设计
 * 设计一个支持以下两种操作的数据结构：
 * void addWord(word)
 * bool search(word)
 *
 * @author zhangy
 */
public class WordDictionary1 {

    private Node root;

    /**
     * Initialize your data structure here.
     */
    public WordDictionary1() {
        root = new Node();
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
        Node cur = root;
        int idx = 0;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            idx = c - 'a';
            if (cur.next[idx] == null) {
                cur.next[idx] = new Node();
            }
            cur = cur.next[idx];
        }
        cur.isWord = true;
    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
     */
    public boolean search(String word) {
        return match(root, word, 0);
    }

    private boolean match(Node node, String word, int index) {

        if (index == word.length()) {
            return node.isWord;
        }

        char c = word.charAt(index);
        int idx = c - 'a';

        if (c != '.') {
            if (node.next[idx] == null) {
                return false;
            }
            return match(node.next[idx], word, index + 1);
        } else {
            for (int i = 0; i < node.next.length; i++) {
                if(node.next[i] != null) {
                    if (match(node.next[i], word, index + 1)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private class Node {

        public boolean isWord;
        public Node[] next;

        public Node() {
            this.isWord = false;
            next = new Node[26];
        }


    }
}

