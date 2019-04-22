package rpn;

import java.io.*;
import java.util.*;

public class RPN {

    private Stack <Character> stack = new Stack<Character>();
    private Stack <Integer> intStack = new Stack<Integer>();
    private static Queue <Character> output = new LinkedList<>();
    private Scanner scanner;
    private static Integer result = 0;
    String path = new String("C:/Users/Dominika/source/repos/onp/src/files/calculations.txt");

    private void convert() throws IOException {
        scanner = new Scanner(new File(path));
        char read = Character.MIN_VALUE;
        String line;
        while (scanner.hasNext()) {
            line = scanner.next();
            for (int i = 0; i < line.length(); i++) {
                read = line.charAt(i);
                if (Character.isDigit(read)) {
                    output.add(read);
                } else if (isFunction(read)) {
                    stack.push(read);
                } else if (isOperand(read)) {
                    checkOperand(read);
                }
            }
        }
        while (!stack.empty()) {
            output.add((char) stack.peek());
            stack.pop();
        }
        scanner.close();
    }

    private void checkOperand(char read) {
        if (read == '+' || read == '-') {
            if (stack.size() != 0) {
                if (stack.peek().equals((Character) '*') || stack.peek().equals((Character) '/')) {
                    moveIntoOutput();
                    checkOperand(read);
                } else {
                    moveIntoOutput();
                    stack.push(read);
                }
            } else {
                stack.push(read);
            }
        } else {
            if (stack.size() != 0) {
                if (stack.peek().equals((Character) '*') || stack.peek().equals((Character) '/')) {
                    moveIntoOutput();
                }
            }
            stack.push(read);
        }
    }

    private void moveIntoOutput() {
        output.add(stack.peek());
        stack.pop();
    }

    private boolean isOperand(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            return true;
        } else return false;
    }

    private boolean isFunction(char c) {
        if (c == 's' || c == 't' || c == 'c') {
            return true;
        } else return false;
    }

    private void calculate() {
        for (char c : output) {
            if (Character.isDigit(c)) {
                intStack.push(Integer.parseInt(String.valueOf(c)));
            } else if (isOperand(c)) {
                int a = intStack.peek();
                intStack.pop();
                int b = intStack.peek();
                intStack.pop();
                int r = 0;
                if (c == '+') {
                    r = b + a;
                } else if (c == '-') {
                    r = b - a;
                } else if (c == '*') {
                    r = b * a;
                } else if (c == '/') {
                    r = b / a;
                }
                intStack.push(r);
            }
        }
        result = intStack.peek();
    }

    public static void main(String[] args) throws IOException {
        RPN rpn = new RPN();
        rpn.convert();
        Iterator iterator = output.iterator();
        while(iterator.hasNext()){
            System.out.print((Character) iterator.next());
        }
        rpn.calculate();
        System.out.println("\n result is " + result);
    }
}
