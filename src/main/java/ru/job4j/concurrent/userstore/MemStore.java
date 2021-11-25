package ru.job4j.concurrent.userstore;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemStore implements Store {
    private final List<User> store = new CopyOnWriteArrayList<>();

    @Override
    public boolean add(User user) {
        return store.add(user);
    }

    @Override
    public User get(int id) {
        User result = User.NULLUSER;
        for (User el : store) {
            if (el.getId() == id) {
                result = el;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean delete(User user) {
        return store.remove(user);
    }
}
