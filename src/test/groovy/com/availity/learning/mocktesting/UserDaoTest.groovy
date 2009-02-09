package com.availity.learning.mocktesting

import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */

public class UserDaoTest extends AbstractTransactionalDataSourceSpringContextTests {

  protected String[] getConfigLocations() {
    setAutowireMode(AUTOWIRE_BY_NAME);
    def returnString
    return ["classpath:/applicationContext-resources.xml"] as String[]
  }

  private UserDao dao = null;

  public void setUserDao(UserDao dao) {
    this.dao = dao;
  }

  public void testGetUserInvalid() throws Exception {
    try {
      dao.get(1000L);
      fail("'badusername' found in database, failing test...");
    } catch (DataAccessException d) {
      assertTrue(d != null);
    }
  }

  public void testGetUser() throws Exception {
    User user = dao.get(-1L);

    assertNotNull(user);
    assertEquals(1, user.getRoles().size());
    assertTrue(user.isEnabled());
  }

  public void testGetUserPassword() throws Exception {
    User user = dao.get(-1L);
    String password = dao.getUserPassword(user.getUsername());
    assertNotNull(password);
    log.debug("password: " + password);
  }

  public void testUpdateUser() throws Exception {
    User user = dao.get(-1L);


    dao.saveUser(user);
    flush();

    user = dao.get(-1L);
    assertEquals(address, user.getAddress());
    assertEquals("new address", user.getAddress().getAddress());

    // verify that violation occurs when adding new user with same username
    user.setId(null);

    endTransaction();

    try {
      dao.saveUser(user);
      flush();
      fail("saveUser didn't throw DataIntegrityViolationException");
    } catch (DataIntegrityViolationException e) {
      assertNotNull(e);
      log.debug("expected exception: " + e.getMessage());
    }
  }

  public void testAddAndRemoveUser() throws Exception {
    User user = new User("testuser");
    user.setPassword("testpass");
    user.setFirstName("Test");
    user.setLastName("Last");
    user.setAddress(address);
    user.setEmail("testuser@appfuse.org");
    user.setWebsite("http://raibledesigns.com");

    user = dao.saveUser(user);
    flush();

    assertNotNull(user.getId());
    user = dao.get(user.getId());
    assertEquals("testpass", user.getPassword());

    dao.remove(user.getId());
    flush();

    try {
      dao.get(user.getId());
      fail("getUser didn't throw DataAccessException");
    } catch (DataAccessException d) {
      assertNotNull(d);
    }
  }

  public void testUserExists() throws Exception {
    boolean b = dao.exists(-1L);
    assertTrue(b);
  }

  public void testUserNotExists() throws Exception {
    boolean b = dao.exists(111L);
    assertFalse(b);
  }

}
