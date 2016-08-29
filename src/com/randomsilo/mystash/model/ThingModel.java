package com.randomsilo.mystash.model;

import com.randomsilo.mystash.db.pojo.Thing;
import com.randomsilo.mystash.db.pojo.ThingBelongsTo;
import com.randomsilo.mystash.db.pojo.ThingImage;
import com.randomsilo.mystash.db.pojo.ThingLocation;

public class ThingModel {

	private Thing thing;
	private ThingBelongsTo thingBelongsTo;
	private ThingImage thingImage;
	private ThingLocation thingLocation;

	// constructors
	public ThingModel() {
		thing = new Thing();
		thingBelongsTo = new ThingBelongsTo();
		thingImage = new ThingImage();
		thingLocation = new ThingLocation();
	}
	
	public ThingModel(Thing thing, ThingBelongsTo thingBelongsTo, ThingImage thingImage, ThingLocation thingLocation) {
		this.thing = thing;
		this.thingBelongsTo = thingBelongsTo;
		this.thingImage = thingImage;
		this.thingLocation = thingLocation;
	}
	
	// Helpers
	public boolean exists() {
		if(getThingId() == null || getThingId() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public Long getThingId() {
		if(thing == null) {
			return null;
		}
		return thing.getId();
	}
	
	public void setThingId(Long l) {
		thing.setId(l);
		thingBelongsTo.setThingId(l);
		thingImage.setThingId(l);
		thingLocation.setThingId(l);
	}

	// property
	public Thing getThing() {
		return thing;
	}

	public void setThing(Thing thing) {
		this.thing = thing;
	}

	public ThingBelongsTo getThingBelongsTo() {
		return thingBelongsTo;
	}

	public void setThingBelongsTo(ThingBelongsTo thingBelongsTo) {
		this.thingBelongsTo = thingBelongsTo;
	}

	public ThingImage getThingImage() {
		return thingImage;
	}

	public void setThingImage(ThingImage image) {
		this.thingImage = image;
	}

	public ThingLocation getThingLocation() {
		return thingLocation;
	}

	public void setThingLocation(ThingLocation thingLocation) {
		this.thingLocation = thingLocation;
	}
	
}
