package com.availity.learning.mocktesting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnit44Runner;

@RunWith(MockitoJUnit44Runner.class)
public class CarePrescribeBPOImplTest {

  @Mock
  UserDao userDao;
  @Mock
  OrganizationDao organizationDao;
  @Mock
  PrematicsService prematicsService;


  @Test
  public void shouldTestUsingMockitExample() throws PrematicsException {
    when(userDao.isRegistered("user")).thenReturn(Boolean.TRUE);


    CarePrescribeBPOImpl carePrescribeService = new CarePrescribeBPOImpl(userDao,
      organizationDao, prematicsService);
    carePrescribeService.sendServiceRequest("user");
    verify(prematicsService).registerUser("user");
  }

}
