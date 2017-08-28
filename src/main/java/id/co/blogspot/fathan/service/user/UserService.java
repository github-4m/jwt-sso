package id.co.blogspot.fathan.service.user;

import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
public interface UserService {

  String authenticate(String ticket, HttpServletResponse httpServletResponse) throws Exception;

  String generateJwtToken(String username) throws Exception;

  Claims parseJwtToken(String jwtToken);

  void unauthenticate() throws Exception;
}
