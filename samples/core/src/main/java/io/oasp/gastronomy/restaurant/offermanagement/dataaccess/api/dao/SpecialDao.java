package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationRevisionedDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

/**
 * @author WSWIDERS
 *
 */
public interface SpecialDao extends ApplicationRevisionedDao<SpecialEntity>, MasterDataDao<SpecialEntity> {

}
