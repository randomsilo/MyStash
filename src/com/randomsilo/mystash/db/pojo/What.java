package com.randomsilo.mystash.db.pojo;

public class What extends BaseDescriptor {

	public What() {
		
	}
	
	public What(String tag, String description) {
		this.setTag(tag);
		this.setDescription(description);
	}
}
