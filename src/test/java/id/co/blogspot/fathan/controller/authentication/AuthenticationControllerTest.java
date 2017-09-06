package id.co.blogspot.fathan.controller.authentication;

import id.co.blogspot.fathan.dto.cas.CasLogoutRequest;
import id.co.blogspot.fathan.service.user.UserService;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by fathan.mustaqiim on 10/28/2016.
 */
public class AuthenticationControllerTest {

  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();
  private static final String DEFAULT_TICKET = UUID.randomUUID().toString();

  @Mock
  private UserService userService;

  @InjectMocks
  private AuthenticationController authenticationController;

  private CasLogoutRequest generateCasLogoutRequest() throws Exception {
    CasLogoutRequest casLogoutRequest = new CasLogoutRequest();
    return casLogoutRequest;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    Mockito.when(this.userService.authenticate(Mockito.anyString(), Mockito.any(HttpServletResponse.class)))
        .thenReturn(null);
    Mockito.doNothing().when(this.userService).unauthenticate(Mockito.anyString());
    Mockito.doNothing().when(this.userService).unauthenticate(Mockito.any(HttpServletResponse.class));
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.userService);
  }

  @Test
  public void authenticateTest() throws Exception {
    this.authenticationController.authenticate(
        AuthenticationControllerTest.DEFAULT_REQUEST_ID, AuthenticationControllerTest.DEFAULT_TICKET, null);
    Mockito.verify(this.userService).authenticate(Mockito.anyString(), Mockito.any(HttpServletResponse.class));
  }

  @Test
  public void unauthenticateCallbackTest() throws Exception {
    this.authenticationController.unauthenticate(this.generateCasLogoutRequest());
    Mockito.verify(this.userService).unauthenticate(Mockito.anyString());
  }

  @Test
  public void unauthenticateTest() throws Exception {
    this.authenticationController.unauthenticate(AuthenticationControllerTest.DEFAULT_REQUEST_ID, null);
    Mockito.verify(this.userService).unauthenticate(Mockito.any(HttpServletResponse.class));
  }
}
