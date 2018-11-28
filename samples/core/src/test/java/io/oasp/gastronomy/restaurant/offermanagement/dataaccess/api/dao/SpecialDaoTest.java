package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialSearchCriteriaTo;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * @author WSWIDERS
 *
 */
@Transactional
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpecialDaoTest extends ComponentTest {

  @Inject
  private SpecialDao specialDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void shpuldSaveSpecialEntityAndSetId() {

    // given
    SpecialEntity se = createSpecialEntity("Andrzej");

    // when
    SpecialEntity savedSpecialEntity = this.specialDao.save(se);

    // then
    assertNotNull(savedSpecialEntity.getId());
  }

  @Test
  public void shoulFindSpecialByFullName() {

    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    criteria.setName("Andrzej");

    SpecialEntity specialEntity1 = createSpecialEntity("Andrzej");
    SpecialEntity specialEntity2 = createSpecialEntity("Dominik");
    this.entityManager.merge(specialEntity1);
    this.entityManager.merge(specialEntity2);
    this.entityManager.flush();

    // when
    List<SpecialEntity> results = this.specialDao.findByCriteria(criteria);

    // then
    assertNotNull(results);
    assertEquals(1, results.size());
    assertEquals("Andrzej", results.get(0).getName());
  }

  @Test
  public void shoulFindSpecialByNotFullName() {

    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    criteria.setName("Andrzej");

    SpecialEntity specialEntity1 = createSpecialEntity("Andrzej");
    SpecialEntity specialEntity2 = createSpecialEntity("Dominik");
    this.entityManager.merge(specialEntity1);
    this.entityManager.merge(specialEntity2);
    this.entityManager.flush();

    // when
    List<SpecialEntity> results = this.specialDao.findByCriteria(criteria);

    // then
    assertNotNull(results);
    assertEquals(1, results.size());
    assertEquals("Andrzej", results.get(0).getName());
  }

  @Test
  public void shoulNotFindSpecialByNameWhenNameIsInvalid() {

    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    criteria.setName("Lukasz");

    SpecialEntity specialEntity1 = createSpecialEntity("Andrzej");
    SpecialEntity specialEntity2 = createSpecialEntity("Dominik");
    this.entityManager.merge(specialEntity1);
    this.entityManager.merge(specialEntity2);
    this.entityManager.flush();

    // when
    List<SpecialEntity> results = this.specialDao.findByCriteria(criteria);

    // then
    assertNotNull(results);
    assertTrue(results.isEmpty());
  }

  @Test
  public void shoulFindSpecialByDate() {

    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    criteria.setDate(LocalDateTime.of(2018, 11, 27, 13, 00)); // Tuesday

    SpecialEntity specialEntity1 = createSpecialEntity("Andrzej", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, 10, 15);
    SpecialEntity specialEntity2 = createSpecialEntity("Dominik", DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, 10, 15);
    this.entityManager.merge(specialEntity1);
    this.entityManager.merge(specialEntity2);
    this.entityManager.flush();

    // when
    List<SpecialEntity> results = this.specialDao.findByCriteria(criteria);

    // then
    assertNotNull(results);
    assertEquals(1, results.size());
    assertEquals("Andrzej", results.get(0).getName());
  }

  @Test
  public void shoulFindSpecialByDateBorderCase() {

    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    criteria.setDate(LocalDateTime.of(2018, 11, 30, 15, 00)); // Friday

    SpecialEntity specialEntity1 = createSpecialEntity("Andrzej", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, 10, 15);
    SpecialEntity specialEntity2 = createSpecialEntity("Dominik", DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, 10, 15);
    this.entityManager.merge(specialEntity1);
    this.entityManager.merge(specialEntity2);
    this.entityManager.flush();

    // when
    List<SpecialEntity> results = this.specialDao.findByCriteria(criteria);

    // then
    assertNotNull(results);
    assertEquals(1, results.size());
    assertEquals("Dominik", results.get(0).getName());
  }

  @Test
  public void shoulNotFindSpecialByDate() {

    // given
    SpecialSearchCriteriaTo criteria = new SpecialSearchCriteriaTo();
    criteria.setDate(LocalDateTime.of(2018, 12, 1, 15, 00)); // Saturday

    SpecialEntity specialEntity1 = createSpecialEntity("Andrzej", DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, 10, 15);
    SpecialEntity specialEntity2 = createSpecialEntity("Dominik", DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, 10, 15);
    this.entityManager.merge(specialEntity1);
    this.entityManager.merge(specialEntity2);
    this.entityManager.flush();

    // when
    List<SpecialEntity> results = this.specialDao.findByCriteria(criteria);

    // then
    assertNotNull(results);
    assertEquals(0, results.size());
  }

  private SpecialEntity createSpecialEntity(String name, DayOfWeek startingDay, DayOfWeek endingDay, int startingHour,
      int endingHour) {

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

  private SpecialEntity createSpecialEntity(String name) {

    return createSpecialEntity(name, DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, 10, 22);
  }
}
