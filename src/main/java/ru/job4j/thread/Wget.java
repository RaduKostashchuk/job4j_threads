package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                FileOutputStream out = new FileOutputStream("c:\\temp\\wget.tmp")) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            long start;
            long end;
            long diff;
            while (true) {
                start = System.nanoTime();
                bytesRead = in.read(buffer, 0, 1024);
                end = System.nanoTime();
                if (bytesRead != -1) {
                    out.write(buffer, 0, bytesRead);
                } else {
                    break;
                }
                diff = (int) (1E+9 / speed) - (end - start);
                if (diff > 0) {
                    Thread.sleep((long) (diff / 1E+6), (int) (diff % 1E+6));
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
