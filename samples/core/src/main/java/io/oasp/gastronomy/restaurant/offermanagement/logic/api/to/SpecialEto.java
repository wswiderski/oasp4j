package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Special;
import io.oasp.module.basic.common.api.to.AbstractEto;

public class SpecialEto extends AbstractEto implements Special {

	private static final long serialVersionUID = -4457788068370871480L;

	private String name;

	private OfferEto offer;

	private WeeklyPeriod activePeriod;

	private Money specialPrice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OfferEto getOffer() {
		return offer;
	}

	public void setOffer(OfferEto offer) {
		this.offer = offer;
	}

	public WeeklyPeriod getActivePeriod() {
		return activePeriod;
	}

	public void setActivePeriod(WeeklyPeriod activePeriod) {
		this.activePeriod = activePeriod;
	}

	public Money getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Money specialPrice) {
		this.specialPrice = specialPrice;
	}

}
