package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import java.util.List;

import io.oasp.gastronomy.restaurant.general.common.api.to.SpecialSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationRevisionedDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

/**
 * @author WSWIDERS
 *
 */
public interface SpecialDao extends ApplicationRevisionedDao<SpecialEntity>, MasterDataDao<SpecialEntity> {

  /**
   * @param criteria
   * @return
   */
  public List<SpecialEntity> findByCriteria(SpecialSearchCriteriaTo criteria);
}
