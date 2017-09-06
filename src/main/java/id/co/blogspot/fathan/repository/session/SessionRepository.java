package id.co.blogspot.fathan.repository.session;

import id.co.blogspot.fathan.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public interface SessionRepository extends JpaRepository<Session, String> {

  Session findByUsername(String username) throws Exception;

  Session findByUsernameAndSessionId(String username, String sessionId) throws Exception;

  Session findByTicket(String ticket) throws Exception;
}
