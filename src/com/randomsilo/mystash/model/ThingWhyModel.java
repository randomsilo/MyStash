package com.randomsilo.mystash.model;

import com.randomsilo.mystash.db.pojo.ThingWhy;
import com.randomsilo.mystash.db.pojo.Why;

public class ThingWhyModel extends ThingWhy {

	private Why why;

	public Why getWhy() {
		return why;
	}

	public void setWhy(Why why) {
		this.why = why;
	}
	
}
