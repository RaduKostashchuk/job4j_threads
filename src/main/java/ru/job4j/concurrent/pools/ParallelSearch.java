package ru.job4j.concurrent.pools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class ParallelSearch<T> extends RecursiveTask<Set<Integer>> {
    private static final int THRESHOLD = 10;
    private final T[] array;
    private final int from;
    private final int to;
    private final Predicate<T> predicate;

    public ParallelSearch(T[] array, int from, int to, Predicate<T> predicate) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.predicate = predicate;
    }

    @Override
    protected Set<Integer> compute() {
        Set<Integer> result = new HashSet<>();
        if ((to - from) <= THRESHOLD) {
            return Search.search(array, from, to, predicate);
        }
        int middle = (to - from) / 2;
        ParallelSearch<T> left =
                new ParallelSearch<>(array, from, middle, predicate);
        ParallelSearch<T> right =
                new ParallelSearch<>(array, middle + 1, to, predicate);
        left.fork();
        right.fork();
        result.addAll(left.join());
        result.addAll(right.join());
        return result;
    }
}
