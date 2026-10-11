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
}