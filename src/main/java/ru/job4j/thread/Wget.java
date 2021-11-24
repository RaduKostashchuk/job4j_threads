package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String file;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                FileOutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            int sumBytesRead = 0;
            long start = System.currentTimeMillis();
            long interval;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                sumBytesRead += bytesRead;
                if (sumBytesRead > speed) {
                    interval  = (System.currentTimeMillis() - start);
                    if (interval < 1000) {
                        Thread.sleep(1000 - interval);
                    }
                    sumBytesRead = 0;
                    start = System.currentTimeMillis();
                }
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void validate(String[] args) {
        int speed = Integer.parseInt(args[1]);
        if (args.length != 3 || speed == 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        long start;
        long end;
        String file = args[2];
        validate(args);
        Thread wget = new Thread(new Wget(url, speed, file));
        start = System.currentTimeMillis();
        wget.start();
        wget.join();
        end = System.currentTimeMillis();
        System.out.println("File load time: " + (end - start) / 1000);
    }
}
