package Q2;

public class TransactionStack {
    private char[] stack;
    private int top;

    public TransactionStack(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        stack = new char[capacity];
        top = -1;
 
    }
   
        public boolean isEmpty() {
        return top == -1;
    }

    public void push(char c) {
        if (top == stack.length - 1) {
            throw new IllegalStateException("Stack is full");
        }

        top++;
        stack[top] = c;
    }

}


