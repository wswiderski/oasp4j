package io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest.OffermanagementRestServiceTestHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This configuration class provides a {@link OffermanagementRestServiceTestHelper} and a {@link RestTemplate} bean
 */
@Configuration
public class OffermanagementRestTestConfig {

  /**
   * This method is creating {@link OffermanagementRestServiceTestHelper} bean
   *
   * @return {@link OffermanagementRestServiceTestHelper}
   */
  @Bean
  public OffermanagementRestServiceTestHelper offermanagementRestServiceTestHelper() {

    OffermanagementRestServiceTestHelper offermanagementRestServiceTestHelper =
        new OffermanagementRestServiceTestHelper();
    return offermanagementRestServiceTestHelper;
  }

  /**
   * This method is creating {@link RestTemplate} bean
   *
   * @return {@link RestTemplate}
   */
  @Bean
  public RestTemplate restTemplate() {

    RestTemplate restTemplate = new RestTemplate();
    return restTemplate;
  }
}
