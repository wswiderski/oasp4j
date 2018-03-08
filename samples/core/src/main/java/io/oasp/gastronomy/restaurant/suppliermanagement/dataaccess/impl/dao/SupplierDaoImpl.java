package io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.SupplierEntity;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.dao.SupplierDao;

import javax.inject.Named;

@Named
public class SupplierDaoImpl extends ApplicationMasterDataDaoImpl<SupplierEntity> implements SupplierDao {

  public SupplierDaoImpl() {
    super();
  }

  @Override
  protected Class<SupplierEntity> getEntityClass() {
    return SupplierEntity.class;
  }
}
