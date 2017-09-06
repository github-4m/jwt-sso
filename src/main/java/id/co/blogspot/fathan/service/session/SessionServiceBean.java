package id.co.blogspot.fathan.service.session;

import id.co.blogspot.fathan.entity.Session;
import id.co.blogspot.fathan.repository.session.SessionRepository;
import id.co.blogspot.fathan.util.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
@Service
@Transactional(readOnly = true)
public class SessionServiceBean implements SessionService {

  @Autowired
  private SessionRepository sessionRepository;

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void create(String username, String ticket) throws Exception {
    if (StringUtils.isEmpty(username)) {
      throw new Exception("Invalid username");
    }
    Session savedSession = this.sessionRepository.findByUsername(username);
    if (savedSession != null) {
      this.sessionRepository.delete(savedSession.getId());
      this.sessionRepository.flush();
    }
    Session session = new Session();
    session.setUsername(username);
    session.setSessionId(Credential.getSessionId());
    session.setHostname(Credential.getHostname());
    session.setTicket(ticket);
    this.sessionRepository.save(session);
  }

  @Override
  public boolean isAuthorized() throws Exception {
    Session session =
        this.sessionRepository.findByUsernameAndSessionId(
            Credential.getUsername(), Credential.getSessionId());
    return session != null;
  }

  @Override
  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void remove(String ticket) throws Exception {
    Session session = this.sessionRepository.findByTicket(ticket);
    if (session != null) {
      this.sessionRepository.delete(session.getId());
    }
  }
}
