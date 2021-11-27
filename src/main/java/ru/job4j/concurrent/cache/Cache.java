package ru.job4j.concurrent.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(
                model.getId(),
                (k, v) -> {
                    Base stored = memory.get(model.getId());
                    if (model.getVersion() != stored.getVersion()) {
                        throw new OptimisticException("Versions aren't equal.");
                    }
                    Base toSave = new Base(model.getId(), model.getVersion() + 1);
                    toSave.setName(model.getName());
                    return toSave;
                }
                ) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int id) {
        return memory.get(id);
    }
}
