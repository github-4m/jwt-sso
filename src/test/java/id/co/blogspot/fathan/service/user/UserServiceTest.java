package id.co.blogspot.fathan.service.user;

import id.co.blogspot.fathan.dto.cas.CasAuthenticationSuccess;
import id.co.blogspot.fathan.outbound.cas.CasOutbound;
import id.co.blogspot.fathan.service.session.SessionService;
import id.co.blogspot.fathan.util.Credential;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public class UserServiceTest {

  private static final String DEFAULT_USERNAME_1 = "DEVELOPER";
  private static final String DEFAULT_SESSION_ID_1 = UUID.randomUUID().toString();
  private static final String DEFAULT_TICKET = UUID.randomUUID().toString();

  @Mock
  private SessionService sessionService;

  @Mock
  private CasOutbound casOutbound;

  @InjectMocks
  private UserServiceBean userServiceBean;

  private String jwtToken;

  private void generateJwtSecretKey() throws Exception {
    ReflectionTestUtils.setField(
        this.userServiceBean, "jwtSecretKey", UUID.randomUUID().toString());
  }

  private CasAuthenticationSuccess generateCasAuthenticationSuccess() throws Exception {
    CasAuthenticationSuccess casAuthenticationSuccess = new CasAuthenticationSuccess();
    return casAuthenticationSuccess;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.generateJwtSecretKey();
    Credential.setSessionId(UserServiceTest.DEFAULT_SESSION_ID_1);
    CasAuthenticationSuccess casAuthenticationSuccess = this.generateCasAuthenticationSuccess();
    this.jwtToken = this.userServiceBean.generateJwtToken(UserServiceTest.DEFAULT_USERNAME_1);
    Mockito.doNothing().when(this.casOutbound).authenticate(Mockito.any(HttpServletResponse.class));
    Mockito.doNothing().when(this.casOutbound).authenticate(Mockito.any(HttpServletResponse.class));
    Mockito.when(this.casOutbound.validate(Mockito.anyString())).thenReturn(casAuthenticationSuccess);
    Mockito.doNothing().when(this.sessionService).create(Mockito.anyString());
    Mockito.doNothing().when(this.sessionService).remove();
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.sessionService);
    Mockito.verifyNoMoreInteractions(this.casOutbound);
  }

  @Test
  public void authenticateTest() throws Exception {
    this.userServiceBean.authenticate(UserServiceTest.DEFAULT_TICKET, null);
    Mockito.verify(this.casOutbound).validate(Mockito.anyString());
    Mockito.verify(this.sessionService).create(Mockito.anyString());
  }

  @Test
  public void authenticateWithTicketIsNullTest() throws Exception {
    this.userServiceBean.authenticate(null, null);
    Mockito.verify(this.casOutbound).authenticate(Mockito.any(HttpServletResponse.class));
    Mockito.verify(this.casOutbound).validate(Mockito.anyString());
    Mockito.verify(this.sessionService).create(Mockito.anyString());
  }

  @Test
  public void parseJwtTokenTest() throws Exception {
    this.userServiceBean.parseJwtToken(this.jwtToken);
  }

  @Test
  public void parseJwtTokenWithNullReturnTest() throws Exception {
    this.userServiceBean.parseJwtToken(UUID.randomUUID().toString());
  }

  @Test
  public void unauthenticateTest() throws Exception {
    this.userServiceBean.unauthenticate();
    Mockito.verify(this.sessionService).remove();
  }
}
