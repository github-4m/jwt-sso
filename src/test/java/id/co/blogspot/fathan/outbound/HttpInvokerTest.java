package id.co.blogspot.fathan.outbound;

import java.net.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fathan.mustaqiim on 8/24/2017.
 */
public class HttpInvokerTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private HttpInvoker httpInvoker;

  private ResponseEntity generateResponseEntity() throws Exception {
    return new ResponseEntity(HttpStatus.OK);
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    ResponseEntity responseEntity = this.generateResponseEntity();
    Mockito.when(this.restTemplate.exchange(Mockito.any(URI.class), Mockito.any(HttpMethod.class), Mockito.any(
        HttpEntity.class), Mockito.any(Class.class))).thenReturn(responseEntity);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.restTemplate);
  }

  @Test
  public void invokeTest() throws Exception {
    this.httpInvoker.invoke(null, null, null);
    Mockito.verify(this.restTemplate).exchange(Mockito.any(URI.class), Mockito.any(HttpMethod.class), Mockito.any(
        HttpEntity.class), Mockito.any(Class.class));
  }

}
