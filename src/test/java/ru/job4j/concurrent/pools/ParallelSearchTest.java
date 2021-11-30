package ru.job4j.concurrent.pools;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParallelSearchTest {

    @Test
    public void whenSearchInteger() {
        Integer[] array = {23, 1, 3, 4, 23, 34, 12, 45, 78, 3, 23, 34, 0, 22, 23, 135, 5757, 23, 23};
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Set<Integer> result = pool.invoke(new ParallelSearch<>(array, 0, array.length - 1, el -> el.equals(23)));
        Set<Integer> expected = Set.of(0, 4, 10, 14, 17, 18);
        assertThat(result, is(expected));
    }

    @Test
    public void whenSearchNotExistent() {
    Integer[] array = {23, 1, 3, 4, 23, 34, 12, 45, 78, 3, 23, 34, 0, 22, 23, 135, 5757, 23, 23};
    ForkJoinPool pool = ForkJoinPool.commonPool();
    Set<Integer> result = pool.invoke(new ParallelSearch<>(array, 0, array.length - 1, el -> el.equals(111)));
    Set<Integer> expected = new HashSet<>();
    assertThat(result, is(expected));
    }

}