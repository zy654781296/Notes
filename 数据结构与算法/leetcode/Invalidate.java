package leetcode;

import java.util.Stack;

public class Invalidate {

    public boolean isValid(String s) {

        if(s == null) {
            return false;
        }

        if(s.length() == 0) {
            return true;
        }

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                char top = stack.pop();

                if(c == ')' && top != '(') {
                    return false;
                }

                if(c == '}' && top != '{') {
                    return false;
                }

                if(c == ']' && top != '[') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

}
