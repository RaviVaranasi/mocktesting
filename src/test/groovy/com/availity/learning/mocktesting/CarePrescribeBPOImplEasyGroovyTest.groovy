package com.availity.learning.mocktesting

import com.availity.learning.mocktesting.CarePrescribeBPOImpl
import com.availity.learning.mocktesting.OrganizationDao
import com.availity.learning.mocktesting.PrematicsService
import com.availity.learning.mocktesting.UserDao
import org.junit.Test
import static org.easymock.EasyMock.*

public class CarePrescribeBPOImplEasyGroovyTest {

  private UserDao mockUserDao
  private OrganizationDao mockOrganizationDao
  private PrematicsService mockPrematicsService
  private CarePrescribeBPOImpl carePrescribeService

/*
 * Map Coercion - coalMineCanary check
 */

  @Test (expected = NullPointerException.class)
  void user_not_registered() {
    mockUserDao = [remove: {}] as UserDao
    mockOrganizationDao = [getOrganization: {"1"}] as OrganizationDao
    mockPrematicsService = [registerUser: {}, recordServiceUsage: {}] as PrematicsService
    carePrescribeService = new CarePrescribeBPOImpl(mockUserDao, mockOrganizationDao, mockPrematicsService)
    carePrescribeService.sendServiceRequest("user")
  }

  /*
   * EasyMock and Groovy in Harmony
   */

  @Test
  void sendServiceRequest_userDao_prematicsSvc_Fail() throws Exception {
    mockUserDao = createMock(UserDao.class)
    mockOrganizationDao = createMock(OrganizationDao.class)
    mockPrematicsService = createMock(PrematicsService.class)
    carePrescribeService = new CarePrescribeBPOImpl(mockUserDao,
        mockOrganizationDao, mockPrematicsService)

    expect(mockUserDao.isRegistered("user")).andReturn(Boolean.TRUE)
    replay(mockUserDao)
    mockPrematicsService.registerUser("user")
    mockPrematicsService.recordServiceUsage()
    expectLastCall().times(1)
    replay(mockPrematicsService)

    carePrescribeService.sendServiceRequest("user")
    verify mockUserDao
    verify mockPrematicsService

  }

  /*
   * Map Coercion - Coercing the UserDao - positive
   */
  @Test
  void user_should_have_cp_agent() {
    def mockReturnUser = new User()
    mockReturnUser.email = "janedoe@availity.com"
    mockReturnUser.id = 1
    mockReturnUser.firstName = "jane"
    mockReturnUser.lastName = "doe"

    // Coercion!
    mockUserDao = [getUser: {mockReturnUser}] as UserDao

    // Crazee talk..., can't do this in Java...
    carePrescribeService = new CarePrescribeBPOImpl(userDao:mockUserDao)
    def returnUser = carePrescribeService.invokeCarePrescribeAgent(1)
    assert returnUser.isCarePrescribeAgent()
  }

  /*
   * Map Coercion - Coercing the UserDao - negative
   */
  @Test
  void user_should_NOT_have_cp_agent() {
    def mockReturnUser = new User()
    mockReturnUser.email = "janedoe@gmail.com"
    mockReturnUser.id = 1
    mockReturnUser.firstName = "jane"
    mockReturnUser.lastName = "doe"

    // Coercion!
    mockUserDao = [getUser: {mockReturnUser}] as UserDao

    carePrescribeService = new CarePrescribeBPOImpl(userDao:mockUserDao)
    def returnUser = carePrescribeService.invokeCarePrescribeAgent(1)
    assert !returnUser.isCarePrescribeAgent()
  }
}
