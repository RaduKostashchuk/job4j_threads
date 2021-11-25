package ru.job4j.concurrent.userstore;

public interface Store {

    boolean add(User user);
    boolean update(User user);
    boolean delete(User user);
    User get(int id);
}
