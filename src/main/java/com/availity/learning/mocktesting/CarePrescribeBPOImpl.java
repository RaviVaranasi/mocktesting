package com.availity.learning.mocktesting;

public class CarePrescribeBPOImpl  {
	
	private UserDao userDao;
	private OrganizationDao organizationDao;
	private PrematicsService prematicsService;
	
	public void sendServiceRequest(String user)  {
	    try {
	      //Send Organization request if necessary
	      boolean isSiteRegistered = userDao.isRegistered(user);
	      if (!isSiteRegistered) {
	    	  String organization = organizationDao.getOrganization(user);
	    	  prematicsService.registerOrganization(organization);
	      }
	      prematicsService.registerUser(user);
	    } catch (final PrematicsException e) {
	      throw new RuntimeException(e);
	    }
	  }

}
