package leetcode.s709;

/**
 * 709. 转换成小写字母 该函数接收一个字符串参数 str，并将该字符串中的大写字母转换成小写字母，之后返回新的字符串。
 */
public class Solution {

    public String toLowerCase(String str) {

        if (str.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c >= 'A' && c <= 'Z') {
               int s = (int)c;
               s+=32;
               c = (char)s;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
