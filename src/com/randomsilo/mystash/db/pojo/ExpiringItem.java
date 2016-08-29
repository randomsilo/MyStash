package com.randomsilo.mystash.db.pojo;

public class ExpiringItem {
	String dateType;
	String isoDate;
	Long dateDifference;
	Long thingId;
	String thingName;
	String parentName;
	String userMessage;
	
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getIsoDate() {
		return isoDate;
	}
	public void setIsoDate(String isoDate) {
		this.isoDate = isoDate;
	}
	public Long getDateDifference() {
		return dateDifference;
	}
	public void setDateDifference(Long dateDifference) {
		this.dateDifference = dateDifference;
	}
	public String getThingName() {
		return thingName;
	}
	public void setThingName(String thingName) {
		this.thingName = thingName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public Long getThingId() {
		return thingId;
	}
	public void setThingId(Long thingId) {
		this.thingId = thingId;
	}
	
	
}
