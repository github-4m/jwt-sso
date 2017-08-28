package id.co.blogspot.fathan.configuration;

import id.co.blogspot.fathan.security.aspect.AuthorizationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {

  @Bean
  public AuthorizationAspect authorizationAspect() throws Exception {
    return new AuthorizationAspect();
  }

}
