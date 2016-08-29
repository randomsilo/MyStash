package com.randomsilo.mystash.service;


public class ThingDeleteResponse {
	private boolean deleted;
	private Long parentId;

	public ThingDeleteResponse() {
		deleted = false;
		parentId = null;
	}
	
	public ThingDeleteResponse(boolean deleted, Long parentId) {
		this.deleted = deleted;
		this.parentId = parentId;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean wasDeleted() {
		return deleted;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}
	
	
}
