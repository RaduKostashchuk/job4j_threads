package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;

public class TriggerTest {

    @Test
    public void runTest() {
        Trigger tr = new Trigger();
        assertTrue(tr.run());
    }

}