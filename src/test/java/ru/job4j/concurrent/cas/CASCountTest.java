package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CASCountTest {

    @Test
    public void whenIncrement() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        count.increment();
        assertThat(count.get(), is(3));
    }

}