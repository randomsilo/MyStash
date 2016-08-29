package com.randomsilo.mystash.db.pojo;

public class ThingExpiresOn extends BasePojoThingRelated {
	private Long expirationDate;
	private Integer notifyDaysBefore;
	private String expireDetails;
	
	public Long getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Long expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Integer getNotifyDaysBefore() {
		return notifyDaysBefore;
	}
	public void setNotifyDaysBefore(Integer notifyDaysBefore) {
		this.notifyDaysBefore = notifyDaysBefore;
	}
	public String getExpireDetails() {
		return expireDetails;
	}
	public void setExpireDetails(String expireDetails) {
		this.expireDetails = expireDetails;
	}
	
	
}
