package io.oasp.gastronomy.restaurant.offermanagement;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;

/**
 * @author WSWIDERS
 *
 */
public class SpecialCreatorUtil {

  public static SpecialEntity createSpecialEntity(String name, DayOfWeek startingDay, DayOfWeek endingDay,
      int startingHour, int endingHour) {

    WeeklyPeriodEmbeddable weeklyPeriod = new WeeklyPeriodEmbeddable();
    weeklyPeriod.setEndingDay(endingDay);
    weeklyPeriod.setStartingDay(startingDay);
    weeklyPeriod.setEndingHour(endingHour);
    weeklyPeriod.setStartingHour(startingHour);
    Money specialPrice = new Money(BigDecimal.TEN);
    SpecialEntity se = new SpecialEntity();
    se.setName(name);
    se.setActivePeriod(weeklyPeriod);
    se.setSpecialPrice(specialPrice);
    return se;
  }

  public static SpecialEntity createSpecialEntity(String name) {

    return createSpecialEntity(name, DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, 10, 22);
  }
}
