package com.randomsilo.mystash.db.pojo;

public class AttentionDate extends BaseDescriptor {

	public AttentionDate() {
		
	}
	
	public AttentionDate(String tag, String description) {
		this.setTag(tag);
		this.setDescription(description);
	}
}
