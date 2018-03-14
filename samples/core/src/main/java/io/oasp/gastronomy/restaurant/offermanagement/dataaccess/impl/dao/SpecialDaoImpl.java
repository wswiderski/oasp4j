package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialSearchCriteriaTo;

import javax.inject.Named;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.alias.Alias.$;

@Named
public class SpecialDaoImpl extends ApplicationMasterDataDaoImpl<SpecialEntity> implements SpecialDao {

  /**
   * The constructor.
   */
  public SpecialDaoImpl() {

    super();
  }

  @Override
  protected Class<SpecialEntity> getEntityClass() {

    return SpecialEntity.class;
  }

  @Override
  public List<SpecialEntity> findActiveSpecials(SpecialSearchCriteriaTo criteria) {
    DayOfWeek currentDayOfWeek = criteria.getDateOfCheckingOffers().getDayOfWeek();
    int currentHour = criteria.getDateOfCheckingOffers().getHour();

    SpecialEntity special = Alias.alias(SpecialEntity.class);
    EntityPathBase<SpecialEntity> alias = $(special);
    JPAQuery<SpecialEntity> query = new JPAQuery<SpecialEntity>(getEntityManager()).from(alias);

    buldQueryForDateInActivePeriod(currentDayOfWeek, currentHour, special, query);

    return query.fetch();
  }

  @Override
  public Money findBestActiveSpecial(SpecialSearchCriteriaTo criteria) {
    SpecialEntity special = Alias.alias(SpecialEntity.class);
    EntityPathBase<SpecialEntity> alias = $(special);
    JPAQuery<SpecialEntity> query = new JPAQuery<SpecialEntity>(getEntityManager()).from(alias);
    LocalDateTime currentDateTime = criteria.getDateOfCheckingOffers();
    if (criteria.getOfferNumber() != null) {
      query.where($(special.getOffer().getNumber()).eq(criteria.getOfferNumber()));
    }
    this.buldQueryForDateInActivePeriod(currentDateTime.getDayOfWeek(), currentDateTime.getHour(), special, query);

    query.orderBy($(special.getSpecialPrice()).asc());
    SpecialEntity specialEntityWithBestPrice = query.fetchFirst();

    return specialEntityWithBestPrice != null ? specialEntityWithBestPrice.getSpecialPrice() : null;
  }

  private void buldQueryForDateInActivePeriod(DayOfWeek currentDayOfWeek, int currentHour, SpecialEntity special, JPAQuery<SpecialEntity> query) {
    query.where($(special.getActivePeriod().getStartingDay()).loe(currentDayOfWeek));
    query.where($(special.getActivePeriod().getEndingDay()).goe(currentDayOfWeek));
    query.where($(special.getActivePeriod().getStartingHour()).loe(currentHour));
    query.where($(special.getActivePeriod().getEndingHour()).goe(currentHour));
  }

}
