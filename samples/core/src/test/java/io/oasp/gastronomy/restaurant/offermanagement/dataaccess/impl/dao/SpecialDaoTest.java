package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.SupplierEntity;
import io.oasp.module.test.common.base.ComponentTest;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@SpringBootTest(classes = {SpringBootApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpecialDaoTest extends ComponentTest {

  @Inject
  private SpecialDao specialDao;

  @Inject
  private OfferDao offerDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void testPersistingSpecialOffer() {
    // given
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

    // when
    specialDao.save(special);

    // then
    assertThat(special.getId()).isNotNull();
  }
}
