package ru.job4j.concurrent.pools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParallelSearch<T> extends RecursiveTask<Set<Integer>> {
    private static final int THRESHOLD = 10;
    private final T[] array;
    private final Predicate<T> predicate;

    public ParallelSearch(T[] array, Predicate<T> predicate) {
        this.array = array;
        this.predicate = predicate;
    }

    @Override
    protected Set<Integer> compute() {
        Set<Integer> result = new HashSet<>();
        if (array.length <= THRESHOLD) {
            return Search.search(array, predicate);
        }
        int middle = array.length / 2;
        ParallelSearch<T> left =
                new ParallelSearch<>(Arrays.copyOfRange(array, 0, middle), predicate);
        ParallelSearch<T> right =
                new ParallelSearch<>(Arrays.copyOfRange(array, middle, array.length), predicate);
        left.fork();
        right.fork();
        result.addAll(left.join());
        result.addAll(right.join().stream().map(el -> el + middle).collect(Collectors.toSet()));
        return result;
    }
}
