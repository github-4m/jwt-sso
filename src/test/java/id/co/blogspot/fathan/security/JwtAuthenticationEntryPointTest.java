package id.co.blogspot.fathan.security;

import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by fathan.mustaqiim on 8/25/2017.
 */
public class JwtAuthenticationEntryPointTest {

  @Mock
  private HttpServletResponse httpServletResponse;

  @InjectMocks
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    Mockito.doNothing().when(this.httpServletResponse).sendError(Mockito.anyInt(), Mockito.anyString());
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.httpServletResponse);
  }

  @Test
  public void commenceTest() throws Exception {
    this.jwtAuthenticationEntryPoint.commence(null, this.httpServletResponse, null);
    Mockito.verify(this.httpServletResponse).sendError(Mockito.anyInt(), Mockito.anyString());
  }

}
