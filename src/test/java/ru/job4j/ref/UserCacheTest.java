package ru.job4j.ref;

import org.junit.Test;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserCacheTest {

    @Test
    public void whenGetAllFromCacheAndChangeThenLocalRemainUnchanged() {
        UserCache cache = new UserCache();
        User u1 = User.of("u1");
        User u2 = User.of("u2");
        cache.add(u1);
        cache.add(u2);
        List<User> fromCache = cache.findAll();
        fromCache.get(0).setName("Ivan");
        fromCache.get(1).setName("Petr");
        assertThat(cache.findById(1).getName(), is("u1"));
        assertThat(cache.findById(2).getName(), is("u2"));
    }

}