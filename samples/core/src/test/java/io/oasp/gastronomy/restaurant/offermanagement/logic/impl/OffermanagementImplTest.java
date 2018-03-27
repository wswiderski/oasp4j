package io.oasp.gastronomy.restaurant.offermanagement.logic.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.OfferEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.OfferDao;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferCto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.OfferSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialEto;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialSearchCriteriaTo;
import io.oasp.module.beanmapping.common.api.BeanMapper;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;
import io.oasp.module.jpa.common.api.to.PaginationResultTo;
import io.oasp.module.test.common.base.ModuleTest;

/**
 * This class tests the correct execution of the methods findOffer and findOfferCto belonging to the
 * {@link OffermanagementImpl}
 *
 */

public class OffermanagementImplTest extends ModuleTest {

  private static final long ID = 1;

  /**
   * The System Under Test (SUT)
   */

  private OffermanagementImpl offerManagementImpl;

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @Mock
  private OfferDao offerDao;

  @Mock
  private BeanMapper beanMapper;

  @Mock
  private SpecialDao specialDao;

  /**
   * This method initializes the object {@link OffermanagementImpl} and assigns the mocked objects of the classes
   * {@link OfferDao} and {@link BeanMapper} to the attributes of the {@link OffermanagementImpl} object before tests,
   * if they are not null.
   */
  @Before
  public void init() {

    this.offerManagementImpl = new OffermanagementImpl();
    this.offerManagementImpl.setOfferDao(this.offerDao);
    this.offerManagementImpl.setBeanMapper(this.beanMapper);
    this.offerManagementImpl.setSpecialDao(specialDao);
  }

  /**
   * This method dereferences all object after each test
   */
  @After
  public void clean() {

    this.beanMapper = null;
    this.offerDao = null;
    this.offerManagementImpl = null;
  }

  /**
   * This method tests the execution of the findOffer method belonging to the {@link OffermanagementImpl} class
   */
  @Test
  public void findOffer() {

    // given
    OfferEntity offerEntity = mock(OfferEntity.class);
    OfferEto offerEto = new OfferEto();

    when(this.offerDao.findOne(ID)).thenReturn(offerEntity);
    when(this.beanMapper.map(offerEntity, OfferEto.class)).thenReturn(offerEto);

    // when
    OfferEto responseOfferEto = this.offerManagementImpl.findOffer(ID);

    // then
    assertThat(responseOfferEto).isNotNull();
    assertThat(responseOfferEto).isEqualTo(offerEto);
  }

  /**
   * This method tests the execution of the findOfferCto method belonging to the {@link OffermanagementImpl} class
   */
  @Test
  public void findOfferCto() {

    // given
    OfferCto offerCto = new OfferCto();
    OfferEto offerEto = new OfferEto();

    offerCto.setOffer(offerEto);
    OfferEntity offerEntity = mock(OfferEntity.class);

    when(this.offerDao.findOne(ID)).thenReturn(offerEntity);
    when(this.beanMapper.map(offerEntity, OfferEto.class)).thenReturn(offerEto);

    // when
    OfferCto responseOfferCto = this.offerManagementImpl.findOfferCto(ID);

    // then
    assertThat(responseOfferCto).isNotNull();
    assertThat(responseOfferCto.getOffer()).isEqualTo(offerEto);

  }
  
	@Test
	public void shouldFindAllActiveSpecials() {
		// given
		SpecialSearchCriteriaTo searchCriteria = new SpecialSearchCriteriaTo();
		SpecialEntity special = new SpecialEntity();
		List<SpecialEntity> specials = Collections.singletonList(special);
		when(specialDao.findActiveSpecials(searchCriteria)).thenReturn(specials);
		SpecialEto specialEto = new SpecialEto();
		List<SpecialEto> specialEtos = Collections.singletonList(specialEto);
		when(this.beanMapper.mapList(specials, SpecialEto.class)).thenReturn(specialEtos);

		// when
		List<SpecialEto> activeSpecials = this.offerManagementImpl.getActiveSpecials(searchCriteria);

		// then
		assertThat(activeSpecials).isNotNull();
		assertThat(activeSpecials.size()).isEqualTo(1);
		assertThat(activeSpecials.get(0)).isEqualTo(specialEto);

	}
	
	@Test
	public void shouldSaveSpecial() {
		// given
		SpecialEto specialEto = new SpecialEto();
		SpecialEntity special = new SpecialEntity();
		when(beanMapper.map(special , SpecialEto.class)).thenReturn(specialEto);
		when(beanMapper.map(specialEto , SpecialEntity.class)).thenReturn(special);
		when(specialDao.save(special)).thenReturn(special);
		
		// when
		SpecialEto savedSpecial = offerManagementImpl.saveSpecial(specialEto);
		
		// then
		assertThat(savedSpecial).isNotNull();
		assertThat(savedSpecial).isEqualTo(specialEto);
		
	}
	
	@Test
	public void shouldDeleteSpecial() {
		// given
		// when
		offerManagementImpl.deleteSpecial(1L);
		
		// then
		Mockito.verify(specialDao).delete(1L);
		
	}
	
	@Test
	public void shouldFindOfferWithSpecial() {
		// given
		OfferSearchCriteriaTo criteria = new OfferSearchCriteriaTo();
		OfferEntity offer = new OfferEntity();
		offer.setPrice(new Money(BigDecimal.TEN));
		offer.setNumber(1L);
		List<OfferEntity> offersWithoutSpecials = Collections.singletonList(offer);
		PaginatedListTo<OfferEntity> paginatedOffersWithoutSpecials = new PaginatedListTo<>(offersWithoutSpecials, new PaginationResultTo());
		when(offerDao.findOffers(criteria)).thenReturn(paginatedOffersWithoutSpecials);
		OfferEto offerEto = new OfferEto();
		offerEto.setNumber(1L);
		offerEto.setPrice(new Money(BigDecimal.TEN));
		when(beanMapper.mapList(offersWithoutSpecials, OfferEto.class)).thenReturn(Collections.singletonList(offerEto ));
		when(specialDao.findBestActiveSpecial(Mockito.argThat(new HasTheSameOfferNumber(1L)))).thenReturn(new Money(BigDecimal.ONE));
		
		// when
		PaginatedListTo<OfferEto> offers = offerManagementImpl.findOfferEtos(criteria);
		
		// then
		assertThat(offers.getResult().size()).isEqualTo(1);
		assertThat(offers.getResult().get(0).getPrice()).isEqualTo(new Money(BigDecimal.valueOf(9)));
		assertThat(offers.getResult().get(0).getSpecial()).isEqualTo(new Money(BigDecimal.ONE));
		
	}
	
	private class HasTheSameOfferNumber extends ArgumentMatcher<SpecialSearchCriteriaTo> {
		
		private long offerNumber;

		public HasTheSameOfferNumber(long offerNumber) {
			this.offerNumber = offerNumber;
		}
		
		@Override
		public boolean matches(Object argument) {
			return (argument instanceof SpecialSearchCriteriaTo)
					&& ((SpecialSearchCriteriaTo) argument).getOfferNumber().equals(offerNumber);
		}
	}

}
