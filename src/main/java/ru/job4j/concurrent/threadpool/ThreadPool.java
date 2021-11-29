package ru.job4j.concurrent.threadpool;

import ru.job4j.concurrent.prodcons.SimpleBlockingQueue;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int size) {
         this.tasks = new SimpleBlockingQueue<>(size);
         for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
             Thread thread = new Thread(
                     () -> {
                         while (!Thread.currentThread().isInterrupted()) {
                             try {
                                 Runnable task = tasks.poll();
                                 System.out.print(Thread.currentThread().getName() + " ");
                                 task.run();
                             } catch (InterruptedException e) {
                                 System.out.println("Exiting.");
                                 Thread.currentThread().interrupt();
                             }
                         }
                     }
             );
             thread.start();
             threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public void shutdown() {
        for (Thread el : threads) {
            el.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(10);
        Runnable task1 = () -> System.out.println("Task1");
        Runnable task2 = () -> System.out.println("Task2");
        Runnable task3 = () -> System.out.println("Task3");
        Runnable task4 = () -> System.out.println("Task4");
        pool.work(task1);
        pool.work(task2);
        pool.work(task3);
        pool.work(task4);
        pool.work(task1);
        Thread.sleep(500);
        pool.shutdown();
    }
}
