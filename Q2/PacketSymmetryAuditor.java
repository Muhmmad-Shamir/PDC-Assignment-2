package Q2;

import java.util.Scanner;

public class PacketSymmetryAuditor {
    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter raw packet payload: ");
        String payload = input.nextLine();
        input.close();

        int length = payload.length();
        int capacity = Math.max(1, length);

        TransactionStack stack = new TransactionStack(capacity);
        RequestQueue queue = new RequestQueue(capacity);

        for (int i = 0; i < length; i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        System.out.println("Payload: " + payload);

        if (!stack.isEmpty()) {
            System.out.println("Stack top: " + stack.peek());
        }

        char[] reversed = new char[length];
char[] original = new char[length];

Thread stackWorker = new Thread(new Runnable() {
    @Override
    public void run() {
        int i = 0;

        while (!stack.isEmpty()) {
            reversed[i] = stack.pop();
            i++;
        }
    }
}, "Stack-Worker");

Thread queueWorker = new Thread(new Runnable() {
    @Override
    public void run() {
        int i = 0;

        while (!queue.isEmpty()) {
            original[i] = queue.dequeue();
            i++;
        }
    }
}, "Queue-Worker");

stackWorker.start();
queueWorker.start();

stackWorker.join();
queueWorker.join();
    }
}