package id.co.blogspot.fathan.dto.cas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasAuthenticationSuccess implements Serializable {

  @JacksonXmlProperty(localName = "user")
  private String username;

  @JacksonXmlElementWrapper(localName = "attributes")
  private Map<String, Object> attributes = new HashMap<>();

  public CasAuthenticationSuccess() {
  }

  public CasAuthenticationSuccess(String username, Map<String, Object> attributes) {
    this.username = username;
    this.attributes = attributes;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String toString() {
    return "CasAuthenticationSuccess{" +
        "username='" + username + '\'' +
        ", attributes=" + attributes +
        '}';
  }
}
