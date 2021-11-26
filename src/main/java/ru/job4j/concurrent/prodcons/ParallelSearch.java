package ru.job4j.concurrent.prodcons;

import javax.swing.text.TabExpander;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final Thread consumer;
        final Thread producer;
        producer = new Thread(
                () -> {
                    for (int i = 0; i < 4; i++) {
                        try {
                            queue.offer(i);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        consumer = new Thread(
                () -> {
                    try {
                        while (true) {
                            System.out.println(queue.poll());
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Consumer exiting.");
                    }
                }
        );
        producer.start();
        consumer.start();
        while (!producer.getState().equals(Thread.State.TERMINATED)) {
            Thread.sleep(500);
        }
        consumer.interrupt();
    }
}
