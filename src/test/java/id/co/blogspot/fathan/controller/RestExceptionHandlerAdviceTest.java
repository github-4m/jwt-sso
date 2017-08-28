package id.co.blogspot.fathan.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Created by fathan.mustaqiim on 8/25/2017.
 */
public class RestExceptionHandlerAdviceTest {

  @InjectMocks
  private RestExceptionHandlerAdvice restExceptionHandlerAdvice;

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void exceptionHandlerTest() throws Exception {
    this.restExceptionHandlerAdvice.exceptionHandler(new Exception());
  }

}
