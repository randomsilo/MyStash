package com.randomsilo.mystash.model;

import com.randomsilo.mystash.db.pojo.Property;
import com.randomsilo.mystash.db.pojo.ThingProperty;

public class ThingPropertyModel extends ThingProperty {

	private Property property;

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
	
}
