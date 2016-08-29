package com.randomsilo.mystash.db.pojo;

public class Resource extends BaseDescriptor {
	
	public Resource() {
		
	}
	
	public Resource(String tag, String description) {
		this.setTag(tag);
		this.setDescription(description);
	}
}
