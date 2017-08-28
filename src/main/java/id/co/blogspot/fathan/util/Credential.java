package id.co.blogspot.fathan.util;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public final class Credential {

  public static final String CREDENTIAL_USERNAME = "USERNAME";
  public static final String CREDENTIAL_SESSION_ID = "SESSION_ID";
  public static final String CREDENTIAL_HOSTNAME = "HOSTNAME";
  public static final String CREDENTIAL_REQUEST_ID = "REQUEST_ID";
  public static final String CREDENTIAL_ROLES = "ROLES";

  private Credential() {
  }

  public static String getUsername() {
    return MDC.get(Credential.CREDENTIAL_USERNAME);
  }

  public static void setUsername(String username) {
    MDC.put(Credential.CREDENTIAL_USERNAME, username);
  }

  public static String getSessionId() {
    return MDC.get(Credential.CREDENTIAL_SESSION_ID);
  }

  public static void setSessionId(String sessionId) {
    MDC.put(Credential.CREDENTIAL_SESSION_ID, sessionId);
  }

  public static String getHostname() {
    return MDC.get(Credential.CREDENTIAL_HOSTNAME);
  }

  public static void setHostname(String hostname) {
    MDC.put(Credential.CREDENTIAL_HOSTNAME, hostname);
  }

  public static String getRequestId() {
    return MDC.get(Credential.CREDENTIAL_REQUEST_ID);
  }

  public static void setRequestId(String requestId) {
    MDC.put(Credential.CREDENTIAL_REQUEST_ID, requestId);
  }

  public static String[] getRoles() {
    return StringUtils.commaDelimitedListToStringArray(MDC.get(Credential.CREDENTIAL_ROLES));
  }

  public static void setRoles(String[] roles) {
    MDC.put(Credential.CREDENTIAL_ROLES, StringUtils.arrayToCommaDelimitedString(roles));
  }
}
