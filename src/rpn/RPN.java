package rpn;

import java.io.*;
import java.util.*;

public class RPN {

    private Stack <Character> stack = new Stack<Character>();
    private Stack <Integer> intStack = new Stack<Integer>();
    private static Queue <Character> output = new LinkedList<>();
    private Scanner scanner;
    private static Integer result = 0;
    String path = new String("C:/Users/ddolik/Documents/GitHub/onp/src/files/calculations.txt");

    private void convert() throws IOException {
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        char read = Character.MIN_VALUE;
        String line;
        while (scanner.hasNext()) {
            line = scanner.next();
            int i = 0;
            while (i < line.length()) {
                read = line.charAt(i);
                if (Character.isDigit(read)) {
                    output.add(read);
                } else if (isFunction(read)) {
                    stack.push(read);
                    i += 2;
                } else if (isOperand(read)) {
                    checkOperand(read);
                } else if (read == '(') {               // equals?
                    stack.push(read);
                } else if (read == ')') {               // right bracket for comparing only -> sth MUST BE on stack
                    while (!stack.peek().equals((Character) '(')) {
                        moveIntoOutput();
                    }
                    stack.pop();
                }
                i++;
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
                } else if (stack.peek().equals((Character) '(')) {
                    //moveIntoOutput();
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

    private boolean isOperand(char c) {
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
        try {
            Iterator iterator = output.iterator();
            while(iterator.hasNext()){
                System.out.print((Character) iterator.next());
            }
        } catch (NullPointerException e) {
            System.out.println("Nothing in output queue");
        }

//        rpn.calculate();
//        System.out.println("\n result is " + result);
    }
}
