package id.co.blogspot.fathan.outbound;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fathan.mustaqiim on 8/24/2017.
 */
public class HttpInvoker {

  @Autowired
  private RestTemplate restTemplate;

  public String invoke(URI uri, HttpMethod httpMethod, String requestBody) throws Exception {
    HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
    ResponseEntity<String> responseEntity = this.restTemplate.exchange(uri, httpMethod, httpEntity, String.class);
    return responseEntity.getBody();
  }

}
