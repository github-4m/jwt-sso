package id.co.blogspot.fathan.outbound.cas;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
public interface CasOutboundPath {

  String AUTHENTICATE = "/login";
  String VALIDATE = "/serviceValidate";
  String UNAUTHENTICATE = "/logout";

}
