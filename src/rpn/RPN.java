package rpn;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class RPN {

    private Stack stack = new Stack();
    private Integer result = new Integer(-1);
    private Scanner scanner;

    private void calculate() throws IOException {
        // opening
        scanner = new Scanner(new File("C:/Users/Dominika/source/repos/onp/src/files/calculations.txt"));

        String read = new String("");
        Integer x = new Integer(-1);

        // if file exists, read a char
        while (c != null) {
            c = scanner.next().charAt(0);
            /* if char is a digit -> push
               if char is an operand -> pop previous and get next argument
               and push calculated result */
            if (Character.isDigit(read)) {
                stack.push(read);
            } else {
                //x = (Integer) Character.getNumericValue((Character) stack.lastElement());
                if (read.equals('+')) {
                    x += Integer.parseInt(scanner.next());
                } else if (read.equals('-')) {
                    x -= Integer.parseInt(scanner.next());
                } else if (read.equals('*')) {
                    x *= Integer.parseInt(scanner.next());
                } else if (read.equals('/')) {
                    x /= Integer.parseInt(scanner.next());
                }
                Integer.toString(x);
                stack.push((char)x);
            }
        }
        result = (Integer) stack.pop();
        if (result == -1) {
            System.out.println("Error: nothing happened");
        } else {
            System.out.println("Result: " + result);
        }

        // closing
        reader.close();
    }

    public static void main(String[] args) throws IOException {
        RPN rpn = new RPN();
        rpn.calculate();
    }
}
