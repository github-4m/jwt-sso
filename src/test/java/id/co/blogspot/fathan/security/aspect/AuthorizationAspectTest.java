package id.co.blogspot.fathan.security.aspect;

import id.co.blogspot.fathan.security.annotation.Authorize;
import id.co.blogspot.fathan.security.exception.UnauthorizedException;
import id.co.blogspot.fathan.util.Credential;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
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
public class AuthorizationAspectTest {

  private static final String DEFAULT_ROLE_1 = "ROLE_1";
  private static final String DEFAULT_ROLE_2 = "ROLE_2";
  private static final VerificationMode NEVER_CALLED = Mockito.times(0);

  @Mock
  private ProceedingJoinPoint proceedingJoinPoint;

  @Mock
  private MethodSignature methodSignature;

  @Mock
  private Authorize authorize;

  @InjectMocks
  private AuthorizationAspect authorizationAspect;

  private Method method;

  @Authorize(roles = {AuthorizationAspectTest.DEFAULT_ROLE_2})
  public void method() throws Exception {
  }

  @Before
  public void initializeTest() throws Throwable {
    MockitoAnnotations.initMocks(this);
    method = AuthorizationAspectTest.class.getMethod("method");
    Mockito.when(this.proceedingJoinPoint.getSignature()).thenReturn(this.methodSignature);
    Mockito.when(this.methodSignature.getMethod()).thenReturn(this.method);
    Mockito.when(this.proceedingJoinPoint.proceed()).thenReturn(null);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.proceedingJoinPoint);
    Mockito.verifyNoMoreInteractions(this.methodSignature);
  }

  @Test
  public void authorizeTest() throws Throwable {
    Credential.setRoles(new String[]{AuthorizationAspectTest.DEFAULT_ROLE_1, AuthorizationAspectTest.DEFAULT_ROLE_2});
    this.authorizationAspect.authorize(this.proceedingJoinPoint);
    Mockito.verify(this.proceedingJoinPoint).getSignature();
    Mockito.verify(this.methodSignature).getMethod();
    Mockito.verify(this.proceedingJoinPoint).proceed();
  }

  @Test(expected = UnauthorizedException.class)
  public void authorizeWithUnauthorizedExceptionTest() throws Throwable {
    Credential.setRoles(new String[]{AuthorizationAspectTest.DEFAULT_ROLE_1});
    try {
      this.authorizationAspect.authorize(this.proceedingJoinPoint);
    } catch (Exception e) {
      if (e instanceof UnauthorizedException) {
        UnauthorizedException unauthorizedException = (UnauthorizedException) e;
        Assert.assertEquals("Unauthorized api access", unauthorizedException.getMessage());
        Mockito.verify(this.proceedingJoinPoint).getSignature();
        Mockito.verify(this.methodSignature).getMethod();
        Mockito.verify(this.proceedingJoinPoint, AuthorizationAspectTest.NEVER_CALLED).proceed();
        throw e;
      } else {
        Assert.assertFalse(true);
      }
    }
  }

}
