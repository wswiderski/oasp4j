package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import org.springframework.stereotype.Repository;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;

/**
 * @author WSWIDERS
 *
 */
@Repository
public class SpecialDaoImpl extends ApplicationMasterDataDaoImpl<SpecialEntity> implements SpecialDao {

  /**
   * The constructor.
   */
  public SpecialDaoImpl() {

    super();
  }

  @Override
  public Class<SpecialEntity> getEntityClass() {

    return SpecialEntity.class;
  }
}
