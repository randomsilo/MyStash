package com.randomsilo.mystash.service;

import com.randomsilo.mystash.model.ThingModel;

public class ThingSaveResponse {
	private boolean saved;
	private ThingModel thingModel;

	public ThingSaveResponse() {
		saved = false;
		thingModel = null;
	}
	
	public ThingSaveResponse(boolean saved, ThingModel thingModel) {
		this.saved = saved;
		this.thingModel = thingModel;
	}
	
	public boolean wasSaved() {
		return saved;
	}
	
	public ThingModel getThingModel() {
		return thingModel;
	}
	
	
}
