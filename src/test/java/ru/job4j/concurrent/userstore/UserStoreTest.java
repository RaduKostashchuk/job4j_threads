package ru.job4j.concurrent.userstore;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserStoreTest {

    @Test
    public void whenAddUser() {
        UserStore store = new UserStore();
        User user = new User(1, 100);
        store.add(user);
        assertThat(store.get(1).getAmount(), is(100));
    }

    @Test
    public void whenDeleteUser() {
        UserStore store = new UserStore();
        User user = new User(1, 100);
        store.add(user);
        store.delete(user);
        assertThat(store.get(1), IsNull.nullValue());
    }

    @Test
    public void whenTransfer() {
        UserStore store = new UserStore();
        User user1 = new User(1, 100);
        User user2 = new User(10, 300);
        store.add(user1);
        store.add(user2);
        store.transfer(1, 10, 10);
        assertThat(store.get(1).getAmount(), is(90));
        assertThat(store.get(10).getAmount(), is(310));
    }
}