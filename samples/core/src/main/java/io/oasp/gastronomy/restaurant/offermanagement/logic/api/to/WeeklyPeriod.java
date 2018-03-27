package io.oasp.gastronomy.restaurant.offermanagement.logic.api.to;

import java.time.DayOfWeek;

public class WeeklyPeriod {
	
	private DayOfWeek startingDay;

	private int startingHour;

	private DayOfWeek endingDay;

	private int endingHour;

	public DayOfWeek getStartingDay() {
		return startingDay;
	}

	public void setStartingDay(DayOfWeek startingDay) {
		this.startingDay = startingDay;
	}

	public int getStartingHour() {
		return startingHour;
	}

	public void setStartingHour(int startingHour) {
		this.startingHour = startingHour;
	}

	public DayOfWeek getEndingDay() {
		return endingDay;
	}

	public void setEndingDay(DayOfWeek endingDay) {
		this.endingDay = endingDay;
	}

	public int getEndingHour() {
		return endingHour;
	}

	public void setEndingHour(int endingHour) {
		this.endingHour = endingHour;
	}

}
