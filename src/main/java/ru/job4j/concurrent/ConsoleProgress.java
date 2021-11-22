package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        int count = 0;
        char[] process = {'|', '/', '-', '\\'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading..." + process[count % 4]);
                count++;
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(2000);
        progress.interrupt();
    }
}