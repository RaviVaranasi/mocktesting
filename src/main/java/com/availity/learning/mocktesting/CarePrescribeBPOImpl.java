package com.availity.learning.mocktesting;

import java.util.List;

public class CarePrescribeBPOImpl {

  private final UserDao userDao;
  private final OrganizationDao organizationDao;
  private final PrematicsService prematicsService;
  protected static final String AVAILITY_LLC = "Availity LLC";


  public CarePrescribeBPOImpl() {
    userDao = new UserRepository();
    organizationDao = new OrganizationDaoImpl();
    prematicsService = new PrematicsWebServiceImpl();

  }

  public CarePrescribeBPOImpl(UserDao userDao, OrganizationDao organizationDao, PrematicsService prematicsService) {
    this.userDao = userDao;
    this.organizationDao = organizationDao;
    this.prematicsService = prematicsService;
  }


  public void sendServiceRequest(String user) {
    try {
      //Send Organization request if necessary
      boolean isSiteRegistered = userDao.isRegistered(user);
      if (!isSiteRegistered) {
        String organization = organizationDao.getOrganization(user);
        prematicsService.registerOrganization(organization);
        prematicsService.recordServiceUsage();
      }
      prematicsService.registerUser(user);
      prematicsService.recordServiceUsage();
    } catch (final PrematicsException e) {
      throw new RuntimeException(e);
    }
  }

  public User invokeCarePrescribeAgent(Long userId) {
    User user = userDao.getUser(userId);
    if (user.getEmail().contains("availity.com")) {
      user.setCarePrescribeAgent(true);
    } else {
      user.setCarePrescribeAgent(false);
    }
    return user;
  }

  public List<User> invokeCarePrescribeAgent(String lastname) {
    List<User> users = userDao.getUserByLastName(lastname);
    for (User user : users) {
      String organization = organizationDao.getOrganization(user.getId().toString());
      if (AVAILITY_LLC.equals(organization)) {
        user.setCarePrescribeAgent(true);
      } else {
        user.setCarePrescribeAgent(false);
      }
    }
    return users;
  }
}
