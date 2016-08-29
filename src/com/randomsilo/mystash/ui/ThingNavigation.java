package com.randomsilo.mystash.ui;

import java.util.ArrayList;
import java.util.List;

import com.randomsilo.mystash.BrowseActivity;
import com.randomsilo.mystash.ThingDetailsActivity;
import com.randomsilo.mystash.ThingImagesActivity;
import com.randomsilo.mystash.ThingLocationActivity;
import com.randomsilo.mystash.ThingMetadataActivity;
import com.randomsilo.mystash.ThingPropertiesActivity;
import com.randomsilo.mystash.ThingResourceActivity;
import com.randomsilo.mystash.ThingScheduleActivity;

public class ThingNavigation {
	@SuppressWarnings("rawtypes")
	private List<Class> horizontalActivities;
	private int currentIndex = 0;
	
	@SuppressWarnings("rawtypes")
	public ThingNavigation(boolean hasCamera) {
		horizontalActivities = new ArrayList<Class>();
		horizontalActivities.add(BrowseActivity.class);
		horizontalActivities.add(ThingDetailsActivity.class);
		horizontalActivities.add(ThingScheduleActivity.class);
		horizontalActivities.add(ThingPropertiesActivity.class);
		horizontalActivities.add(ThingResourceActivity.class);
		horizontalActivities.add(ThingMetadataActivity.class);
		if(hasCamera) {
			horizontalActivities.add(ThingImagesActivity.class);
		}
		horizontalActivities.add(ThingLocationActivity.class);
		
		
	}
	
	@SuppressWarnings("rawtypes")
	public void setCurrentActivity(Class current) {
		for(int i=0; i<horizontalActivities.size(); i++) {
			Class stored = horizontalActivities.get(i);
			if(current.getCanonicalName().equals(stored.getCanonicalName())) {
				currentIndex = i;
				break;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Class getPreviousActivity() {
		int index = currentIndex;
		index--;
		
		if(index < 0) {
			index = horizontalActivities.size() -1;
		}
		return horizontalActivities.get(index);
	}
	
	@SuppressWarnings("rawtypes")
	public Class getNextActivity() {
		int index = currentIndex;
		index++;
		
		if(index == horizontalActivities.size()) {
			index = 0;
		}
		return horizontalActivities.get(index);
	}
}
