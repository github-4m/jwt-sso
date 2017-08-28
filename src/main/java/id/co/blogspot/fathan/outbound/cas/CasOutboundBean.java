package id.co.blogspot.fathan.outbound.cas;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import id.co.blogspot.fathan.dto.cas.CasAuthenticationSuccess;
import id.co.blogspot.fathan.dto.cas.CasResponse;
import id.co.blogspot.fathan.outbound.HttpInvoker;
import id.co.blogspot.fathan.security.exception.UnauthorizedException;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
@Component
public class CasOutboundBean implements CasOutbound {

  @Autowired
  private XmlMapper xmlMapper;

  @Autowired
  private HttpInvoker httpInvoker;

  @Value(value = "${cas.host}")
  private String casHost;

  @Value(value = "${cas.service}")
  private String casService;

  @Override
  public void authenticate(HttpServletResponse httpServletResponse) throws Exception {
    URI uri = new URIBuilder(this.casHost).setPath(CasOutboundPath.AUTHENTICATE)
        .addParameter("service", this.casService).build();
    httpServletResponse.sendRedirect(uri.toString());
  }

  @Override
  public CasAuthenticationSuccess validate(String ticket) throws Exception {
    URI uri = new URIBuilder(this.casHost).setPath(CasOutboundPath.VALIDATE).addParameter("service", this.casService)
        .addParameter("ticket", ticket).build();
    String responseBody = this.httpInvoker.invoke(uri, HttpMethod.GET, null);
    CasResponse casResponse = this.xmlMapper.readValue(responseBody, CasResponse.class);
    if (!StringUtils.isEmpty(casResponse.getCasAuthenticationFailure())) {
      throw new UnauthorizedException("Unauthorized api access");
    }
    return casResponse.getCasAuthenticationSuccess();
  }
}
