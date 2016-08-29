package com.randomsilo.mystash.db.pojo;

public class ThingBelongsTo extends BasePojoThingRelated {
	private Long parentId;
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}