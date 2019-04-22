package leetcode.s804;

import tree.avl.AVLTree;

import java.util.TreeSet;

class Solution1 {

    public int uniqueMorseRepresentations(String[] words) {
        String[] code = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};

        AVLTree<String, Object> set = new AVLTree<>();
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                sb.append(code[c - 'a']);
            }

            set.add(sb.toString(), null);
        }

        return set.getSize();
    }
}