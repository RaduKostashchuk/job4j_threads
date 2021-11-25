package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    public synchronized int increment() {
        value++;
        return value;
    }

    public synchronized int get() {
        return value;
    }
}
