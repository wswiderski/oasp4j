package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import java.time.DayOfWeek;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author WSWIDERS
 *
 */
@Embeddable
public class WeeklyPeriodEmbeddable {

  private DayOfWeek startingDay;

  private int startingHour;

  private DayOfWeek endingDay;

  private int endingHour;

  /**
   * @return startingDay
   */
  public DayOfWeek getStartingDay() {

    return this.startingDay;
  }

  /**
   * @param startingDay new value of {@link #getstartingDay}.
   */
  public void setStartingDay(DayOfWeek startingDay) {

    this.startingDay = startingDay;
  }

  /**
   * @return startingHour
   */
  @Max(24)
  @Min(0)
  public int getStartingHour() {

    return this.startingHour;
  }

  /**
   * @param startingHour new value of {@link #getstartingHour}.
   */
  public void setStartingHour(int startingHour) {

    this.startingHour = startingHour;
  }

  /**
   * @return endingDay
   */
  public DayOfWeek getEndingDay() {

    return this.endingDay;
  }

  /**
   * @param endingDay new value of {@link #getendingDay}.
   */
  public void setEndingDay(DayOfWeek endingDay) {

    this.endingDay = endingDay;
  }

  /**
   * @return endingHour
   */
  @Max(24)
  @Min(0)
  public int getEndingHour() {

    return this.endingHour;
  }

  /**
   * @param endingHour new value of {@link #getendingHour}.
   */
  public void setEndingHour(int endingHour) {

    this.endingHour = endingHour;
  }
}
