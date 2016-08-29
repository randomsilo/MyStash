package com.randomsilo.mystash.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.randomsilo.mystash.session.MyStashSession;

public class BootService extends Service {
    private static final String TAG = "BootService";
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startid)
    {
    	Log.d(TAG, "onStart");
    	
    	if(MyStashSession.getInstance() == null || MyStashSession.getInstance().getThingService() == null) {
    		Log.d(TAG, "Init App");
    		MyStashSession.getInstance().Init(this.getApplication());
    	}
    	
    	Log.d(TAG, "Send Notifications");
    	MyStashSession.getInstance().getNotificationService().SendExpiringNotifications();
        
    	Log.d(TAG, "Notifications Complete");
    }
}
