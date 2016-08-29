package com.randomsilo.mystash.model;

import com.randomsilo.mystash.db.pojo.ThingWhat;
import com.randomsilo.mystash.db.pojo.What;

public class ThingWhatModel extends ThingWhat {

	private What what;

	public What getWhat() {
		return what;
	}

	public void setWhat(What what) {
		this.what = what;
	}
	
}
