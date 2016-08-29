package com.randomsilo.mystash.session;

import android.content.Context;

import com.randomsilo.mystash.db.pojo.AttentionDate;
import com.randomsilo.mystash.db.pojo.Property;
import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.db.pojo.What;
import com.randomsilo.mystash.db.pojo.Why;
import com.randomsilo.mystash.model.ThingAttentionDateModel;
import com.randomsilo.mystash.model.ThingConsumeModel;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.model.ThingPropertyModel;
import com.randomsilo.mystash.model.ThingProvideModel;
import com.randomsilo.mystash.model.ThingWhatModel;
import com.randomsilo.mystash.model.ThingWhyModel;
import com.randomsilo.mystash.service.NotificationService;
import com.randomsilo.mystash.service.ThingSaveResponse;
import com.randomsilo.mystash.service.ThingService;

public class MyStashSession {

	private final static MyStashSession INSTANCE = new MyStashSession();
	private ThingService thingService; 
	private NotificationService notificationService;
	private ThingModel activeThing;
	private AttentionDate activeAttentionDate;
	private Resource activeResource;
	private Property activeProperty;
	private What activeWhat;
	private Why activeWhy;
	private int lastSelectedResourceType = 0;
	private int lastSelectedMetaData = 0;
	private int lastSelectedMetaDataWhat = 0;
	private int lastSelectedMetaDataWhy = 0;
	private int lastSelectedResourceSearchType = 0;
	private int lastSelectedResourceConsumes = 0;
	private int lastSelectedResourceProvides = 0;
	private ThingAttentionDateModel activeAttentionDateModel;
	private ThingPropertyModel activePropertyModel;
	private ThingConsumeModel activeConsumeModel; 
	private ThingProvideModel activeProvideModel;
	private ThingWhatModel activeWhatModel;
	private ThingWhyModel activeWhyModel;

	private MyStashSession() {
		
	}
	
	public static MyStashSession getInstance() {
		return INSTANCE;
	}
	
	public void Init(Context context) {
		setThingService(new ThingService(context));
		setNotificationService(new NotificationService(context));
	}

	public ThingService getThingService() {
		return thingService;
	}

	public void setThingService(ThingService thingService) {
		this.thingService = thingService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public ThingModel getActiveThing() {
		return activeThing;
	}

	public void setActiveThing(ThingModel activeThing) {
		this.activeThing = activeThing;
	}
	
	public boolean isThingSelected() {
		if(getActiveThing() != null && getActiveThing().getThingId() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean save() {
		ThingSaveResponse r = thingService.save(getActiveThing());
		if(r.wasSaved()) {
			setActiveThing(r.getThingModel());
		} 
		
		return r.wasSaved();
	}

	public Resource getActiveResource() {
		return activeResource;
	}

	public void setActiveResource(Resource resource) {
		this.activeResource = resource;
	}

	public AttentionDate getActiveAttentionDate() {
		return activeAttentionDate;
	}
	
	public void setActiveAttentionDate(AttentionDate activeAttentionDate) {
		this.activeAttentionDate = activeAttentionDate;
	}

	public Property getActiveProperty() {
		return activeProperty;
	}
	
	public void setActiveProperty(Property activeProperty) {
		this.activeProperty = activeProperty;
	}

	public What getActiveWhat() {
		return activeWhat;
	}

	public void setActiveWhat(What activeWhat) {
		this.activeWhat = activeWhat;
	}

	public Why getActiveWhy() {
		return activeWhy;
	}

	public void setActiveWhy(Why activeWhy) {
		this.activeWhy = activeWhy;
	}

	public int getLastSelectedResourceType() {
		return lastSelectedResourceType;
	}

	public void setLastSelectedResourceType(int lastSelectedResourceType) {
		this.lastSelectedResourceType = lastSelectedResourceType;
	}
	
	public int getLastSelectedMetaData() {
		return lastSelectedMetaData;
	}

	public void setLastSelectedMetaData(int lastSelectedMetaData) {
		this.lastSelectedMetaData = lastSelectedMetaData;
	}

	public ThingAttentionDateModel getActiveAttentionDateModel() {
		return activeAttentionDateModel;
	}
	
	public int getLastSelectedMetaDataWhat() {
		return lastSelectedMetaDataWhat;
	}

	public void setLastSelectedMetaDataWhat(int lastSelectedMetaDataWhat) {
		this.lastSelectedMetaDataWhat = lastSelectedMetaDataWhat;
	}

	public int getLastSelectedMetaDataWhy() {
		return lastSelectedMetaDataWhy;
	}

	public void setLastSelectedMetaDataWhy(int lastSelectedMetaDataWhy) {
		this.lastSelectedMetaDataWhy = lastSelectedMetaDataWhy;
	}

	public int getLastSelectedResourceSearchType() {
		return lastSelectedResourceSearchType;
	}

	public void setLastSelectedResourceSearchType(int lastSelectedResourceSearchType) {
		this.lastSelectedResourceSearchType = lastSelectedResourceSearchType;
	}

	public int getLastSelectedResourceConsumes() {
		return lastSelectedResourceConsumes;
	}

	public void setLastSelectedResourceConsumes(int lastSelectedResourceConsumes) {
		this.lastSelectedResourceConsumes = lastSelectedResourceConsumes;
	}

	public int getLastSelectedResourceProvides() {
		return lastSelectedResourceProvides;
	}

	public void setLastSelectedResourceProvides(int lastSelectedResourceProvides) {
		this.lastSelectedResourceProvides = lastSelectedResourceProvides;
	}

	public ThingPropertyModel getActivePropertyModel() {
		return activePropertyModel;
	}
	
	public ThingConsumeModel getActiveConsumeModel() {
		return activeConsumeModel;
	}
	
	public ThingProvideModel getActiveProvideModel() {
		return activeProvideModel;
	}

	public void setActiveAttentionDateModel(ThingAttentionDateModel activeAttentionDateModel) {
		this.activeAttentionDateModel = activeAttentionDateModel;
	}
	
	public void setActivePropertyModel(ThingPropertyModel activePropertyModel) {
		this.activePropertyModel = activePropertyModel;
	}
	
	public void setActiveConsumeModel(ThingConsumeModel activeConsumeModel) {
		this.activeConsumeModel = activeConsumeModel;
	}
	
	public void setActiveProvideModel(ThingProvideModel activeProvideModel) {
		this.activeProvideModel = activeProvideModel;
	}
	
	public ThingWhatModel getActiveWhatModel() {
		return activeWhatModel;
	}

	public void setActiveWhatModel(ThingWhatModel activeWhatModel) {
		this.activeWhatModel = activeWhatModel;
	}

	public ThingWhyModel getActiveWhyModel() {
		return activeWhyModel;
	}

	public void setActiveWhyModel(ThingWhyModel activeWhyModel) {
		this.activeWhyModel = activeWhyModel;
	}
	
	

}
