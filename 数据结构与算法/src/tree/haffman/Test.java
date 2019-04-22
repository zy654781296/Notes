package tree.haffman;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试类
 *
 * @author zhangy
 */
public class Test {

    public static void main(String[] ar) {
        List<HuffmanTree.Node> list = new ArrayList<>();
        HuffmanTree.Node<String> node = new HuffmanTree.Node("good", 50);
        list.add(node);
        list.add(new HuffmanTree.Node("morning", 10));
        HuffmanTree.Node<String> node2 =new HuffmanTree.Node("afternoon", 20);
        list.add(node2);
        list.add(new HuffmanTree.Node("hell", 110));
        list.add(new HuffmanTree.Node("hi", 200));
        HuffmanTree tree = new HuffmanTree();
        tree.crateHaffmanTree(list);
        tree.showHuffman(tree.root);
        System.out.println(tree.getHuffmanCode(node2));
    }
}
