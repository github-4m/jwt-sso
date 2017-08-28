package id.co.blogspot.fathan.security.aspect;

import id.co.blogspot.fathan.security.annotation.Authorize;
import id.co.blogspot.fathan.security.exception.UnauthorizedException;
import id.co.blogspot.fathan.util.Credential;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
@Aspect
public class AuthorizationAspect {

  @Around(value = "@annotation(id.co.blogspot.fathan.security.annotation.Authorize)")
  public Object authorize(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    String[] roles = Credential.getRoles();
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    Method method = methodSignature.getMethod();
    Authorize authorize = method.getAnnotation(Authorize.class);
    boolean isAuthorized = false;
    for (String role : roles) {
      if (Arrays.asList(authorize.roles()).contains(role)) {
        isAuthorized = true;
        break;
      }
    }
    if (!isAuthorized) {
      throw new UnauthorizedException("Unauthorized api access");
    }
    return proceedingJoinPoint.proceed();
  }

}
