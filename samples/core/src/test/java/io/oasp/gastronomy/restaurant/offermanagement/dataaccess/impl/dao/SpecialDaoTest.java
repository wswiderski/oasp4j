package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialSearchCriteriaTo;
import io.oasp.module.test.common.base.ComponentTest;
import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
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
    OfferEntity offerEntity = this.prepareOffer();
    SpecialEntity specialEntity1 = this.prepareSpecialWithOffer(offerEntity);
    SpecialEntity specialEntity2 = this.prepareSpecialWithOffer(offerEntity);
    specialEntity2.setName("Special Entity 2");
    specialDao.save(Arrays.asList(specialEntity1, specialEntity2));

    // when
    List<SpecialEntity> foundSpecialEntities = specialDao.findAll();

    assertThat(foundSpecialEntities).extracting("name").containsOnly(specialEntity1.getName(), specialEntity2.getName());
  }

  @Test
  public void testNotFindingAnyActiveSpecialsWhenNoSpecialExists() {
    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    criteria.setDateOfCheckingOffers(LocalDateTime.of(2018,2,2,12,0));

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(criteria);

    // then
    assertThat(currentlyActiveSpecials).isEmpty();
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithStartingHourCurrentHourAndStartingDayCurrentDay() {
    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    criteria.setDateOfCheckingOffers(currentDateTime);
    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour());
    special.getActivePeriod().setEndingHour(currentDateTime.getHour()+2);
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek());
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(criteria);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithStartingHourCurrentHourAndStartingDayLessCurrentDay() {
    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    criteria.setDateOfCheckingOffers(currentDateTime);    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour());
    special.getActivePeriod().setEndingHour(currentDateTime.getHour()+2);
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(1));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(criteria);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithStartingHourCurrentHourAndEndingDayCurrentDay() {
    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    criteria.setDateOfCheckingOffers(currentDateTime);    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour());
    special.getActivePeriod().setEndingHour(currentDateTime.getHour()+2);
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(1));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek());
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(criteria);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithEndingHourCurrentHourAndStartingDayCurrentDay() {
    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    criteria.setDateOfCheckingOffers(currentDateTime);    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour());
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek());
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(criteria);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithEndingHourCurrentHourAndDaysBetweenCurrentDay() {
    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    criteria.setDateOfCheckingOffers(currentDateTime);    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour());
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(1));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(criteria);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithEndingHourCurrentHourAndEndingDayCurrentDay() {
    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    criteria.setDateOfCheckingOffers(currentDateTime);    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour());
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(2));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek());
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(criteria);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testFindingOneActiveSpecialOfferWithCurrentHourBetweenHoursAndCurrentDayBetweenDays() {
    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    criteria.setDateOfCheckingOffers(currentDateTime);    SpecialEntity special = this.prepareSpecialOffer();
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour()+2);
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(2));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek().plus(2));
    SpecialEntity savedSpecial = specialDao.save(special);

    // when
    List<SpecialEntity> currentlyActiveSpecials = specialDao.findActiveSpecials(criteria);

    // then
    assertThat(currentlyActiveSpecials).extracting("id").containsOnly(savedSpecial.getId());
  }

  @Test
  public void testNotFindingBestActiveSpecialWhenNoSpecialExists() {
    // given
    SpecialSearchCriteriaTo searchCriteria = new SpecialSearchCriteriaTo();
    searchCriteria.setOfferNumber(102L);
    searchCriteria.setDateOfCheckingOffers(LocalDateTime.of(2018,2,2,2,0));

    // when
    Money bestSpecialPrice = this.specialDao.findBestActiveSpecial(searchCriteria);

    assertThat(bestSpecialPrice).isNull();
  }

  @Test
  public void testFindingBestActiveSpecialWhenTwoDifferentExist() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    OfferEntity offer = this.prepareOffer();
    SpecialEntity specialWithWorsePrice = this.prepareSpecialWithOffer(offer);
    specialWithWorsePrice.setSpecialPrice(new Money(11));
    this.setSpecialActiveForCurrentDateTime(specialWithWorsePrice, currentDateTime);
    SpecialEntity specialWithBetterPrice = this.prepareSpecialWithOffer(offer);
    specialWithBetterPrice.setSpecialPrice(new Money(9));
    specialWithBetterPrice.setName("Special With Best Price");
    this.setSpecialActiveForCurrentDateTime(specialWithBetterPrice, currentDateTime);
    specialDao.save(Arrays.asList(specialWithBetterPrice, specialWithWorsePrice));

    SpecialSearchCriteriaTo searchCriteria = new SpecialSearchCriteriaTo();
    searchCriteria.setDateOfCheckingOffers(currentDateTime);
    searchCriteria.setOfferNumber(specialWithBetterPrice.getOffer().getNumber());

    // when
    Money bestSpecialPrice = this.specialDao.findBestActiveSpecial(searchCriteria);

    assertThat(bestSpecialPrice).isEqualTo(specialWithBetterPrice.getSpecialPrice());
  }

  @Test
  public void testFindingBestActiveSpecialWhenTwoEqualtExist() {
    // given
    LocalDateTime currentDateTime = LocalDateTime.of(2018,2,2,12,0);
    OfferEntity offer = this.prepareOffer();
    SpecialEntity special1 = this.prepareSpecialWithOffer(offer);
    this.setSpecialActiveForCurrentDateTime(special1, currentDateTime);
    SpecialEntity special2 = this.prepareSpecialWithOffer(offer);
    special2.setName("Special with same price");
    this.setSpecialActiveForCurrentDateTime(special2, currentDateTime);
    specialDao.save(Arrays.asList(special2, special1));

    SpecialSearchCriteriaTo searchCriteria = new SpecialSearchCriteriaTo();
    searchCriteria.setDateOfCheckingOffers(currentDateTime);
    searchCriteria.setOfferNumber(special2.getOffer().getNumber());

    // when
    Money bestSpecialPrice = this.specialDao.findBestActiveSpecial(searchCriteria);

    assertThat(bestSpecialPrice).isEqualTo(special2.getSpecialPrice());
  }

  private void setSpecialActiveForCurrentDateTime(SpecialEntity special, LocalDateTime currentDateTime) {
    special.getActivePeriod().setStartingHour(currentDateTime.getHour() - 2);
    special.getActivePeriod().setEndingHour(currentDateTime.getHour());
    special.getActivePeriod().setStartingDay(currentDateTime.getDayOfWeek().minus(2));
    special.getActivePeriod().setEndingDay(currentDateTime.getDayOfWeek());
  }

  private SpecialEntity prepareSpecialOffer() {
    OfferEntity offer = prepareOffer();

    return prepareSpecialWithOffer(offer);
  }

  private SpecialEntity prepareSpecialWithOffer(OfferEntity offer) {
    SpecialEntity special = new SpecialEntity();
    assertThat(special.getId()).isNull();
    special.setName("Max Source");
    special.setSpecialPrice(new Money(10));
    WeeklyPeriodEmbeddable activePeriod = new WeeklyPeriodEmbeddable();
    activePeriod.setEndingHour(14);
    activePeriod.setStartingHour(6);
    special.setActivePeriod(activePeriod);
    special.setOffer(offer);
    return special;
  }

  private OfferEntity prepareOffer() {
    OfferEntity offer = new OfferEntity();
    offer.setNumber(102L);
    offer.setPrice(new Money(12));
    offerDao.save(offer);
    return offer;
  }
}
