package id.co.blogspot.fathan.outbound.cas;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import id.co.blogspot.fathan.dto.cas.CasResponse;
import id.co.blogspot.fathan.outbound.HttpInvoker;
import id.co.blogspot.fathan.security.exception.UnauthorizedException;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
public class CasOutboundTest {

  private static final String DEFAULT_CAS_HOST = "https://localhost/cas";
  private static final String DEFAULT_CAS_SERVICE = "https://localhost/jwt-sso";
  private static final String DEFAULT_CAS_SESSION_TICKET = "ST-1234567890";
  private static final String DEFAULT_CAS_RESPONSE = "<serviceResponse><authenticationSuccess/><authenticationFailure/></serviceResponse>";

  @Mock
  private XmlMapper xmlMapper;

  @Mock
  private HttpInvoker httpInvoker;

  @Mock
  private HttpServletResponse httpServletResponse;

  @InjectMocks
  private CasOutboundBean casOutboundBean;

  private CasResponse generateCasResponse() throws Exception {
    CasResponse casResponse = new CasResponse();
    return casResponse;
  }

  @Before
  public void initializeTest() throws Exception {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(this.casOutboundBean, "casHost", CasOutboundTest.DEFAULT_CAS_HOST);
    ReflectionTestUtils.setField(this.casOutboundBean, "casService", CasOutboundTest.DEFAULT_CAS_SERVICE);
    CasResponse casResponse = this.generateCasResponse();
    Mockito.doNothing().when(this.httpServletResponse).sendRedirect(Mockito.anyString());
    Mockito.when(this.httpInvoker.invoke(Mockito.any(URI.class), Mockito.any(HttpMethod.class), Mockito.anyString()))
        .thenReturn(CasOutboundTest.DEFAULT_CAS_RESPONSE);
    Mockito.when(this.xmlMapper.readValue(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(casResponse);
  }

  @After
  public void finalizeTest() throws Exception {
    Mockito.verifyNoMoreInteractions(this.xmlMapper);
    Mockito.verifyNoMoreInteractions(this.httpInvoker);
    Mockito.verifyNoMoreInteractions(this.httpServletResponse);
  }

  @Test
  public void authenticateTest() throws Exception {
    this.casOutboundBean.authenticate(this.httpServletResponse);
    Mockito.verify(this.httpServletResponse).sendRedirect(Mockito.anyString());
  }

  @Test
  public void validateTest() throws Exception {
    this.casOutboundBean.validate(CasOutboundTest.DEFAULT_CAS_SESSION_TICKET);
    Mockito.verify(this.httpInvoker).invoke(Mockito.any(URI.class), Mockito.any(HttpMethod.class), Mockito.anyString());
    Mockito.verify(this.xmlMapper).readValue(Mockito.anyString(), Mockito.any(Class.class));
  }

  @Test(expected = UnauthorizedException.class)
  public void validateWithUnauthorizedExceptionTest() throws Exception {
    CasResponse casResponse = new CasResponse();
    casResponse.setCasAuthenticationFailure("Unauthorized api access");
    Mockito.when(this.xmlMapper.readValue(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(casResponse);
    try {
      this.casOutboundBean.validate(CasOutboundTest.DEFAULT_CAS_SESSION_TICKET);
    } catch (Exception e) {
      if (e instanceof UnauthorizedException) {
        Mockito.verify(this.httpInvoker)
            .invoke(Mockito.any(URI.class), Mockito.any(HttpMethod.class), Mockito.anyString());
        Mockito.verify(this.xmlMapper).readValue(Mockito.anyString(), Mockito.any(Class.class));
        throw e;
      } else {
        Assert.assertFalse(true);
      }
    }
  }

}
