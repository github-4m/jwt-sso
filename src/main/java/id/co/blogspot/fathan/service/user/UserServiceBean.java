package id.co.blogspot.fathan.service.user;

import id.co.blogspot.fathan.dto.cas.CasAuthenticationSuccess;
import id.co.blogspot.fathan.outbound.cas.CasOutbound;
import id.co.blogspot.fathan.service.session.SessionService;
import id.co.blogspot.fathan.util.Credential;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceBean implements UserService {

  @Autowired
  private SessionService sessionService;

  @Autowired
  private CasOutbound casOutbound;

  @Value(value = "${jwt.secret.key}")
  private String jwtSecretKey;

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public String authenticate(String ticket, HttpServletResponse httpServletResponse) throws Exception {
    if (StringUtils.isEmpty(ticket)) {
      this.casOutbound.authenticate(httpServletResponse);
    }
    CasAuthenticationSuccess casAuthenticationSuccess = this.casOutbound.validate(ticket);
    this.sessionService.create(casAuthenticationSuccess.getUsername());
    return this.generateJwtToken(casAuthenticationSuccess.getUsername());
  }

  @Override
  public String generateJwtToken(String username) throws Exception {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("sessionId", Credential.getSessionId());
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, this.jwtSecretKey)
        .compact();
  }

  @Override
  public Claims parseJwtToken(String jwtToken) {
    try {
      return Jwts.parser().setSigningKey(this.jwtSecretKey).parseClaimsJws(jwtToken).getBody();
    } catch (JwtException | ClassCastException e) {
      return null;
    }
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void unauthenticate() throws Exception {
    this.sessionService.remove();
  }
}
