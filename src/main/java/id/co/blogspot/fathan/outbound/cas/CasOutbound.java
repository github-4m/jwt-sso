package id.co.blogspot.fathan.outbound.cas;

import id.co.blogspot.fathan.dto.cas.CasAuthenticationSuccess;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
public interface CasOutbound {

  void authenticate(HttpServletResponse httpServletResponse) throws Exception;

  CasAuthenticationSuccess validate(String ticket) throws Exception;

  void unauthenticate(HttpServletResponse httpServletResponse) throws Exception;

}
