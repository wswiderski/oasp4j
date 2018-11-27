package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import static com.querydsl.core.alias.Alias.$;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Named;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

import io.oasp.gastronomy.restaurant.general.common.api.to.SpecialSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationMasterDataDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;

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

      BooleanBuilder builder = new BooleanBuilder();
      builder.or($(special.getActivePeriod().getEndingDay()).loe(day)
          .and($(special.getActivePeriod().getStartingDay()).goe(day)));

      builder.or($(special.getActivePeriod().getEndingHour()).loe(hour)
          .and($(special.getActivePeriod().getStartingHour()).goe(hour)));
      query.where(builder);
    }

    List<SpecialEntity> result = query.fetch();
    return result;
  }

}
