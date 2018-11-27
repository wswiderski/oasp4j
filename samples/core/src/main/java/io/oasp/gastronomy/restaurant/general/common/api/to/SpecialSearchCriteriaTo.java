package io.oasp.gastronomy.restaurant.general.common.api.to;

import java.time.LocalDateTime;

import io.oasp.module.basic.common.api.to.AbstractTo;

/**
 * @author WSWIDERS
 *
 */
public class SpecialSearchCriteriaTo extends AbstractTo {

  /**
   *
   */
  private static final long serialVersionUID = 1287888266547906847L;

  private String name;

  private Long offerId;

  private LocalDateTime date;

  /**
   * @return name
   */
  public String getName() {

    return this.name;
  }

  /**
   * @param name new value of {@link #getname}.
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @return offerId
   */
  public Long getOfferId() {

    return this.offerId;
  }

  /**
   * @param offerId new value of {@link #getofferId}.
   */
  public void setOfferId(Long offerId) {

    this.offerId = offerId;
  }

  /**
   * @return date
   */
  public LocalDateTime getDate() {

    return this.date;
  }

  /**
   * @param date new value of {@link #getdate}.
   */
  public void setDate(LocalDateTime date) {

    this.date = date;
  }

}
