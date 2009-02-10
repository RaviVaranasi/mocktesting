package com.availity.learning.mocktesting;

import static org.easymock.EasyMock.*;

import org.junit.Test;

public class CarePrescribeBPOImplEasyTest {

    @Test(expected = AssertionError.class)
	public void testUserNotRegistered() {
		UserDao mockUserDao = createMock(UserDao.class);
		OrganizationDao mockOrganizationDao = createMock(OrganizationDao.class);
		PrematicsService mockPrematicsService = createMock(PrematicsService.class);
		CarePrescribeBPOImpl carePrescribeService = new CarePrescribeBPOImpl(mockUserDao,
				mockOrganizationDao, mockPrematicsService);
		
		replay(mockUserDao);
		carePrescribeService.sendServiceRequest("user");
		
	}

    @Test
	public void test_SendServiceRequest_UserDao_PrematicsSvc() throws Exception {
		UserDao mockUserDao = createMock(UserDao.class);
		OrganizationDao mockOrganizationDao = createMock(OrganizationDao.class);
		PrematicsService mockPrematicsService = createMock(PrematicsService.class);
		CarePrescribeBPOImpl carePrescribeService = new CarePrescribeBPOImpl(mockUserDao,
				mockOrganizationDao, mockPrematicsService);
		
		expect(mockUserDao.isRegistered("user")).andReturn(Boolean.TRUE);
		replay(mockUserDao);
		mockPrematicsService.registerUser("user");
		mockPrematicsService.recordServiceUsage();
		expectLastCall().times(1);
		replay(mockPrematicsService);
		
		carePrescribeService.sendServiceRequest("user");
		verify(mockUserDao);
		verify(mockPrematicsService);
		
	}

    @Test
	public void test_SendServiceRequest_UserDao_PrematicsSvc_Fail() throws Exception {
		UserDao mockUserDao = createMock(UserDao.class);
		OrganizationDao mockOrganizationDao = createMock(OrganizationDao.class);
		PrematicsService mockPrematicsService = createMock(PrematicsService.class);
		CarePrescribeBPOImpl carePrescribeService = new CarePrescribeBPOImpl(mockUserDao,
				mockOrganizationDao, mockPrematicsService);
		
		expect(mockUserDao.isRegistered("user")).andReturn(Boolean.TRUE);
		replay(mockUserDao);
		//mockPrematicsService.registerOrganization("org");
		mockPrematicsService.registerUser("user");
		mockPrematicsService.recordServiceUsage();
		expectLastCall().times(1);
		replay(mockPrematicsService);
		
		carePrescribeService.sendServiceRequest("user");
		verify(mockUserDao);
		verify(mockPrematicsService);
		
	}

    @Test
	public void test_SendServiceRequest_UserDao_PrematicsSvc_OrgDao() throws Exception {
		UserDao mockUserDao = createMock(UserDao.class);
		OrganizationDao mockOrganizationDao = createMock(OrganizationDao.class);
		PrematicsService mockPrematicsService = createMock(PrematicsService.class);
		CarePrescribeBPOImpl carePrescribeService = new CarePrescribeBPOImpl(mockUserDao,
				mockOrganizationDao, mockPrematicsService);
		
		expect(mockUserDao.isRegistered("user")).andReturn(Boolean.FALSE);
		replay(mockUserDao);
		expect(mockOrganizationDao.getOrganization("user")).andReturn("org");
		replay(mockOrganizationDao);
		mockPrematicsService.registerOrganization("org");
		mockPrematicsService.registerUser("user");
		mockPrematicsService.recordServiceUsage();
		expectLastCall().times(2);
		replay(mockPrematicsService);
		
		carePrescribeService.sendServiceRequest("user");
		verify(mockUserDao);
		verify(mockPrematicsService);
		
	}

    @Test
	public void test_SendServiceRequest_UserDao_PrematicsSvc_OrgDao_Fail() throws Exception {
		UserDao mockUserDao = createMock(UserDao.class);
		OrganizationDao mockOrganizationDao = createMock(OrganizationDao.class);
		PrematicsService mockPrematicsService = createMock(PrematicsService.class);
		CarePrescribeBPOImpl carePrescribeService = new CarePrescribeBPOImpl(mockUserDao,
				mockOrganizationDao, mockPrematicsService);
		
		expect(mockUserDao.isRegistered("user")).andReturn(Boolean.FALSE);
		replay(mockUserDao);
		expect(mockOrganizationDao.getOrganization("user")).andReturn("org");
		replay(mockOrganizationDao);
		mockPrematicsService.registerOrganization("org");
		mockPrematicsService.registerUser("user");
		mockPrematicsService.recordServiceUsage();
		expectLastCall().times(2);
		replay(mockPrematicsService);
		
		carePrescribeService.sendServiceRequest("user");
		verify(mockUserDao);
		verify(mockPrematicsService);
		
	}

}
