package tree.trie;

import java.util.TreeMap;

/**
 * 基于链表字典树
 *
 * @author zhangy
 */
public class Trie {

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
    public Trie() {
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
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                //一个字符对应一个Node节点
                cur.next.put(c, new Node());
            }
            //cur就是word的最后一个字符的Node
            cur = cur.next.get(c);
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
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
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
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        //cur就是word的最后一个字符的Node
        return true;
    }


    // 1，如果单词是另一个单词的前缀，只需要把该word的最后一个节点的isWord的改成false
    // 2，如果单词的所有字母的都没有多个分支，删除整个单词
    // 3，如果单词的除了最后一个字母，其他的字母有多个分支，

    /**
     * 删除操作
     *
     * @param word 单词
     * @return 删除成功
     */
    public boolean remove(String word) {

        Node childNode = null;
        int childNodeIndex = -1;
        Node cur = root;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Node child = cur.next.get(chars[i]);
            //如果Trie中没有这个单词
            if (child == null) {
                return false;
            }

            //当前节点的子节点大于1个
            if (child.next.size() > 1) {
                childNodeIndex = i;
                childNode = child;
            }

            cur = child;
        }

        //如果单词后面还有子节点
        if (cur.next.size() > 0) {
            if (cur.isWord) {
                cur.isWord = false;
                size--;
                return true;
            }

            //不存在该单词，该单词只是前缀
            return false;
        }

        //如果单词的所有字母的都没有多个分支，删除整个单词
        if (childNodeIndex == -1) {
            root.next.remove(word.charAt(0));
            size--;
            return true;
        }

        //如果单词的除了最后一个字母，其他的字母有分支
        if (childNodeIndex != word.length() - 1) {
            childNode.next.remove(word.charAt(childNodeIndex + 1));
            size--;
            return true;
        }

        return false;
    }

    /**
     * 节点类
     */
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
