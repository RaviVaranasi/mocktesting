package com.availity.learning.mocktesting;

import java.util.List;

public interface UserDao {

  boolean isRegistered(String user);

  User getUser(Long id);

  User getUser(String username);

  List<User> getUserByLastName(String lastname);

  List<User> getUsers();

  User saveUser(User user);

  void remove(Long id);


}
