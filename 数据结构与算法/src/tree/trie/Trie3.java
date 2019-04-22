package tree.trie;

/**
 * 基于数组实现的Trie
 *
 * @author zhangy
 */
public class Trie3 {

    /**
     * 节点类
     */
    private class Node {

        public boolean isWord;
        public Node[] next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new Node[26];
        }

        public Node() {
            this(false);
        }
    }


    /**
     * 根节点
     */
    private Node root;

    /**
     * 大小
     */
    private int size;

    /**
     * 默认构造函数
     */
    public Trie3() {
        root = new Node();
        size = 0;
    }

    /**
     * 获取字典树的大小
     *
     * @return 大小
     */
    public int getSize() {
        return size;
    }

    /**
     * 向Trie中添加一个新的单词word
     *
     * @param word 单词
     */
    public void add(String word) {

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

        //如果当前的node已经是一个word，则不需要添加
        if (!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }

    /**
     * 查询单词word是否在Trie中
     *
     * @param word 单词
     * @return true 包含
     */
    public boolean contains(String word) {

        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next[c - 'a'] == null) {
                return false;
            }
            cur = cur.next[c - 'a'];
        }
        //cur就是word的最后一个字符的Node
        return cur.isWord;
    }

    /**
     * 查询是否在Trie中有单词以prefix为前缀
     *
     * @param prefix 前缀
     * @return true 包含
     */
    public boolean isPrefix(String prefix) {

        Node cur = root;
        char[] chars = prefix.toCharArray();
        for (char c : chars) {
            if (cur.next[c - 'a'] == null) {
                return false;
            }
            cur = cur.next[c - 'a'];
        }
        //cur就是word的最后一个字符的Node
        return true;
    }
}
