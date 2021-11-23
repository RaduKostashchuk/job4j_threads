package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private static final int TO_MILLIS = 1000;
    private static final int TO_MICROS = 1000;
    private static final int KBS_TO_MICROS = 1000000;
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
            long start = System.nanoTime() / TO_MICROS;
            long end;
            long diff;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                end = System.nanoTime() / TO_MICROS;
                diff = KBS_TO_MICROS / speed - (end - start);
                if (diff > 0) {
                    Thread.sleep(diff / TO_MILLIS, (int) (diff % TO_MILLIS));
                }
                out.write(buffer, 0, bytesRead);
                start = System.nanoTime() / TO_MICROS;
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
        String file = args[2];
        validate(args);
        Thread wget = new Thread(new Wget(url, speed, file));
        wget.start();
        wget.join();
    }
}
