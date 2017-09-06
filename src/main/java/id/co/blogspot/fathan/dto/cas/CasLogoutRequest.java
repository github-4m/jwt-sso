package id.co.blogspot.fathan.dto.cas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;

/**
 * Created by fathan.mustaqiim on 9/6/2017.
 */
@JacksonXmlRootElement(localName = "LogoutRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasLogoutRequest implements Serializable {

  @JacksonXmlProperty(localName = "SessionIndex")
  private String ticket;

  public CasLogoutRequest() {
  }

  public CasLogoutRequest(String ticket) {
    this.ticket = ticket;
  }

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  @Override
  public String toString() {
    return "CasLogoutRequest{" +
        "ticket='" + ticket + '\'' +
        '}';
  }
}
