package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        int count = 0;
        char[] process = {'|', '/', '-', '\\'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading..." + process[count % process.length]);
                count++;
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(2000);
        progress.interrupt();
    }
}
