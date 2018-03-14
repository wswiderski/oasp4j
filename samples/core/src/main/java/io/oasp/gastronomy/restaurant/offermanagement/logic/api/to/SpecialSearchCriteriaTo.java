package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;

public class SpecialSearchCriteriaTo extends SearchCriteriaTo {
  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private String name;

  private OfferEntity offer;

  private WeeklyPeriodEmbeddable activePeriod;

  private Money specialPrice;

  private Long offerNumber;

  public SpecialSearchCriteriaTo() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OfferEntity getOffer() {
    return offer;
  }

  public void setOffer(OfferEntity offer) {
    this.offer = offer;
  }

  public WeeklyPeriodEmbeddable getActivePeriod() {
    return activePeriod;
  }

  public void setActivePeriod(WeeklyPeriodEmbeddable activePeriod) {
    this.activePeriod = activePeriod;
  }

  public Money getSpecialPrice() {
    return specialPrice;
  }

  public void setSpecialPrice(Money specialPrice) {
    this.specialPrice = specialPrice;
  }

  public Long getOfferNumber() {
    return offerNumber;
  }

  public void setOfferNumber(Long offerNumber) {
    this.offerNumber = offerNumber;
  }
}
