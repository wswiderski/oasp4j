package io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationDao;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.SupplierEntity;
import io.oasp.module.jpa.dataaccess.api.MasterDataDao;

public interface SupplierDao extends ApplicationDao<SupplierEntity>, MasterDataDao<SupplierEntity> {
}
