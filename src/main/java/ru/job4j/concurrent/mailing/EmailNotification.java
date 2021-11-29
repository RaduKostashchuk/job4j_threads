package ru.job4j.concurrent.mailing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String subject = "Notification " + user.getUsername() + " to email " + user.getEmail();
        String body = "Add a new event to " + user.getUsername();
        pool.submit(() -> send(subject, body, user.getEmail()));
    }

    public void send(String subject, String body, String email) { }

    public void close() throws InterruptedException {
        pool.shutdown();
        while (pool.isTerminated()) {
            Thread.sleep(100);
        }
    }
}
