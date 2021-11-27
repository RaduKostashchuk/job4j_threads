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

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == size) {
            wait();
          }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T result;
        while (queue.isEmpty()) {
            wait();
        }
        result = queue.poll();
        notifyAll();
        return result;
    }

    public synchronized int size() {
        return queue.size();
    }
}
