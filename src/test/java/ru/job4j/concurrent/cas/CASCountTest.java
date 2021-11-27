package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CASCountTest {

    private static class Increment implements Runnable {
        private final CASCount count;

        public Increment(CASCount count) {
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < 200; i++) {
                count.increment();
                System.out.println(Thread.currentThread().getName() + " " + count.get());
            }
        }
    }

    @Test
    public void whenIncrement() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(
                new Increment(count), "first");
        Thread second = new Thread(
                new Increment(count), "second");
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(400));
    }

}