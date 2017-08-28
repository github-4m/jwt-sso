package id.co.blogspot.fathan.dto.cas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
@JacksonXmlRootElement(localName = "serviceResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasResponse implements Serializable {

  @JacksonXmlProperty(localName = "authenticationSuccess")
  private CasAuthenticationSuccess casAuthenticationSuccess;

  @JacksonXmlProperty(localName = "authenticationFailure")
  private String casAuthenticationFailure;

  public CasResponse() {
  }

  public CasResponse(CasAuthenticationSuccess casAuthenticationSuccess, String casAuthenticationFailure) {
    this.casAuthenticationSuccess = casAuthenticationSuccess;
    this.casAuthenticationFailure = casAuthenticationFailure;
  }

  public CasAuthenticationSuccess getCasAuthenticationSuccess() {
    return casAuthenticationSuccess;
  }

  public void setCasAuthenticationSuccess(CasAuthenticationSuccess casAuthenticationSuccess) {
    this.casAuthenticationSuccess = casAuthenticationSuccess;
  }

  public String getCasAuthenticationFailure() {
    return casAuthenticationFailure;
  }

  public void setCasAuthenticationFailure(String casAuthenticationFailure) {
    this.casAuthenticationFailure = casAuthenticationFailure;
  }

  @Override
  public String toString() {
    return "CasResponse{" +
        "casAuthenticationSuccess=" + casAuthenticationSuccess +
        ", casAuthenticationFailure='" + casAuthenticationFailure + '\'' +
        '}';
  }
}
