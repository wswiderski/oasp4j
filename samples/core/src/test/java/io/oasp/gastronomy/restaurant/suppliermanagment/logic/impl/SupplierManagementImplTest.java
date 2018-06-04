package io.oasp.gastronomy.restaurant.suppliermanagment.logic.impl;

import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.SupplierEntity;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.dao.SupplierDao;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.api.to.SupplierCto;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.api.to.SupplierEto;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.api.to.SupplierSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.impl.SuppliermanagementImpl;
import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationResultTo;
import io.oasp.module.test.common.base.ModuleTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the correct execution of the methods findOffer and findSupplierCto belonging to the
 * {@link SuppliermanagementImpl}
 */

public class SupplierManagementImplTest extends ModuleTest {

  private static final long ID = 1;

  /**
   * The System Under Test (SUT)
   */

  private SuppliermanagementImpl supplierManagement;

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  private SupplierDao supplierDao;

  @Mock
  private BeanMapper beanMapper;


  /**
   * This method initializes the object {@link SuppliermanagementImpl} and assigns the mocked objects of the classes
   * {@link SupplierDao} and {@link BeanMapper} to the attributes of the {@link SuppliermanagementImpl} object before tests,
   * if they are not null.
   */
  @Before
  public void init() {
    supplierManagement = new SuppliermanagementImpl();
    supplierManagement.setBeanMapper(beanMapper);
    supplierManagement.setSupplierDao(supplierDao);
  }

  /**
   * This method dereferences all object after each test
   */
  @After
  public void clean() {
    beanMapper = null;
    supplierDao = null;
    supplierManagement = null;
  }

  /**
   * This method tests the execution of the find supplier method belonging to the {@link SuppliermanagementImpl} class
   */
  @Test
  public void testfindSupplierById() {

    // given
    SupplierEntity SupplierEntity = mock(SupplierEntity.class);
    SupplierEto SupplierEto = new SupplierEto();

    when(supplierDao.findOne(ID)).thenReturn(SupplierEntity);
    when(beanMapper.map(SupplierEntity, SupplierEto.class)).thenReturn(SupplierEto);

    // when
    SupplierEto responseSupplierEto = supplierManagement.findSupplier(ID);

    // then
    assertThat(responseSupplierEto).isNotNull();
    assertThat(responseSupplierEto).isEqualTo(SupplierEto);
  }

  /**
   * This method tests the execution of the findSupplierCto method belonging to the {@link SuppliermanagementImpl} class
   */
  @Test
  public void testfindSupplierCto() {

    // given
    SupplierCto offerCto = new SupplierCto();
    SupplierEto supplierEto = new SupplierEto();

    offerCto.setSupplier(supplierEto);
    SupplierEntity supplierEntity = mock(SupplierEntity.class);

    when(supplierDao.findOne(ID)).thenReturn(supplierEntity);
    when(beanMapper.map(supplierEntity, SupplierEto.class)).thenReturn(supplierEto);

    // when
    SupplierCto responseSupplierCto = supplierManagement.findSupplierCto(ID);

    // then
    assertThat(responseSupplierCto).isNotNull();
    assertThat(responseSupplierCto.getSupplier()).isEqualTo(supplierEto);

  }


  @Test
  public void shouldSaveSupplier() {
    // given
    SupplierEto supplierEto = new SupplierEto();
    SupplierEntity supplierEntity = new SupplierEntity();
    when(beanMapper.map(supplierEntity, SupplierEto.class)).thenReturn(supplierEto);
    when(beanMapper.map(supplierEto, SupplierEntity.class)).thenReturn(supplierEntity);
    when(supplierDao.save(supplierEntity)).thenReturn(supplierEntity);

    // when
    SupplierEto savedSpecial = supplierManagement.saveSupplier(supplierEto);

    // then
    assertThat(savedSpecial).isNotNull();
    assertThat(savedSpecial).isEqualTo(supplierEto);

  }

  @Test
  public void testDeleteSupplier() {
    // given
    SupplierEntity supplierEntity = new SupplierEntity();
    supplierEntity.setId(1L);
    when(supplierDao.find(ID)).thenReturn(supplierEntity);
    // when
    supplierManagement.deleteSupplier(ID);
    // then
    Mockito.verify(supplierDao).delete(supplierEntity);

  }

  @Test
  public void shouldFindOfferWithSpecial() {
    // given
    SupplierSearchCriteriaTo criteria = new SupplierSearchCriteriaTo();
    SupplierEntity supplier = new SupplierEntity();
    supplier.setRate(20);
    supplier.setName("Capgemini");
    List<SupplierEntity> suppliersWithRate = Collections.singletonList(supplier);
    PaginatedListTo<SupplierEntity> paginatedResult = new PaginatedListTo<>(suppliersWithRate, new PaginationResultTo());
    when(supplierDao.findSuppliers(criteria)).thenReturn(paginatedResult);
    SupplierEto SupplierEto = new SupplierEto();
    SupplierEto.setRate(20);
    SupplierEto.setName("Capgemini");
    when(beanMapper.mapList(suppliersWithRate, SupplierEto.class)).thenReturn(Collections.singletonList(SupplierEto));

    // when
    PaginatedListTo<SupplierEto> result = supplierManagement.findSupplierEtos(criteria);

    // then
    assertThat(result.getResult().size()).isEqualTo(1);
    assertThat(result.getResult().get(0).getName()).isEqualTo(supplier.getName());
    assertThat(result.getResult().get(0).getRate()).isEqualTo(supplier.getRate());

  }

}
