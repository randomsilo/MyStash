package com.randomsilo.mystash.util;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class FastGps {

    private Context context;
    // flag for GPS Status
    private boolean isGPSEnabled = false;
    // flag for network status
    private boolean isNetworkEnabled = false;
    private LocationManager locationManager;
    private Location location;
    private double latitude;
    private double longitude;

    public FastGps(Context context) {
        this.context = context;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }   
    
    public void getMyLocation() {
        List<String> providers = locationManager.getProviders(true);

        Location l = null;
        for (int i = 0; i < providers.size(); i++) {
            l = locationManager.getLastKnownLocation(providers.get(i));
            if (l != null)
                break;
        }
        if (l != null) {
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }
    }

    public boolean isGPSenabled() {
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return (isGPSEnabled || isNetworkEnabled);
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        return longitude;
    }
    
	public Context getContext() {
		return context;
	}
	
	public Location getLocation() {
		return location;
	}
    
    
}