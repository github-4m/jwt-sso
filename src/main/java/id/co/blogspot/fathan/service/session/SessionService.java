package id.co.blogspot.fathan.service.session;

/**
 * Created by fathan.mustaqiim on 10/27/2016.
 */
public interface SessionService {

  void create(String username, String ticket) throws Exception;

  boolean isAuthorized() throws Exception;

  void remove(String ticket) throws Exception;
}
