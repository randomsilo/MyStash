package com.randomsilo.mystash.db.pojo;

public class Property extends BaseDescriptor {

	public Property() {
		
	}
	
	public Property(String tag, String description) {
		this.setTag(tag);
		this.setDescription(description);
	}
}
