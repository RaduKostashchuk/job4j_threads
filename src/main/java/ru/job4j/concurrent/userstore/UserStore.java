package ru.job4j.concurrent.userstore;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public final class UserStore {
    @GuardedBy("this")
    private final Store store = new MemStore();

    public synchronized boolean add(User user) {
        return user.getId() >= 0 && store.add(user);
    }

    public synchronized boolean delete(User user) {
        return store.delete(user);
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        User toUpdate = store.get(user.getId());
        int amountToSet = user.getAmount();
        if (!toUpdate.equals(User.NULLUSER) && amountToSet >= 0) {
            toUpdate.setAmount(amountToSet);
            result = true;
        }
        return result;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User toUser = store.get(toId);
        User fromUser = store.get(fromId);
        if (amount > 0 && !toUser.equals(User.NULLUSER) && !fromUser.equals(User.NULLUSER)) {
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
        User result = store.get(id);
        return new User(result.getId(), result.getAmount());
    }
}
