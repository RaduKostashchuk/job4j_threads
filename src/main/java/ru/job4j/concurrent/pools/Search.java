package ru.job4j.concurrent.pools;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Search {

    public static <T> Set<Integer> search(T[] input, Predicate<T> predicate) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < input.length; i++) {
            if (predicate.test(input[i])) {
                result.add(i);
            }
        }
        return result;
    }
}
