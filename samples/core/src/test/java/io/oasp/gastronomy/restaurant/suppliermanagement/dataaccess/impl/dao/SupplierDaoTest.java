package io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.impl.dao;

import io.oasp.gastronomy.restaurant.SpringBootApp;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.SupplierEntity;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.dao.SupplierDao;
import io.oasp.module.test.common.base.ComponentTest;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@SpringBootTest(classes = {SpringBootApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SupplierDaoTest extends ComponentTest {

  @Inject
  private SupplierDao supplierDao;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  public void testPersistingSupplier() {
    // given
    SupplierEntity supplier = new SupplierEntity();
    assertThat(supplier.getId()).isNull();
    supplier.setName("Max Source");

    // when
    supplierDao.save(supplier);

    // then
    assertThat(supplier.getId()).isNotNull();
  }
}
