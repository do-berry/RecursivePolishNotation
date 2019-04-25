package rpn;

import java.io.*;
import java.util.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

public class RPN {

    private Stack <Character> stack = new Stack<Character>();
    private Stack <Double> doubleStack = new Stack<Double>();
    private static Queue <Character> output = new LinkedList<>();
    private Scanner scanner;
    private static Double result = 0.0;
    String path = new String("C:/Users/Dominika/source/repos/RecursivePolishNotation/src/files/1050.txt");

    private void convert() throws IOException {
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        char read = Character.MIN_VALUE;
        String line;
        while (scanner.hasNext()) {
            clearEverything();
            line = scanner.next();
            int i = 0;
            while (i < line.length()) {
                read = line.charAt(i);
                if (Character.isDigit(read)) {
                    output.add(read);
                } else if (isFunction(read)) {
                    stack.push(read);
                    i += 2;
                } else if (read == '(') {
                    stack.push(read);
                } else if (read == ')') {
                    while (!stack.peek().equals((Character) '(')) {
                        moveIntoOutput();
                    }
                    stack.pop();
                    if (isFunction(stack.peek())) {
                        moveIntoOutput();
                    }
                } else if (isOperator(read)) {
                    checkOperator(read);
                }
                i++;
            }
            while (!stack.empty()) {
                output.add((char) stack.peek());
                stack.pop();
            }
            calculate();
        }

        scanner.close();
    }

    private void clearEverything() {
        stack.clear();
        output.clear();
        doubleStack.clear();
    }

    private void checkOperator(char read) {
        if (read == '+' || read == '-') {
            if (stack.size() != 0) {
                if (stack.peek().equals((Character) '*') || stack.peek().equals((Character) '/')) {
                    moveIntoOutput();
                    checkOperator(read);
                } else if (stack.peek().equals((Character) '(')) {
                    stack.push(read);
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

    private boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            return true;
        } else return false;
    }

    private boolean isFunction(char c) {
        if (c == 's' || c == 't' || c == 'c') {                 // sin, tg, cos
            return true;
        } else return false;
    }

    private void calculate() {
        for (char c : output) {
            if (Character.isDigit(c)) {
                doubleStack.push(Double.parseDouble(String.valueOf(c)));
            } else if (isOperator(c)) {
                double a = doubleStack.peek();
                doubleStack.pop();
                double b = doubleStack.peek();
                doubleStack.pop();
                double r = 0;
                if (c == '+') {
                    r = b + a;
                } else if (c == '-') {
                    r = b - a;
                } else if (c == '*') {
                    r = b * a;
                } else if (c == '/') {
                    r = b / a;
                }
                doubleStack.push(r);
            } else if (isFunction(c)) {
                double r = 0, a = doubleStack.peek();
                doubleStack.pop();
                if (c == 's') {
                    r = sin(a);
                } else if (c == 'c') {
                    r = cos(a);
                } else if (c == 't') {
                    r = tan(a);
                }
                doubleStack.push(r);
            }
        }
        result = doubleStack.peek();
    }

    public static void main(String[] args) throws IOException {
        RPN rpn = new RPN();
        rpn.convert();
        //rpn.calculate();
        //print();
    }

    private static void print() {
        Iterator iterator = output.iterator();
        while(iterator.hasNext()) {
            System.out.print((Character) iterator.next());
        }
        System.out.println("\n result is " + result);
    }
}
