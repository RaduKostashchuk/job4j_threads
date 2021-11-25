package ru.job4j.synch;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>();
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void addToInstantiatedFromList() throws InterruptedException {
        LinkedList<Integer> source = new LinkedList<>();
        source.add(1);
        source.add(2);
        SingleLockList<Integer> list = new SingleLockList<>(source);
        Thread first = new Thread(() -> list.add(3));
        Thread second = new Thread(() -> list.add(4));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2, 3, 4)));
    }

}