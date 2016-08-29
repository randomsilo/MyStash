package com.randomsilo.mystash.db.pojo;


public class Thing extends BasePojo {
	private String tag;
	private String description;
	private Double actualCost;
	private Double replacementCost;
	
	public Thing() {
		super();
		tag = "";
		description = "";
		actualCost = 0.00;
		replacementCost = 0.00;
	}
	
	public Thing(String tag, String description) {
		super();
		setTag(tag);
		setDescription(description);
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getActualCost() {
		return actualCost;
	}
	public void setActualCost(Double actualCost) {
		this.actualCost = actualCost;
	}
	public Double getReplacementCost() {
		return replacementCost;
	}
	public void setReplacementCost(Double replacementCost) {
		this.replacementCost = replacementCost;
	}
	
}
