package com.availity.learning.mocktesting;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.Collections;
import java.util.List;

/**
 */
public class UserRepository extends HibernateDaoSupport implements UserDao {

  @SuppressWarnings("unchecked")
  public User getUser(Long id) {
    List<User> users = getHibernateTemplate().find("from User u where id=?", id);
    return users != null && !users.isEmpty() ? users.get(0) : new User();
  }

  @SuppressWarnings("unchecked")
  public User getUser(String username) {
    List<User> users = getHibernateTemplate().find("from User u where username=?", username);
    return users != null && !users.isEmpty() ? users.get(0) : new User();
  }

  @SuppressWarnings("unchecked")
  public List<User> getUsers() {
    return getHibernateTemplate().find("from User u order by upper(u.username)");
  }

  public User saveUser(User user) {
    getHibernateTemplate().saveOrUpdate(user);
    getHibernateTemplate().flush();
    return user;
  }

  public void remove(Long id) {
    getHibernateTemplate().delete(this.getUser(id));
  }

  public boolean isRegistered(String user) {
    return false;
  }
}

