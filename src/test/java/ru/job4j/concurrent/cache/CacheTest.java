package ru.job4j.concurrent.cache;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class CacheTest {

    @Test
    public void whenAddTwoThenOk() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        Base user2 = new Base(2, 1);
        user1.setName("Ivan");
        user2.setName("Boris");
        assertTrue(cache.add(user1));
        assertTrue(cache.add(user2));
        assertThat(cache.get(1).getName(), is("Ivan"));
        assertThat(cache.get(2).getName(), is("Boris"));
    }

    @Test
    public void whenAddAndDeleteThenGetNull() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        user1.setName("Ivan");
        cache.add(user1);
        cache.delete(user1);
        assertThat(cache.get(1), IsNull.nullValue());
    }

    @Test
    public void whenAddAndUpdateThenOk() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        user1.setName("Ivan");
        cache.add(user1);
        user1.setName("Petr");
        assertTrue(cache.update(user1));
        assertThat(cache.get(1).getName(), is("Petr"));
    }

    @Test(expected = OptimisticException.class)
    public void whenAddOneAndUpdateWithWrongVersionThenThrowException() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        Base user2 = new Base(1, 2);
        cache.add(user1);
        cache.update(user2);
    }

    @Test(expected = OptimisticException.class)
    public void whenAddAndTryUpdateTwiceThenThrowException() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        Base user2 = new Base(1, 1);
        Base user3 = new Base(1, 1);
        cache.add(user1);
        cache.update(user2);
        cache.update(user3);
    }

    @Test
    public void whenAddAndTryUpdateTwiceThenOk() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        Base user2 = new Base(1, 1);
        Base user3 = new Base(1, 2);
        assertTrue(cache.add(user1));
        assertTrue(cache.update(user2));
        assertTrue(cache.update(user3));
    }
}