package id.co.blogspot.fathan.util;

import java.lang.reflect.Constructor;
import org.junit.Test;

/**
 * Created by fathan.mustaqiim on 11/25/2016.
 */
public class PreconditionTest {

  private static final String DEFAULT_ERROR_MESSAGE = "error message";

  @Test
  public void constructorTest() throws Exception {
    Constructor<Precondition> preconditionConstructor = Precondition.class.getDeclaredConstructor();
    preconditionConstructor.setAccessible(true);
    preconditionConstructor.newInstance();
  }

  @Test
  public void checkArgumentTest() throws Exception {
    Precondition.checkArgument(true, PreconditionTest.DEFAULT_ERROR_MESSAGE);
  }

  @Test(expected = Exception.class)
  public void checkArgumentWithExceptionTest() throws Exception {
    Precondition.checkArgument(false, PreconditionTest.DEFAULT_ERROR_MESSAGE);
  }
}
