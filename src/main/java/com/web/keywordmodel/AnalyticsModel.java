package com.web.keywordmodel;

public class AnalyticsModel {

	private long searchcount;
	private long resultcount;
	private long timetaken;

	public long getSearchcount() {
		return searchcount;
	}
	public void setSearchcount(long searchcount) {
		this.searchcount = searchcount;
	}

	public long getResultcount() {
		return resultcount;
	}
	public void setResultcount(long resultcount) {
		this.resultcount = resultcount;
	}

	public long getTimetaken() {
		return timetaken;
	}
	public void setTimetaken(long timetaken) {
		this.timetaken = timetaken;
	}
}