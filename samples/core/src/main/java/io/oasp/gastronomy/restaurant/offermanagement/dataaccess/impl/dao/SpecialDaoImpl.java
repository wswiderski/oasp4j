package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import static com.querydsl.core.alias.Alias.$;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Named;

import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialSearchCriteriaTo;

/**
 * @author WSWIDERS
 *
 */
@Named
public class SpecialDaoImpl extends ApplicationMasterDataDaoImpl<SpecialEntity> implements SpecialDao {

  @Override
  public Class<SpecialEntity> getEntityClass() {

    return SpecialEntity.class;
  }

  @Override
  public List<SpecialEntity> findByCriteria(SpecialSearchCriteriaTo criteria) {

    SpecialEntity special = Alias.alias(SpecialEntity.class);
    EntityPathBase<SpecialEntity> alias = $(special);
    JPAQuery<SpecialEntity> query = new JPAQuery<SpecialEntity>(getEntityManager()).from(alias);

    String name = criteria.getName();
    if (name != null) {
      query.where($(special.getName()).likeIgnoreCase(Expressions.asString("%").concat(name).concat("%")));
    }

    Long offerId = criteria.getOfferId();
    if (offerId != null) {
      query.where($(special.getOffer().getId()).eq(offerId));
    }

    LocalDateTime date = criteria.getDate();
    if (date != null) {

      DayOfWeek day = date.getDayOfWeek();
      int hour = date.getHour();

      query.where($(special.getActivePeriod().getEndingDay()).goe(day));
      query.where($(special.getActivePeriod().getStartingDay()).loe(day));

      query.where($(special.getActivePeriod().getEndingHour()).goe(hour));
      query.where($(special.getActivePeriod().getStartingHour()).loe(hour));
    }

    List<SpecialEntity> result = query.fetch();
    return result;
  }

}
