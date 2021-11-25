package ru.job4j.concurrent.userstore;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public final class UserStore {
    @GuardedBy("this")
    private final Store store = new MemStore();

    public synchronized boolean add(User user) {
        return user.getId() >= 0 && user.getAmount() >= 0 && store.add(user);
    }

    public synchronized boolean delete(User user) {
        return store.delete(user);
    }

    public synchronized boolean update(User user) {
        return user.getAmount() >= 0 && store.update(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User toUser = store.get(toId);
        User fromUser = store.get(fromId);
        if (amount > 0 && toUser != null && fromUser != null) {
            int fromAmount = fromUser.getAmount();
            int toAmount = toUser.getAmount();
            if (amount <= fromAmount) {
                store.update(new User(fromUser.getId(), fromAmount - amount));
                store.update(new User(toUser.getId(), toAmount + amount));
                result = true;
            }
        }
        return result;
    }

    public synchronized User get(int id) {
        return store.get(id);
    }
}
