package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationRevisionedDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialSearchCriteriaTo;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

import java.time.LocalDateTime;
import java.util.List;

public interface SpecialDao extends ApplicationRevisionedDao<SpecialEntity>, MasterDataDao<SpecialEntity> {

  /**
   * Find active special offers for given date of checking.
   * @param criteria with filled date of checking
   * @return all found active special offers
   */
  List<SpecialEntity> findActiveSpecials(SpecialSearchCriteriaTo criteria);

  /**
   * Find active special offer for given date of checking and given offer number with best (smallest) price.
   * @param criteria with filled date of checking, offer number
   * @return best found special price
   */
  Money findBestActiveSpecial(SpecialSearchCriteriaTo criteria);

}
