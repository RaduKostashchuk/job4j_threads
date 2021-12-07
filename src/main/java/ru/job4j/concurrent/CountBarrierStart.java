package ru.job4j.concurrent;

public class CountBarrierStart {
    public static void main(String[] args) {
        CountBarrier cb = new CountBarrier(3);
        Thread thr1 = new Thread(
                () -> {
                    cb.await();
                    System.out.println("thr1 started");
                }
        );
        Thread thr2 = new Thread(
                () -> {
                    cb.await();
                    System.out.println("thr2 started");
                }
        );
        Thread thr3 = new Thread(
                () -> {
                    try {
                        cb.count();
                        System.out.println("Ready");
                        Thread.sleep(1000);
                        cb.count();
                        System.out.println("Steady");
                        Thread.sleep(1000);
                        System.out.println("Go!");
                        cb.count();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        thr1.start();
        thr2.start();
        thr3.start();
    }
}
