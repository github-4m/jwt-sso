package id.co.blogspot.fathan.util;

import java.lang.reflect.Constructor;
import java.util.UUID;
import org.junit.Test;

/**
 * Created by fathan.mustaqiim on 11/25/2016.
 */
public class CredentialTest {

  private static final String DEFAULT_USERNAME = "USERNAME";
  private static final String DEFAULT_SESSION_ID = UUID.randomUUID().toString();
  private static final String DEFAULT_HOSTNAME = "HOSTNAME";
  private static final String DEFAULT_REQUEST_ID = UUID.randomUUID().toString();
  private static final String[] DEFAULT_ROLES = new String[]{"ROLE_1"};

  @Test
  public void constructorTest() throws Exception {
    Constructor<Credential> credentialConstructor = Credential.class.getDeclaredConstructor();
    credentialConstructor.setAccessible(true);
    credentialConstructor.newInstance();
  }

  @Test
  public void setterTest() throws Exception {
    Credential.setUsername(CredentialTest.DEFAULT_USERNAME);
    Credential.setSessionId(CredentialTest.DEFAULT_SESSION_ID);
    Credential.setHostname(CredentialTest.DEFAULT_HOSTNAME);
    Credential.setRequestId(CredentialTest.DEFAULT_REQUEST_ID);
    Credential.setRoles(CredentialTest.DEFAULT_ROLES);
  }

  @Test
  public void getterTest() throws Exception {
    Credential.getUsername();
    Credential.getSessionId();
    Credential.getHostname();
    Credential.getRequestId();
    Credential.getRoles();
  }
}
