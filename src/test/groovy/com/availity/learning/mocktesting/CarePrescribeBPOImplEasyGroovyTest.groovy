package com.availity.learning.mocktesting

import org.junit.Test
import com.availity.learning.mocktesting.*
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
    carePrescribeService = new CarePrescribeBPOImpl(userDao: mockUserDao)
    def returnUser = carePrescribeService.invokeCarePrescribeAgentFromEmail(1)
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

    carePrescribeService = new CarePrescribeBPOImpl(userDao: mockUserDao)
    def returnUser = carePrescribeService.invokeCarePrescribeAgentFromEmail(1)
    assert !returnUser.isCarePrescribeAgent()
  }

  /*
  * Map Coercion - Coercing the UserDao and OrganizationDao
  */

  @Test
  void user_should_have_cp_agent_using_lastname() {
    def mockReturnUser1 = new User()
    mockReturnUser1.email = "janedoe@availity.com"
    mockReturnUser1.id = 1
    mockReturnUser1.firstName = "jane"
    mockReturnUser1.lastName = "doe"

    def mockReturnUser2 = new User()
    mockReturnUser2.email = "johndoe@availity.com"
    mockReturnUser2.id = 2
    mockReturnUser2.firstName = "john"
    mockReturnUser2.lastName = "doe"

    //OMG!  -- Groovy's way of using Lists
    def mockReturnUsers = []
    mockReturnUsers << mockReturnUser1
    mockReturnUsers << mockReturnUser2

    // Coercion!
    mockUserDao = [getUserByLastName: {mockReturnUsers}] as UserDao
    mockOrganizationDao = [getOrganization: {CarePrescribeBPOImpl.AVAILITY_LLC}] as OrganizationDao

    carePrescribeService = new CarePrescribeBPOImpl(userDao: mockUserDao, organizationDao: mockOrganizationDao)
    def returnUsers = carePrescribeService.invokeCarePrescribeAgentFromOrganization("doe")

    //WTH!
    returnUsers.each {
      assert it.isCarePrescribeAgent()
    }
  }
}
