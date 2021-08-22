package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    List<User> listByUsernameAndUserId();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    public User findUserById(int id);

    public User getUserTo(int account_id);

    public User getUserFrom(int account_id);

    public User getUserFromAccountId(int account_id);
}
