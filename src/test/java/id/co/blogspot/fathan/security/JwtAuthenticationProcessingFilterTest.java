package id.co.blogspot.fathan.security;

import id.co.blogspot.fathan.security.exception.JwtInvalidAuthenticationTokenException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

/**
 * Created by fathan.mustaqiim on 8/25/2017.
 */
public class JwtAuthenticationProcessingFilterTest {

  private static final String DEFAULT_JWT_TOKEN = UUID.randomUUID().toString();
  private static final VerificationMode CALLED_TWICE = Mockito.times(2);
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private HttpServletRequest httpServletRequest;

  @Mock
  private HttpServletResponse httpServletResponse;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private FilterChain filterChain;

  @Mock
  private Authentication authentication;

  @InjectMocks
  private JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter;

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    jwtAuthenticationProcessingFilter.setAuthenticationManager(this.authenticationManager);
    Mockito.when(this.httpServletRequest.getHeader(Mockito.anyString()))
        .thenReturn(JwtAuthenticationProcessingFilterTest.DEFAULT_JWT_TOKEN);
    Mockito.when(this.authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(null);
    Mockito.when(this.httpServletRequest.getSession(Mockito.anyBoolean())).thenReturn(null);
    Mockito.when(this.httpServletResponse.isCommitted()).thenReturn(true);
    Mockito.doNothing().when(this.filterChain)
        .doFilter(Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class));
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.httpServletRequest);
    Mockito.verifyNoMoreInteractions(this.httpServletResponse);
    Mockito.verifyNoMoreInteractions(this.authenticationManager);
    Mockito.verifyNoMoreInteractions(this.filterChain);
    Mockito.verifyNoMoreInteractions(this.authentication);
  }

  @Test
  public void attemptAuthenticationTest() throws Exception {
    this.jwtAuthenticationProcessingFilter.attemptAuthentication(this.httpServletRequest, this.httpServletResponse);
    Mockito.verify(this.httpServletRequest).getHeader(Mockito.anyString());
    Mockito.verify(this.authenticationManager).authenticate(Mockito.any(Authentication.class));
  }

  @Test(expected = JwtInvalidAuthenticationTokenException.class)
  public void attemptAuthenticationWithJwtInvalidAuthenticationTokenExceptionTest() throws Exception {
    Mockito.when(this.httpServletRequest.getHeader(Mockito.anyString())).thenReturn(null);
    try {
      this.jwtAuthenticationProcessingFilter.attemptAuthentication(this.httpServletRequest, this.httpServletResponse);
    } catch (Exception e) {
      if (e instanceof JwtInvalidAuthenticationTokenException) {
        JwtInvalidAuthenticationTokenException jwtInvalidAuthenticationTokenException = (JwtInvalidAuthenticationTokenException) e;
        Assert.assertEquals("Invalid JWT token", jwtInvalidAuthenticationTokenException.getMessage());
        Mockito.verify(this.httpServletRequest).getHeader(Mockito.anyString());
        Mockito.verify(this.authenticationManager, JwtAuthenticationProcessingFilterTest.NEVER_CALLED)
            .authenticate(Mockito.any(Authentication.class));
        throw e;
      } else {
        Assert.assertFalse(true);
      }
    }
  }

  @Test
  public void requiresAuthenticationTest() throws Exception {
    this.jwtAuthenticationProcessingFilter.requiresAuthentication(this.httpServletRequest, this.httpServletResponse);
  }

  @Test
  public void successfulAuthenticationTest() throws Exception {
    this.jwtAuthenticationProcessingFilter
        .successfulAuthentication(this.httpServletRequest, this.httpServletResponse, this.filterChain,
            this.authentication);
    Mockito.verify(this.httpServletRequest, JwtAuthenticationProcessingFilterTest.CALLED_TWICE)
        .getSession(Mockito.anyBoolean());
    Mockito.verify(this.httpServletResponse).isCommitted();
    Mockito.verify(this.filterChain)
        .doFilter(Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class));
  }

}
