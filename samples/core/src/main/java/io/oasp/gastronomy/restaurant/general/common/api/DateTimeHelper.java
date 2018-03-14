package io.oasp.gastronomy.restaurant.general.common.api;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeHelper {

  public LocalDateTime now() {
    return LocalDateTime.now();
  }
}
