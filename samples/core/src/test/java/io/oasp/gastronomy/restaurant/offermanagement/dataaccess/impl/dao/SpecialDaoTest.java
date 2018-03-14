package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.module.test.common.base.ComponentTest;
import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Transactional
@SpringBootTest(classes = {SpringBootApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpecialDaoTest extends ComponentTest{

  @Inject
  private SpecialDao specialDao;

  @Inject
  private OfferDao offerDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void testPersistingSpecialOffer() {
    // given
    SpecialEntity special = prepareSpecialOffer();

    // when
    specialDao.save(special);

    // then
    assertThat(special.getId()).isNotNull();
  }

  @Test(expected = ObjectNotFoundUserException.class)
  public void testRemovingSpecialOffer() {
    // given
    SpecialEntity special = this.prepareSpecialOffer();
    SpecialEntity savedSpecialOffer = specialDao.save(special);

    // when
    specialDao.delete(savedSpecialOffer.getId());

    // then
    specialDao.find(savedSpecialOffer.getId());
  }

  @Test
  public void testFindingAllSpecialOffers() {
    // given
    SpecialEntity specialEntity1 = this.prepareSpecialOffer();
    SpecialEntity specialEntity2 = this.prepareSpecialOffer();
    specialEntity2.setName("Special Entity 2");
    specialDao.save(Arrays.asList(specialEntity1, specialEntity2));

    // when
    List<SpecialEntity> foundSpecialEntities = specialDao.findAll();

    assertThat(foundSpecialEntities).extracting("name").containsOnly(specialEntity1.getName(), specialEntity2.getName());
  }

  @Test
  public void testNotFindingAnyActiveSpecialsWhenNoSpecialExists() {
    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(LocalDateTime.now());

    // then
    assertThat(currentlyActiveSpecials).isEmpty();
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithStartingHourCurrentHourAndStartingDayCurrentDay() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour());
    special.getActivePeriod().setEndingHour(currentDateTime.getHour()+2);
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek());
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(currentDateTime);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithStartingHourCurrentHourAndStartingDayLessCurrentDay() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour());
    special.getActivePeriod().setEndingHour(currentDateTime.getHour()+2);
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(1));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(currentDateTime);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithStartingHourCurrentHourAndEndingDayCurrentDay() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour());
    special.getActivePeriod().setEndingHour(currentDateTime.getHour()+2);
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(1));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek());
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(currentDateTime);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithEndingHourCurrentHourAndStartingDayCurrentDay() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour());
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek());
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(currentDateTime);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithEndingHourCurrentHourAndDaysBetweenCurrentDay() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour());
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(1));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(currentDateTime);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithEndingHourCurrentHourAndEndingDayCurrentDay() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour());
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(2));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek());
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(currentDateTime);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithCurrentHourBetweenHoursAndCurrentDayBetweenDays() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour()+2);
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(2));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(currentDateTime);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  private SpecialEntity prepareSpecialOffer() {
    OfferEntity offer = new OfferEntity();
    offerDao.save(offer);

    SpecialEntity special = new SpecialEntity();
    assertThat(special.getId()).isNull();
    special.setName("Max Source");
    WeeklyPeriodEmbeddable activePeriod = new WeeklyPeriodEmbeddable();
    activePeriod.setEndingHour(14);
    activePeriod.setStartingHour(6);
    special.setActivePeriod(activePeriod);
    special.setOffer(offer);
    return special;
  }
}
