package com.randomsilo.mystash.model;

import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.db.pojo.ThingConsume;

public class ThingConsumeModel extends ThingConsume {

	private Resource resource;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
}
