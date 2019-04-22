package tree.haffman;

import java.util.Stack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 哈夫曼树
 *
 * @author zhangy
 */
public class HuffmanTree {

    public Node root;

    /**
     * 创建哈夫曼树
     *
     * @param list list
     * @return 根节点
     */
    public Node crateHaffmanTree(List<Node> list) {

        // 只要nodes数组中还有2个以上的节点
        while (list.size() > 1) {
            // 对剩余的节点进行排序
            Collections.sort(list);
            //获取权值最小的两个节点
            Node left = list.get(list.size() - 1);
            Node right = list.get(list.size() - 2);

            //生成新节点，新节点的权值为两个子节点的权值之和
            Node parent = new Node(null, left.weight + right.weight);
            //让新节点作为两个权值最小节点的父节点
            parent.leftNode = left;
            parent.rightNode = right;
            left.parent = parent;
            right.parent = parent;

            //删除权值最小的两个节点
            list.remove(left);
            list.remove(right);
            //将新节点加入到集合中
            list.add(parent);
        }

        root = list.get(0);
        return root;
    }

    /**
     * 哈夫曼树的广度优先遍历
     *
     * @param root
     */
    public void showHuffman(Node root) {

        LinkedList<Node> linkedList = new LinkedList<>();
        linkedList.offer(root);
        while (!linkedList.isEmpty()) {
            Node node = linkedList.pop();
            System.out.println(node.data);
            if (node.leftNode != null) {
                linkedList.offer(node.leftNode);
            }
            if (node.rightNode != null) {
                linkedList.offer(node.rightNode);
            }
        }
    }

    /**
     * 获取节点的哈夫曼编码
     * 左0  右1
     *
     * @param node 节点
     * @return 哈夫曼编码
     */
    public String getHuffmanCode(Node node) {

        Node cur = node;
        Stack<String> stack = new Stack<>();
        while (cur != null && cur.parent != null) {
            //left 0  right 1
            if (cur.parent.leftNode == cur) {
                stack.push("0");
            } else if (cur.parent.rightNode == cur) {
                stack.push("1");
            }
            cur = cur.parent;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    /**
     * 节点类
     *
     * @param <E>
     */
    public static class Node<E> implements Comparable<Node<E>> {

        E data;
        int weight;
        Node leftNode;
        Node rightNode;
        Node parent;

        public Node(E data, int weight) {
            this.data = data;
            this.weight = weight;
            leftNode = null;
            rightNode = null;
            parent = null;
        }

        @Override
        public int compareTo(Node<E> o) {
            if (this.weight > o.weight) {
                return -1;
            } else if (this.weight < o.weight) {
                return 1;
            }
            return 0;
        }
    }

}
