package com.randomsilo.mystash.db.pojo;

public class ThingMaintenanceOn extends BasePojoThingRelated {
	private Long lastMaintDate;
	private Integer durationDays;
	private Integer notifyDaysBefore;
	private String maintDetails;
	
	public Long getLastMaintDate() {
		return lastMaintDate;
	}
	public void setLastMaintDate(Long lastMaintDate) {
		this.lastMaintDate = lastMaintDate;
	}
	public Integer getDurationDays() {
		return durationDays;
	}
	public void setDurationDays(Integer durationDays) {
		this.durationDays = durationDays;
	}
	public Integer getNotifyDaysBefore() {
		return notifyDaysBefore;
	}
	public void setNotifyDaysBefore(Integer notifyDaysBefore) {
		this.notifyDaysBefore = notifyDaysBefore;
	}
	public String getMaintDetails() {
		return maintDetails;
	}
	public void setMaintDetails(String maintDetails) {
		this.maintDetails = maintDetails;
	}
	
	
}
