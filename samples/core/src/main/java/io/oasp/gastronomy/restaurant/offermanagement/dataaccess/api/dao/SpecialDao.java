package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationRevisionedDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

import java.time.LocalDateTime;
import java.util.List;

public interface SpecialDao extends ApplicationRevisionedDao<SpecialEntity>, MasterDataDao<SpecialEntity> {

  List<SpecialEntity> findActiveSpecials(LocalDateTime dateTimeToCheck);

}
