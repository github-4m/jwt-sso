package id.co.blogspot.fathan.security;

import id.co.blogspot.fathan.security.exception.JwtInvalidAuthenticationTokenException;
import id.co.blogspot.fathan.security.exception.UnauthorizedException;
import id.co.blogspot.fathan.security.model.JwtAuthenticationToken;
import id.co.blogspot.fathan.service.session.SessionService;
import id.co.blogspot.fathan.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.UUID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

/**
 * Created by fathan.mustaqiim on 8/25/2017.
 */
public class JwtAuthenticationProviderTest {

  private static final String DEFAULT_USERNAME = "USERNAME";
  private static final String DEFAULT_TOKEN = UUID.randomUUID().toString();
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private UserService userService;

  @Mock
  private SessionService sessionService;

  @InjectMocks
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  private Claims generateClaims() throws Exception {
    Claims claims = new DefaultClaims();
    claims.setSubject(JwtAuthenticationProviderTest.DEFAULT_USERNAME);
    return claims;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    Claims claims = this.generateClaims();
    Mockito.when(this.userService.parseJwtToken(Mockito.anyString())).thenReturn(claims);
    Mockito.when(this.sessionService.isAuthorized()).thenReturn(true);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.userService);
    Mockito.verifyNoMoreInteractions(this.sessionService);
  }

  @Test
  public void additionalAuthenticationChecksTest() throws Exception {
    this.jwtAuthenticationProvider.additionalAuthenticationChecks(null, null);
  }

  @Test
  public void supportsTest() throws Exception {
    this.jwtAuthenticationProvider.supports(Object.class);
  }

  @Test
  public void retrieveUserTest() throws Exception {
    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
        JwtAuthenticationProviderTest.DEFAULT_TOKEN);
    this.jwtAuthenticationProvider.retrieveUser(null, jwtAuthenticationToken);
    Mockito.verify(this.userService).parseJwtToken(Mockito.anyString());
    Mockito.verify(this.sessionService).isAuthorized();
  }

  @Test(expected = JwtInvalidAuthenticationTokenException.class)
  public void retrieveUserWithClaimsIsNullExceptionTest() throws Exception {
    Mockito.when(this.userService.parseJwtToken(Mockito.anyString())).thenReturn(null);
    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
        JwtAuthenticationProviderTest.DEFAULT_TOKEN);
    try {
      this.jwtAuthenticationProvider.retrieveUser(null, jwtAuthenticationToken);
    } catch (Exception e) {
      if (e instanceof JwtInvalidAuthenticationTokenException) {
        JwtInvalidAuthenticationTokenException jwtInvalidAuthenticationTokenException = (JwtInvalidAuthenticationTokenException) e;
        Assert.assertEquals("Invalid JWT token", jwtInvalidAuthenticationTokenException.getMessage());
        Mockito.verify(this.userService).parseJwtToken(Mockito.anyString());
        Mockito.verify(this.sessionService, JwtAuthenticationProviderTest.NEVER_CALLED).isAuthorized();
        throw e;
      } else {
        Assert.assertFalse(true);
      }
    }
  }

  @Test(expected = JwtInvalidAuthenticationTokenException.class)
  public void retrieveUserWithClaimsSubjectIsNullExceptionTest() throws Exception {
    Claims claims = this.generateClaims();
    claims.setSubject(null);
    Mockito.when(this.userService.parseJwtToken(Mockito.anyString())).thenReturn(claims);
    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
        JwtAuthenticationProviderTest.DEFAULT_TOKEN);
    try {
      this.jwtAuthenticationProvider.retrieveUser(null, jwtAuthenticationToken);
    } catch (Exception e) {
      if (e instanceof JwtInvalidAuthenticationTokenException) {
        JwtInvalidAuthenticationTokenException jwtInvalidAuthenticationTokenException = (JwtInvalidAuthenticationTokenException) e;
        Assert.assertEquals("Invalid JWT token", jwtInvalidAuthenticationTokenException.getMessage());
        Mockito.verify(this.userService).parseJwtToken(Mockito.anyString());
        Mockito.verify(this.sessionService, JwtAuthenticationProviderTest.NEVER_CALLED).isAuthorized();
        throw e;
      } else {
        Assert.assertFalse(true);
      }
    }
  }

  @Test(expected = UnauthorizedException.class)
  public void retrieveUserWithAuthorizationThrowExceptionTest() throws Exception {
    Mockito.doThrow(Exception.class).when(this.sessionService).isAuthorized();
    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
        JwtAuthenticationProviderTest.DEFAULT_TOKEN);
    try {
      this.jwtAuthenticationProvider.retrieveUser(null, jwtAuthenticationToken);
    } catch (Exception e) {
      if (e instanceof UnauthorizedException) {
        Mockito.verify(this.userService).parseJwtToken(Mockito.anyString());
        Mockito.verify(this.sessionService).isAuthorized();
        throw e;
      } else {
        Assert.assertFalse(true);
      }
    }
  }

  @Test(expected = UnauthorizedException.class)
  public void retrieveUserWithUnauthorizedExceptionTest() throws Exception {
    Mockito.when(this.sessionService.isAuthorized()).thenReturn(false);
    JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
        JwtAuthenticationProviderTest.DEFAULT_TOKEN);
    try {
      this.jwtAuthenticationProvider.retrieveUser(null, jwtAuthenticationToken);
    } catch (Exception e) {
      if (e instanceof UnauthorizedException) {
        Mockito.verify(this.userService).parseJwtToken(Mockito.anyString());
        Mockito.verify(this.sessionService).isAuthorized();
        throw e;
      } else {
        Assert.assertFalse(true);
      }
    }
  }

}
