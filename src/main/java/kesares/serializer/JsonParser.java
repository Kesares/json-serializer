package kesares.serializer;

import java.util.Stack;

public class JsonParser {

    public void parse(String json) {
        if (!hasValidParentheses(json))
            throw new IllegalArgumentException("Invalid json");
        System.out.println(json);
        String s = json.substring(1, json.length() - 1);
        System.out.println(s);
    }

    private static boolean hasValidParentheses(String json) {
        Stack<Character> parentheses = new Stack<>();
        for (int i = 0; i < json.length(); i++) {
            char next = json.charAt(i);
            if (next == '(' || next == '{' || next == '[') parentheses.push(next);
            if (parentheses.isEmpty()) return false;

            char c = parentheses.peek();
            if (c == '(' && next == ')' ||
                    c == '{' && next == '}' ||
                    c == '[' && next == ']') {
                parentheses.pop();
            }
        }
        return parentheses.isEmpty();
    }
}
