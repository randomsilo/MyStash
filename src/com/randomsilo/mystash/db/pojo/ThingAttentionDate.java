package com.randomsilo.mystash.db.pojo;


public class ThingAttentionDate extends BasePojoThingRelated {
	private Long attentionDateId;
	private String value; // locale format
	private String valueDate; // YYYY-MM-DD HH:MM:SS.SSS
	
	public Long getAttentionDateId() {
		return attentionDateId;
	}
	public void setAttentionDateId(Long attentionDateId) {
		this.attentionDateId = attentionDateId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	
}
