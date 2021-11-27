package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CASStackTest {

    @Test
    public void when3PushThen3Poll() {
        CASStack<Integer> stack = new CASStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThat(stack.poll(), is(3));
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));
    }

    @Test
    public void when1PushThen1Poll() {
        CASStack<Integer> stack = new CASStack<>();
        stack.push(1);
        assertThat(stack.poll(), is(1));
    }

    @Test
    public void when2PushThen2Poll() {
        CASStack<Integer> stack = new CASStack<>();
        stack.push(1);
        stack.push(2);
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));
    }

}