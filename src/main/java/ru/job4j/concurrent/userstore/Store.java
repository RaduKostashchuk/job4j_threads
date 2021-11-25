package ru.job4j.concurrent.userstore;

public interface Store {

    boolean add(User user);
    User get(int id);
    boolean delete(User user);
}
