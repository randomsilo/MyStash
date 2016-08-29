package com.randomsilo.mystash.db.pojo;

public class ThingLocation extends BasePojoThingRelated {
	private String address;
	private String cityState;
	private String latitude;
	private String longitude;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCityState() {
		return cityState;
	}
	public void setCityState(String cityState) {
		this.cityState = cityState;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
}
