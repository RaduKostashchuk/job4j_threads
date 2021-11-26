package ru.job4j.concurrent.prodcons;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int size;
    private int count = 0;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (count == size) {
            wait();
          }
        queue.offer(value);
        count++;
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T result;
        while (queue.isEmpty()) {
            wait();
        }
        result = queue.poll();
        count--;
        notifyAll();
        return result;
    }

    public int size() {
        return count;
    }
}
