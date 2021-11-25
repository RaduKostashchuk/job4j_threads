package ru.job4j.concurrent.userstore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemStore implements Store {
    private final Map<Integer, User> store = new ConcurrentHashMap<>();

    @Override
    public boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    @Override
    public boolean update(User user) {
        return store.replace(user.getId(), user) != null;
    }

    @Override
    public boolean delete(User user) {
        return store.remove(user.getId(), user);
    }

    @Override
    public User get(int id) {
        return store.get(id);
    }
}
