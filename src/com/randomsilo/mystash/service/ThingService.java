package com.randomsilo.mystash.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.dao.AttentionDateDao;
import com.randomsilo.mystash.db.dao.PropertyDao;
import com.randomsilo.mystash.db.dao.ResourceDao;
import com.randomsilo.mystash.db.dao.ThingAttentionDateDao;
import com.randomsilo.mystash.db.dao.ThingBelongsToDao;
import com.randomsilo.mystash.db.dao.ThingConsumeDao;
import com.randomsilo.mystash.db.dao.ThingDao;
import com.randomsilo.mystash.db.dao.ThingExpiresOnDao;
import com.randomsilo.mystash.db.dao.ThingImageDao;
import com.randomsilo.mystash.db.dao.ThingLocationDao;
import com.randomsilo.mystash.db.dao.ThingMaintenanceOnDao;
import com.randomsilo.mystash.db.dao.ThingPropertyDao;
import com.randomsilo.mystash.db.dao.ThingProvideDao;
import com.randomsilo.mystash.db.dao.ThingWhatDao;
import com.randomsilo.mystash.db.dao.ThingWhyDao;
import com.randomsilo.mystash.db.dao.WhatDao;
import com.randomsilo.mystash.db.dao.WhyDao;
import com.randomsilo.mystash.db.pojo.AttentionDate;
import com.randomsilo.mystash.db.pojo.ExpiringItem;
import com.randomsilo.mystash.db.pojo.FoundItem;
import com.randomsilo.mystash.db.pojo.Property;
import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.db.pojo.Thing;
import com.randomsilo.mystash.db.pojo.ThingAttentionDate;
import com.randomsilo.mystash.db.pojo.ThingBelongsTo;
import com.randomsilo.mystash.db.pojo.ThingConsume;
import com.randomsilo.mystash.db.pojo.ThingImage;
import com.randomsilo.mystash.db.pojo.ThingLocation;
import com.randomsilo.mystash.db.pojo.ThingProperty;
import com.randomsilo.mystash.db.pojo.ThingProvide;
import com.randomsilo.mystash.db.pojo.ThingTotalCost;
import com.randomsilo.mystash.db.pojo.ThingWhat;
import com.randomsilo.mystash.db.pojo.ThingWhy;
import com.randomsilo.mystash.db.pojo.What;
import com.randomsilo.mystash.db.pojo.Why;
import com.randomsilo.mystash.model.ThingAttentionDateModel;
import com.randomsilo.mystash.model.ThingConsumeModel;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.model.ThingPropertyModel;
import com.randomsilo.mystash.model.ThingProvideModel;
import com.randomsilo.mystash.model.ThingWhatModel;
import com.randomsilo.mystash.model.ThingWhyModel;

public class ThingService {
	private static final int NOTIFY_DAYS_BEFORE_EXPIRATION = 30;
	private Context context;
	private MySQLiteHelper dbHelper;
	private ResourceDao resourceDao;
	private AttentionDateDao attentionDateDao;
	private PropertyDao propertyDao;
	private WhatDao whatDao;
	private WhyDao whyDao;
	private ThingDao thingDao;
	private ThingImageDao thingImageDao;
	private ThingBelongsToDao thingBelongsToDao;
	private ThingConsumeDao thingConsumeDao;
	private ThingExpiresOnDao thingExpiresOnDao;
	private ThingLocationDao thingLocationDao;
	private ThingMaintenanceOnDao thingMaintenanceOnDao;
	private ThingAttentionDateDao thingAttentionDateDao;
	private ThingPropertyDao thingPropertyDao;
	private ThingProvideDao thingProvideDao;
	private ThingWhatDao thingWhatDao;
	private ThingWhyDao thingWhyDao;
	
	public ThingService(Context context) {
		this.context = context;
		setupDataService();
	}
	
	private void setupDataService() {
		setupDataService(false);
	}
	
	private void setupDataService(boolean loadDefault) {
		dbHelper = new MySQLiteHelper(context);

		resourceDao = new ResourceDao(dbHelper);
		attentionDateDao = new AttentionDateDao(dbHelper);
		propertyDao = new PropertyDao(dbHelper);
		whatDao = new WhatDao(dbHelper);
		whyDao = new WhyDao(dbHelper);
		thingDao = new ThingDao(dbHelper);
		thingImageDao = new ThingImageDao(dbHelper);
		thingBelongsToDao = new ThingBelongsToDao(dbHelper);
		thingConsumeDao = new ThingConsumeDao(dbHelper);
		thingExpiresOnDao = new ThingExpiresOnDao(dbHelper);
		thingLocationDao = new ThingLocationDao(dbHelper);
		thingMaintenanceOnDao = new ThingMaintenanceOnDao(dbHelper);
		thingAttentionDateDao = new ThingAttentionDateDao(dbHelper);
		thingPropertyDao = new ThingPropertyDao(dbHelper);
		thingProvideDao = new ThingProvideDao(dbHelper);
		thingWhatDao = new ThingWhatDao(dbHelper);
		thingWhyDao = new ThingWhyDao(dbHelper);
		
		dbHelper.addDao(resourceDao);
		dbHelper.addDao(attentionDateDao);
		dbHelper.addDao(propertyDao);
		dbHelper.addDao(whatDao);
		dbHelper.addDao(whyDao);
		dbHelper.addDao(thingDao);
		dbHelper.addDao(thingImageDao);
		dbHelper.addDao(thingBelongsToDao);
		dbHelper.addDao(thingConsumeDao);
		dbHelper.addDao(thingExpiresOnDao);
		dbHelper.addDao(thingLocationDao);
		dbHelper.addDao(thingMaintenanceOnDao);
		dbHelper.addDao(thingAttentionDateDao);
		dbHelper.addDao(thingPropertyDao);
		dbHelper.addDao(thingProvideDao);
		dbHelper.addDao(thingWhatDao);
		dbHelper.addDao(thingWhyDao);
		
		dbHelper.openDaos();
		
		if(loadDefault) {
			addDefaultResources();
			addDefaultAttentionDates();
			addDefaultProperties();
			addDefaultWhats();
			addDefaultWhys();
			Toast.makeText(context, context.getResources().getString(R.string.database_defaults_loaded), Toast.LENGTH_LONG).show();
		}
	}
	
	private void addDefaultResources() {
		List<Resource> l = new ArrayList<Resource>();
		
		l.add( new Resource("Ammo, 9mm", ""));
		l.add( new Resource("Ammo, 40 S&W", ""));
		l.add( new Resource("Ammo, 45 ACP", ""));
		l.add( new Resource("Ammo, 12 Guage", ""));
		l.add( new Resource("Battery, 9-Volt", ""));
		l.add( new Resource("Battery, A123", ""));
		l.add( new Resource("Battery, AA", ""));
		l.add( new Resource("Battery, AAA", ""));
		l.add( new Resource("Fuel, Gasoline, 1G", ""));
		l.add( new Resource("Fuel, Propane, 1G", ""));
		l.add( new Resource("Food, 1 Meal", ""));
		l.add( new Resource("Food, 1 KCAL", ""));
		l.add( new Resource("Water, Bottled, 500ml", ""));
		l.add( new Resource("Water, Jug, 1G", ""));
		
		resourceDao.saveAll(l);
	}
	
	private void addDefaultAttentionDates() {
		List<AttentionDate> l = new ArrayList<AttentionDate>();
		
		l.add( new AttentionDate("Expiration Date", ""));
		l.add( new AttentionDate("Freshness Date", ""));
		l.add( new AttentionDate("Inspection Date", ""));
		l.add( new AttentionDate("Maintenance Date", ""));
		
		attentionDateDao.saveAll(l);
	}
	
	private void addDefaultProperties() {
		List<Property> l = new ArrayList<Property>();
		
		l.add( new Property("Serial Number", ""));
		l.add( new Property("Location", ""));
		l.add( new Property("Quantity", ""));
		l.add( new Property("Weight Lbs", ""));
		
		propertyDao.saveAll(l);
	}
	
	private void addDefaultWhats() {
		List<What> l = new ArrayList<What>();
		
		l.add( new What("Bag", ""));
		l.add( new What("Book", ""));
		l.add( new What("Clothing", ""));
		l.add( new What("Electronics", ""));
		l.add( new What("Firearm", ""));
		l.add( new What("Flashlight", ""));
		l.add( new What("Knife", ""));
		l.add( new What("Medical", ""));
		l.add( new What("Medicine", ""));
		l.add( new What("Multi Tool", ""));
		l.add( new What("Rope", ""));
		l.add( new What("Tool", ""));
		
		whatDao.saveAll(l);
	}
	
	private void addDefaultWhys() {
		List<Why> l = new ArrayList<Why>();
		
		l.add( new Why("Barter", ""));
		l.add( new Why("Cooking", ""));
		l.add( new Why("Defense", ""));
		l.add( new Why("Disease Prevention", ""));
		l.add( new Why("Evacuation", ""));
		l.add( new Why("Fire", ""));
		l.add( new Why("Fishing", ""));
		l.add( new Why("Heat", ""));
		l.add( new Why("Hunting", ""));
		l.add( new Why("Hygiene", ""));
		l.add( new Why("Light", ""));
		l.add( new Why("Sanitation/Waste", ""));
		l.add( new Why("Shelter", ""));
		l.add( new Why("Water Purification", ""));
		
		whyDao.saveAll(l);
	}
	
	public void clearImages(ThingModel thingModel) {
		try {
			
			List<ThingImage> images = this.thingImageDao.findAllByThingId(thingModel.getThingImage().getThingId());
			for(ThingImage image : images) {
				thingImageDao.delete( image);
			}
			
		} catch(Exception e) {
			Log.e("MyStash", "clearImages", e);
		}
	}
	
	public ThingSaveResponse save(ThingModel thingModel) {
		
		ThingSaveResponse response = new ThingSaveResponse();
		ThingModel savedThingModel = new ThingModel(); 
		
		try {
			Thing thing = thingModel.getThing();
			ThingBelongsTo belongsTo = thingModel.getThingBelongsTo();
			ThingImage image = thingModel.getThingImage();
			ThingLocation location = thingModel.getThingLocation();
			
			if(thingModel.exists()) {
				savedThingModel.setThing(thingDao.update(thing));
				savedThingModel.setThingBelongsTo(thingBelongsToDao.update(belongsTo));
			} else {
				// create thing
				savedThingModel.setThing(thingDao.create(thingModel.getThing()));
				
				// set belongs to
				belongsTo.setThingId(savedThingModel.getThingId());
				savedThingModel.setThingBelongsTo(thingBelongsToDao.create(belongsTo));
			}
			
			if(image.getId() == null) {
				// create new image
				image.setThingId(savedThingModel.getThingId());
				savedThingModel.setThingImage(thingImageDao.create(image));
			} else {
				// update existing
				savedThingModel.setThingImage(thingImageDao.update(image));
			}
			
			if(location.getId() == null) {
				// create new location
				location.setThingId(savedThingModel.getThingId());
				savedThingModel.setThingLocation(thingLocationDao.create(location));
			} else {
				// update existing
				savedThingModel.setThingLocation(thingLocationDao.update(location));
			}
		
			response = new ThingSaveResponse(true, savedThingModel);
			
		} catch(Exception e) {
			Log.e("MyStash", "save", e);
			response = new ThingSaveResponse(false, thingModel);
		}
		
		return response;
	}
	
	public ThingModel get(Long id) {
		if(id == null) {
			return null;
		}
		
		ThingModel m = new ThingModel();
		
		try {
			m = new ThingModel(
					thingDao.getThing(id)
					, thingBelongsToDao.getParent(id)
					, thingImageDao.findThingImage(id)
					, thingLocationDao.findThingLocation(id)
			);
			
		} catch(Exception e) {
			Log.e("MyStash", "ThingService:getThing", e);
		}
		
		return m;
	}
	
	public List<ThingModel> getAllThings(Long parentId) {
		List<ThingModel> list = new ArrayList<ThingModel>();
		
		List<ThingBelongsTo> children = thingBelongsToDao.findAllByParentId(parentId);
		for(ThingBelongsTo c : children) {
			list.add(get(c.getThingId()));
		}
		
		return list;
	}
	
	public ThingDeleteResponse delete(ThingModel thingModel) {
		ThingDeleteResponse response = new ThingDeleteResponse();
		
		try {
			Thing thing = thingModel.getThing();
			ThingBelongsTo belongsTo = thingModel.getThingBelongsTo();
			
			if(thingModel.exists()) {
				response.setParentId(belongsTo.getParentId());
				
				List<ThingBelongsTo> children = thingBelongsToDao.findAllByParentId(thing.getId());
				for(ThingBelongsTo c : children) {
					List<ThingBelongsTo> childBelongsTo = thingBelongsToDao.findAllByThingId(c.getThingId());
					
					for(ThingBelongsTo cb : childBelongsTo) {
						cb.setParentId(0L);
						thingBelongsToDao.save(cb);
					}
				}
				
				thingDao.delete(thing);
				thingBelongsToDao.delete(belongsTo);
			} else {
				// nothing to delete
			}
		
			response.setDeleted(true);
			
		} catch(Exception e) {
			Log.e("MyStash", "delete", e);
			response = new ThingDeleteResponse(false, null);
		}
		
		return response;
	}
	
	public void exportDatabase() {
		dbHelper.exportDatabase();
	}
	
	public void importDatabase() {
		dbHelper.importDatabase();
	}
	
	public void clearDatabase() {
		dbHelper.clearDatabase();
		setupDataService();
	}
	
	public void loadDefaultDatabase() {
		dbHelper.clearDatabase();
		setupDataService(true);
	}
	
	public List<Resource> getResourceTags() {
		List<Resource> resources = resourceDao.findAll();
		return resources;
	}
	
	public List<What> getWhatTags() {
		List<What> whats = whatDao.findAll();
		return whats;
	}
	
	public List<Why> getWhyTags() {
		List<Why> whys = whyDao.findAll();
		return whys;
	}
	
	public List<Property> getPropertyTags() {
		List<Property> properties = propertyDao.findAll();
		return properties;
	}
	
	public List<AttentionDate> getAttentionDateTags() {
		List<AttentionDate> attentionDates = attentionDateDao.findAll();
		return attentionDates;
	}
	
	public List<String> getListNames() {
		List<String> lists = new ArrayList<String>();
		lists.add(context.getResources().getString(R.string.list_resource_name));
		lists.add(context.getResources().getString(R.string.list_attention_date_name));
		lists.add(context.getResources().getString(R.string.list_property_name));
		lists.add(context.getResources().getString(R.string.list_what_name));
		lists.add(context.getResources().getString(R.string.list_why_name));
		return lists;
	}
	
	public List<String> getMetaDataListNames() {
		List<String> lists = new ArrayList<String>();
		lists.add(context.getResources().getString(R.string.list_what_name));
		lists.add(context.getResources().getString(R.string.list_why_name));
		return lists;
	}
	
	public List<String> getResourceSearchTypeNames() {
		List<String> lists = new ArrayList<String>();
		lists.add(context.getResources().getString(R.string.list_consumes_name));
		lists.add(context.getResources().getString(R.string.list_provides_name));
		return lists;
	}
	
	
	
	public ResourceDao getResourceDao() {
		return resourceDao;
	}
	
	public AttentionDateDao getAttentionDateDao() {
		return attentionDateDao;
	}

	public void setAttentionDateDao(AttentionDateDao attentionDateDao) {
		this.attentionDateDao = attentionDateDao;
	}

	public PropertyDao getPropertyDao() {
		return propertyDao;
	}

	public void setPropertyDao(PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}

	public WhatDao getWhatDao() {
		return whatDao;
	}
	
	public WhyDao getWhyDao() {
		return whyDao;
	}
	
	public ThingConsumeDao getThingConsumeDao() {
		return thingConsumeDao;
	}
	
	public ThingProvideDao getThingProvideDao() {
		return thingProvideDao;
	}
	
	public ThingAttentionDateDao getThingAttentionDateDao() {
		return thingAttentionDateDao;
	}
	
	public ThingPropertyDao getThingPropertyDao() {
		return thingPropertyDao;
	}
	
	public ThingWhatDao getThingWhatDao() {
		return thingWhatDao;
	}

	public ThingWhyDao getThingWhyDao() {
		return thingWhyDao;
	}

	
	public List<ThingAttentionDateModel> getAttentionDatesList(Long thingId) {
		List<ThingAttentionDateModel> fullList = new ArrayList<ThingAttentionDateModel>();
		
		List<ThingAttentionDate> list = thingAttentionDateDao.findAllByThingId(thingId);
		for( ThingAttentionDate tad : list) {
			ThingAttentionDateModel tadm = new ThingAttentionDateModel();
			tadm.setId(tad.getId());
			tadm.setThingId(tad.getThingId());
			tadm.setAttentionDateId(tad.getAttentionDateId());
			tadm.setValue(tad.getValue());
			
			if(tadm.getAttentionDateId() > 0) {
				AttentionDate ad = attentionDateDao.findById(tadm.getAttentionDateId());
				if( ad != null) {
					tadm.setAttentionDate(ad);
				}
			}
			
			fullList.add(tadm);
		}
		
		return fullList;
	}
	
	public ThingAttentionDateModel getThingAttentionDate(Long Id) {
		
		ThingAttentionDate tad = thingAttentionDateDao.findById( Id);
		ThingAttentionDateModel tadm = new ThingAttentionDateModel();
		tadm.setId(tad.getId());
		tadm.setThingId(tad.getThingId());
		tadm.setAttentionDateId(tad.getAttentionDateId());
		tadm.setValue(tad.getValue());
		
		if(tadm.getAttentionDateId() > 0) {
			AttentionDate ad = attentionDateDao.findById(tadm.getAttentionDateId());
			if( ad != null) {
				tadm.setAttentionDate(ad);
			}
		}
			
		return tadm;
	}
	
	public List<ThingPropertyModel> getPropertiesList(Long thingId) {
		List<ThingPropertyModel> fullList = new ArrayList<ThingPropertyModel>();
		
		List<ThingProperty> list = thingPropertyDao.findAllByThingId(thingId);
		for( ThingProperty tp : list) {
			ThingPropertyModel tpm = new ThingPropertyModel();
			tpm.setId(tp.getId());
			tpm.setThingId(tp.getThingId());
			tpm.setPropertyId(tp.getPropertyId());
			tpm.setValue(tp.getValue());
			
			if(tpm.getPropertyId() > 0) {
				Property p = propertyDao.findById(tpm.getPropertyId());
				if( p != null) {
					tpm.setProperty(p);
				}
			}
			
			fullList.add(tpm);
		}
		
		return fullList;
	}
	
	public ThingPropertyModel getThingProperty(Long Id) {
		
		ThingProperty tp = thingPropertyDao.findById( Id);
		ThingPropertyModel tpm = new ThingPropertyModel();
		tpm.setId(tp.getId());
		tpm.setThingId(tp.getThingId());
		tpm.setPropertyId(tp.getPropertyId());
		tpm.setValue(tp.getValue());
		
		if(tpm.getPropertyId() > 0) {
			Property p = propertyDao.findById(tpm.getPropertyId());
			if( p != null) {
				tpm.setProperty(p);
			}
		}
			
		return tpm;
	}
	
	public List<ThingConsumeModel> getConsumesList(Long thingId) {
		List<ThingConsumeModel> fullList = new ArrayList<ThingConsumeModel>();
		
		List<ThingConsume> list = thingConsumeDao.findAllByThingId(thingId);
		for( ThingConsume tc : list) {
			ThingConsumeModel tcm = new ThingConsumeModel();
			tcm.setId(tc.getId());
			tcm.setThingId(tc.getThingId());
			tcm.setResourceId(tc.getResourceId());
			tcm.setResourceDetails(tc.getResourceDetails());
			tcm.setResourceQuantity(tc.getResourceQuantity());
			
			if(tcm.getResourceId() > 0) {
				Resource r = resourceDao.findById(tcm.getResourceId());
				if( r != null) {
					tcm.setResource(r);
				}
			}
			
			fullList.add(tcm);
		}
		
		return fullList;
	}
	
	public ThingConsumeModel getThingConsumes(Long Id) {
		
		ThingConsume tc = thingConsumeDao.findById( Id);
		ThingConsumeModel tcm = new ThingConsumeModel();
		tcm.setId(tc.getId());
		tcm.setThingId(tc.getThingId());
		tcm.setResourceId(tc.getResourceId());
		tcm.setResourceDetails(tc.getResourceDetails());
		tcm.setResourceQuantity(tc.getResourceQuantity());
		
		if(tcm.getResourceId() > 0) {
			Resource r = resourceDao.findById(tcm.getResourceId());
			if( r != null) {
				tcm.setResource(r);
			}
		}
			
		return tcm;
	}
	
	
	
	public List<ThingProvideModel> getProvidesList(Long thingId) {
		List<ThingProvideModel> fullList = new ArrayList<ThingProvideModel>();
		
		List<ThingProvide> list = thingProvideDao.findAllByThingId(thingId);
		for( ThingProvide t : list) {
			ThingProvideModel tm = new ThingProvideModel();
			tm.setId(t.getId());
			tm.setThingId(t.getThingId());
			tm.setResourceId(t.getResourceId());
			tm.setResourceDetails(t.getResourceDetails());
			tm.setResourceQuantity(t.getResourceQuantity());
			
			if(tm.getResourceId() > 0) {
				Resource r = resourceDao.findById(tm.getResourceId());
				if( r != null) {
					tm.setResource(r);
				}
			}
			
			fullList.add(tm);
		}
		
		return fullList;
	}
	
	public ThingProvideModel getThingProvides(Long Id) {
		
		ThingProvide m = thingProvideDao.findById( Id);
		ThingProvideModel tm = new ThingProvideModel();
		tm.setId(m.getId());
		tm.setThingId(m.getThingId());
		tm.setResourceId(m.getResourceId());
		tm.setResourceDetails(m.getResourceDetails());
		tm.setResourceQuantity(m.getResourceQuantity());
		
		if(tm.getResourceId() > 0) {
			Resource r = resourceDao.findById(tm.getResourceId());
			if( r != null) {
				tm.setResource(r);
			}
		}
			
		return tm;
	}
	
	public List<ThingWhatModel> getWhatsList(Long thingId) {
		List<ThingWhatModel> fullList = new ArrayList<ThingWhatModel>();
		
		List<ThingWhat> list = thingWhatDao.findAllByThingId(thingId);
		for( ThingWhat t : list) {
			ThingWhatModel m = new ThingWhatModel();
			m.setId(t.getId());
			m.setThingId(t.getThingId());
			m.setWhatId(t.getWhatId());
			m.setWhatDetails(t.getWhatDetails());
			
			if(m.getWhatId() > 0) {
				What w = whatDao.findById(m.getWhatId());
				if( w != null) {
					m.setWhat(w);
				}
			}
			
			fullList.add(m);
		}
		
		return fullList;
	}
	
	public ThingWhatModel getThingWhats(Long Id) {
		
		ThingWhat t = thingWhatDao.findById( Id);
		ThingWhatModel m = new ThingWhatModel();
		m.setId(t.getId());
		m.setThingId(t.getThingId());
		m.setWhatId(t.getWhatId());
		m.setWhatDetails(t.getWhatDetails());
		
		if(m.getWhatId() > 0) {
			What w = whatDao.findById(m.getWhatId());
			if( w != null) {
				m.setWhat(w);
			}
		}
			
		return m;
	}
	
	public List<ThingWhyModel> getWhysList(Long thingId) {
		List<ThingWhyModel> fullList = new ArrayList<ThingWhyModel>();
		
		List<ThingWhy> list = thingWhyDao.findAllByThingId(thingId);
		for( ThingWhy t : list) {
			ThingWhyModel m = new ThingWhyModel();
			m.setId(t.getId());
			m.setThingId(t.getThingId());
			m.setWhyId(t.getWhyId());
			m.setWhyDetails(t.getWhyDetails());
			
			if(m.getWhyId() > 0) {
				Why w = whyDao.findById(m.getWhyId());
				if( w != null) {
					m.setWhy(w);
				}
			}
			
			fullList.add(m);
		}
		
		return fullList;
	}
	
	public ThingWhyModel getThingWhys(Long Id) {
		
		ThingWhy t = thingWhyDao.findById( Id);
		ThingWhyModel m = new ThingWhyModel();
		m.setId(t.getId());
		m.setThingId(t.getThingId());
		m.setWhyId(t.getWhyId());
		m.setWhyDetails(t.getWhyDetails());
		
		if(m.getWhyId() > 0) {
			Why w = whyDao.findById(m.getWhyId());
			if( w != null) {
				m.setWhy(w);
			}
		}
			
		return m;
	}
	
	public List<ExpiringItem> getExpiringItems( String containerName, String inPhrase) {
		return this.getExpiringItems(containerName, inPhrase, NOTIFY_DAYS_BEFORE_EXPIRATION);
	}
	
	public List<ExpiringItem> getExpiringItems( String containerName, String inPhrase, Integer daysBeforeNow) {
		List<ExpiringItem> expiredItems = thingDao.getExpiredItems(  containerName, inPhrase, daysBeforeNow);
		
		return expiredItems;
	}
	
	public List<Thing> getPossibleParents(ThingModel child) {
		List<Thing> possibleParents = new ArrayList<Thing>();
		
		// Add Root
		Thing root = new Thing();
		root.setId(0L);
		root.setTag(context.getResources().getString(R.string.app_name) +"");
		
		possibleParents.add( root);
		
		// Add Parent's Peers
		List<ThingBelongsTo> parents = thingBelongsToDao.findAllByThingId( child.getThingBelongsTo().getParentId());
		if(parents.size() > 0) {
			ThingBelongsTo parent = parents.get(0);
			if( parent.getParentId() != null) {
				List<ThingBelongsTo> parentPeers = this.thingBelongsToDao.findAllByParentId( parent.getParentId());
				for(ThingBelongsTo peer : parentPeers) {
					Thing tp = thingDao.getThing( peer.getId());
					possibleParents.add( tp);
				}
			}
		}
		
		// Add Peers for placing into
		List<ThingBelongsTo> peers = this.thingBelongsToDao.findAllByParentId(child.getThingBelongsTo().getParentId());
		for(ThingBelongsTo peer : peers) {
			if( peer.getId() != child.getThingId()) { // can't parent yourself
				Thing tp = thingDao.getThing( peer.getId());
				possibleParents.add( tp);
			}
		}
		
		return possibleParents;
	}
	
	@SuppressLint("UseSparseArrays")
	public ThingTotalCost getThingTotalCost(Long thingId) {
		
		Map<Long, Long> ids = new HashMap<Long, Long>();
		ids.put(thingId, thingId);
		getChildren(thingId, ids);
		
		StringBuilder csvThingIds = new StringBuilder();
		Set<Long> thingIds = ids.keySet();
		Boolean addedOne = false;
		for (Long id : thingIds) {
			
			if(addedOne) {
				csvThingIds.append(",");
			}
			
			csvThingIds.append(id);
		    addedOne = true;
		}
		
		ThingTotalCost ttc = this.thingDao.getTotalCosts( csvThingIds.toString());
		
		return ttc;
	}
	
	private void getChildren( Long thingId, Map<Long, Long> ids) {
		
		List<ThingBelongsTo> children = this.thingBelongsToDao.findAllByParentId( thingId);
		for(ThingBelongsTo child : children) {
			ids.put( child.getId(), child.getId());
			getChildren( child.getId(), ids);
		}
	}

	public List<FoundItem> findItems(String searchText) {

		List<FoundItem> list = thingDao.findByName( searchText, context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.in_phrase));
		
		return list;
	}
	
	public List<FoundItem> findItemsByWhat(What what) {

		List<FoundItem> list = thingDao.findByWhat( what, context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.in_phrase));
		
		return list;
	}
	
	public List<FoundItem> findItemsByWhy(Why why) {

		List<FoundItem> list = thingDao.findByWhy( why, context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.in_phrase));
		
		return list;
	}

	public List<FoundItem> findItemsByConsumes(Resource consume) {
		
		List<FoundItem> list = thingDao.findByConsumes( consume, context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.in_phrase));
		
		return list;
	}
	
	public List<FoundItem> findItemsByProvides(Resource provide) {
		
		List<FoundItem> list = thingDao.findByProvides( provide, context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.in_phrase));
		
		return list;
	}
}
