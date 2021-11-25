package ru.job4j.concurrent.userstore;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public final class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> store = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean delete(User user) {
        return store.remove(user.getId(), user);
    }

    public synchronized boolean update(User user) {
        return store.replace(user.getId(), user) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User toUser = store.get(toId);
        User fromUser = store.get(fromId);
        if (amount > 0 && toUser != null && fromUser != null) {
            int fromAmount = fromUser.getAmount();
            int toAmount = toUser.getAmount();
            if (amount <= fromAmount) {
                fromUser.setAmount(fromAmount - amount);
                toUser.setAmount(toAmount + amount);
                result = true;
            }
        }
        return result;
    }

    public synchronized User get(int id) {
        return store.get(id);
    }
}
