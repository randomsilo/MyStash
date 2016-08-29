package com.randomsilo.mystash.model;

import com.randomsilo.mystash.db.pojo.AttentionDate;
import com.randomsilo.mystash.db.pojo.ThingAttentionDate;

public class ThingAttentionDateModel extends ThingAttentionDate {

	private AttentionDate attentionDate;

	public AttentionDate getAttentionDate() {
		return attentionDate;
	}

	public void setAttentionDate(AttentionDate attentionDate) {
		this.attentionDate = attentionDate;
	}
	
}
