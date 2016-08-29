package com.randomsilo.mystash.db.pojo;

public class ThingProvide extends BasePojoThingRelated {
	private Long resourceId;
	private String resourceDetails;
	private Integer resourceQuantity;
	
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getResourceQuantity() {
		return resourceQuantity;
	}
	public void setResourceQuantity(Integer quantity) {
		this.resourceQuantity = quantity;
	}
	public String getResourceDetails() {
		return resourceDetails;
	}
	public void setResourceDetails(String resourceDetails) {
		this.resourceDetails = resourceDetails;
	}
	
}
