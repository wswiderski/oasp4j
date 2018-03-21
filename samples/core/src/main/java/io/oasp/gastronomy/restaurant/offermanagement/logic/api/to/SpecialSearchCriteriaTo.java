package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.module.jpa.common.api.to.SearchCriteriaTo;

import java.time.LocalDateTime;

/**
 * Criteria for searching @{@link io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity}
 * Contains information about OfferNumber to find by and date of checking for special offer to check if there is sth active.
 */
public class SpecialSearchCriteriaTo extends SearchCriteriaTo {
  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  private String name;

  private OfferEntity offer;

  private LocalDateTime dateOfCheckingOffers;

  private Money specialPrice;

  private Long offerNumber;

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

  public LocalDateTime getDateOfCheckingOffers() {
    return dateOfCheckingOffers;
  }

  public void setDateOfCheckingOffers(LocalDateTime dateOfCheckingOffers) {
    this.dateOfCheckingOffers = dateOfCheckingOffers;
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
