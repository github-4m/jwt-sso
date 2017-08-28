package id.co.blogspot.fathan.configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import id.co.blogspot.fathan.outbound.HttpInvoker;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fathan.mustaqiim on 8/23/2017.
 */
@Configuration
public class BeanConfiguration {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) throws Exception {
    return restTemplateBuilder.build();
  }

  @Bean
  public ObjectMapper objectMapper() throws Exception {
    return new ObjectMapper(new JsonFactory());
  }

  @Bean
  public XmlMapper xmlMapper() throws Exception {
    return new XmlMapper(new XmlFactory());
  }

  @Bean
  public HttpInvoker httpInvoker() throws Exception {
    return new HttpInvoker();
  }

}
