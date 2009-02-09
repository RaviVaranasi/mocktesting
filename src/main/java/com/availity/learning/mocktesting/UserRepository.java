package com.availity.learning.mocktesting;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.persistence.Table;
import java.util.List;

/**
 */
public class UserRepository extends HibernateDaoSupport implements UserDao {

  @SuppressWarnings("unchecked")
  public User getUser(String username) {
    return (User) getHibernateTemplate().find("from User u where username=?", username);
  }

  @SuppressWarnings("unchecked")
  public List<User> getUsers() {
    return getHibernateTemplate().find("from User u order by upper(u.username)");
  }

  public User saveUser(User user) {
    getHibernateTemplate().saveOrUpdate(user);
    // necessary to throw a DataIntegrityViolation and catch it in UserManager
    getHibernateTemplate().flush();
    return user;
  }


  public boolean isRegistered(String user) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }
}

