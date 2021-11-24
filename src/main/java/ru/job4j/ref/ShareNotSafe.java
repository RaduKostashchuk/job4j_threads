package ru.job4j.ref;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user1 = User.of("name1");
        cache.add(user1);
        Thread first = new Thread(
                () -> user1.setName("rename")
                    );
        first.start();
        first.join();
        System.out.println(cache.findById(1).getName());
    }
}
