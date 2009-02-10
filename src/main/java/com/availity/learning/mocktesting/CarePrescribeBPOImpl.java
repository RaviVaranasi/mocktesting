package com.availity.learning.mocktesting;

public class CarePrescribeBPOImpl {

  private final UserDao userDao;
  private final OrganizationDao organizationDao;
  private final PrematicsService prematicsService;


  public CarePrescribeBPOImpl(UserDao userDao,
    OrganizationDao organizationDao, PrematicsService prematicsService) {
    super();
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

}
