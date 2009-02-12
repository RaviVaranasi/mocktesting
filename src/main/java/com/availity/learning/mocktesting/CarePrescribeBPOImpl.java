package com.availity.learning.mocktesting;

public class CarePrescribeBPOImpl {

  private final UserDao userDao;
  private final OrganizationDao organizationDao;
  private final PrematicsService prematicsService;


  public CarePrescribeBPOImpl() {
    userDao = new UserRepository();
    organizationDao = new OrganizationDaoImpl();
    prematicsService = new PrematicsWebServiceImpl();

  }

  public CarePrescribeBPOImpl(UserDao userDao,
                              OrganizationDao organizationDao, PrematicsService prematicsService) {
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
      user.isCarePrescribeAgent(true);
    } else {
      user.isCarePrescribeAgent(false);
    }
    return user;
  }
}
