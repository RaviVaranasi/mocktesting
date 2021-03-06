package com.availity.learning.mocktesting

import org.hibernate.SessionFactory
import org.springframework.orm.hibernate3.HibernateTemplate
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */

public class UserDaoTest extends AbstractTransactionalDataSourceSpringContextTests {

  protected String[] getConfigLocations() {
    setAutowireMode(AUTOWIRE_BY_NAME)
    setDependencyCheck(false)
    def returnString
    return ["classpath:/applicationContext-resources.xml"] as String[]
  }

  private UserDao dao = null

  public void setUserDao(def dao) {
    this.dao = dao
  }

  public void testGetUser() throws Exception {
    def user = dao.getUser(-1L);

    assert user != null
    assert user.getId() != null
  }

  public void testAddAndRemoveUser() throws Exception {
    def user = new User("testuser")
    user.password = "testpass"
    user.firstName = "Test"
    user.lastName = "Last";
    user.email = "testuser@appfuse.org";

    user = dao.saveUser(user);
    flush();
    assert user.getId() != null

    user = dao.getUser(user.getId())
    assert "testpass" == user.password

    dao.remove(user.getId());
    flush();

    def userAgain = dao.getUser(user.getId());

    assert "testpass" != userAgain.password
  }

  protected void flush() {
    HibernateTemplate hibernateTemplate =
    new HibernateTemplate((SessionFactory) applicationContext.getBean("sessionFactory"));
    hibernateTemplate.flush();
    hibernateTemplate.clear();
  }
}
