package io.oasp.gastronomy.restaurant.offermanagement.service.impl.rest;

import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.WeeklyPeriod;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderCto;
import io.oasp.gastronomy.restaurant.salesmanagement.logic.api.to.OrderPositionEto;
import io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementHttpRestServiceTest;
import io.oasp.gastronomy.restaurant.salesmanagement.service.impl.rest.SalesmanagementRestServiceTest;

import java.time.DayOfWeek;

/**
 * This is a helper class for the classes {@link SalesmanagementRestServiceTest} and
 * {@link SalesmanagementHttpRestServiceTest}. It capsulates some sample attributes as constants used both testing
 * classes. Moreover it provides two methods to create sample {@link OrderCto} and {@link OrderPositionEto} objects
 * using the sample attributes defined by this class.
 */
public class OffermanagementRestServiceTestHelper {

  public static final String DUMMY_NAME = "dummyName";

  /**
   * This method creates a sample {@link SpecialEto} depending on the constants defined by this class.
   *
   * @param id
   * @return {@link SpecialEto}
   */
  public SpecialEto createSpecialEto(Long id) {

    SpecialEto specialEto = new SpecialEto();
    specialEto.setId(id);
    specialEto.setName(DUMMY_NAME);
    specialEto.setOffer(prepareSomeOffer());
    specialEto.setActivePeriod(createWeeklyPeriod());

    return specialEto;
  }
  private WeeklyPeriod createWeeklyPeriod(){
    WeeklyPeriod period =  new WeeklyPeriod();
    period.setStartingDay(DayOfWeek.MONDAY);
    period.setStartingHour(8);

    period.setEndingDay(DayOfWeek.FRIDAY);
    period.setEndingHour(16);
    return period;
  }

  private OfferEto prepareSomeOffer() {
    OfferEto offerEto = new OfferEto();
    offerEto.setName("someOffer");
    offerEto.setId(1l);
    return offerEto;
  }

}
