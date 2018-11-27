package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * @author WSWIDERS
 *
 */
@Transactional
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpecialDaoTest extends ComponentTest {

  @Autowired
  private SpecialDao specialDao;

  @Test
  public void shpuldSaveSpecialEntityAndSetId() {

    // given
    WeeklyPeriodEmbeddable weeklyPeriod = new WeeklyPeriodEmbeddable();
    weeklyPeriod.setEndingDay(DayOfWeek.WEDNESDAY);
    weeklyPeriod.setStartingDay(DayOfWeek.MONDAY);
    weeklyPeriod.setEndingHour(22);
    weeklyPeriod.setStartingHour(10);
    Money specialPrice = new Money(BigDecimal.TEN);
    SpecialEntity se = new SpecialEntity();
    se.setName("Andrzej");
    se.setActivePeriod(weeklyPeriod);
    se.setSpecialPrice(specialPrice);
    // when
    SpecialEntity savedSpecialEntity = this.specialDao.save(se);
    // then
    assertNotNull(savedSpecialEntity.getId());
  }

}
