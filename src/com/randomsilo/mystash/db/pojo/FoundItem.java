package com.randomsilo.mystash.db.pojo;

public class FoundItem {
	Long thingId;
	String thingName;
	String parentName;
	String userMessage;
	Long resourceCount;
	
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
	public Long getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(Long resourceCount) {
		this.resourceCount = resourceCount;
	}
	
	
}
