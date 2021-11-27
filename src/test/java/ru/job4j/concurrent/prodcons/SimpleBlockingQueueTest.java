package ru.job4j.concurrent.prodcons;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {

    private static class Producer extends Thread {
        private final SimpleBlockingQueue<Integer> queue;

        public Producer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                queue.offer(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private static class Consumer extends Thread {
        private final SimpleBlockingQueue<Integer> queue;

        public Consumer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void whenStartConsumerThenStateIsWaiting() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread consumer = new Consumer(queue);
        consumer.start();
        Thread.sleep(100);
        assertThat(consumer.getState(), is(Thread.State.WAITING));
        assertThat(queue.size(), is(0));
    }

    @Test
    public void whenStartTwoProducersThenSecondStateIsWaiting() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer1 = new Producer(queue);
        Thread producer2 = new Producer(queue);
        producer1.start();
        producer1.join();
        producer2.start();
        Thread.sleep(100);
        assertThat(producer1.getState(), is(Thread.State.TERMINATED));
        assertThat(producer2.getState(), is(Thread.State.WAITING));
    }

    @Test
    public void whenStartProducerBeforeConsumerThenBothStateIsTerminated() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Producer(queue);
        Thread consumer = new Consumer(queue);
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
        assertThat(producer.getState(), is(Thread.State.TERMINATED));
        assertThat(consumer.getState(), is(Thread.State.TERMINATED));
    }
}