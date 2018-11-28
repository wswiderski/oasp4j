package io.oasp.gastronomy.restaurant.offermanagement.logic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.offermanagement.SpecialCreatorUtil;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.Offermanagement;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialEto;
import io.oasp.module.test.common.base.ComponentTest;

/**
 * @author WSWIDERS
 *
 */
@Transactional
@SpringBootTest(classes = { SpringBootApp.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OffermanagementImplTest_Integration extends ComponentTest {

  @Inject
  private Offermanagement offerManagement;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void shouldFindSavedSpecialById() {

    // given
    String name = "Andrzej";
    List<OfferEntity> offers = this.entityManager.createQuery("SELECT e FROM OfferEntity e").getResultList();
    OfferEntity offer = offers.get(0);
    assertNotNull(offer);

    SpecialEntity specialEntity1 = SpecialCreatorUtil.createSpecialEntity(name);
    specialEntity1.setOffer(offer);
    Long offerId = offer.getId();

    SpecialEntity savedEntity = this.entityManager.merge(specialEntity1);
    this.entityManager.flush();
    Long savedEntityId = savedEntity.getId();

    // when
    SpecialEto result = this.offerManagement.findSpecial(savedEntityId);

    // then
    assertNotNull(result);
    assertEquals(name, result.getName());
    assertEquals(offerId, result.getOfferId());
  }

}
