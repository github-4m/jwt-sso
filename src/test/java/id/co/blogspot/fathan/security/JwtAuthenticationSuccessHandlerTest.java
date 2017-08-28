package id.co.blogspot.fathan.security;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Created by fathan.mustaqiim on 8/25/2017.
 */
public class JwtAuthenticationSuccessHandlerTest {

  @InjectMocks
  private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void onAuthenticationSuccessTest() throws Exception {
    this.jwtAuthenticationSuccessHandler.onAuthenticationSuccess(null, null, null);
  }

}
