package com.randomsilo.mystash.model;

import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.db.pojo.ThingProvide;

public class ThingProvideModel extends ThingProvide {

	private Resource resource;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
}
