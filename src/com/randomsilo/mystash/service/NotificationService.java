package com.randomsilo.mystash.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import com.randomsilo.mystash.LandingActivity;
import com.randomsilo.mystash.R;
import com.randomsilo.mystash.SplashScreenActivity;
import com.randomsilo.mystash.db.pojo.ExpiringItem;
import com.randomsilo.mystash.session.MyStashSession;

public class NotificationService {
	private Context context;
	public static final int MAX_NOTIFICATIONS = 10;
	public static final String FOLDER = "NotificationSettings";
	public static final String FILE = "Notification.properties";
	private int daysBeforeExpire = 30;
	
	public NotificationService(Context context) {
		this.context = context;
		this.load();
	}
	
	public Context getApplicationContext() {
		return this.context;
	}
	
	public int getDaysBeforeExpire() {
		return daysBeforeExpire;
	}

	public void setDaysBeforeExpire(int daysBeforeExpire) {
		this.daysBeforeExpire = daysBeforeExpire;
	}

	public Boolean save() {
		Boolean success = false;
		try {
			File filePath = new File(context.getFilesDir(), FOLDER);
			File settingsFile = new File(filePath, FILE);
			settingsFile.getParentFile().mkdirs();
			if (settingsFile.exists()) {
				settingsFile.delete();
			}
			
			settingsFile.createNewFile();
			
			PrintWriter out;
			out = new PrintWriter(settingsFile);
			out.println(getDaysBeforeExpire());
			out.flush();
			out.close();
			
			success = settingsFile.exists();
			
		} catch (Exception e) {
			Log.e("NotificationService", "save exception", e);
		}
		
		return success;
	}
	
	public int load() {
		daysBeforeExpire = 30;
		
		try {
			File filePath = new File(context.getFilesDir(), FOLDER);
			File settingsFile = new File(filePath, FILE);
			settingsFile.getParentFile().mkdirs();
			if (settingsFile.exists()) {
				BufferedReader reader = new BufferedReader( new FileReader (settingsFile));
			    String line = null;
			    StringBuilder stringBuilder = new StringBuilder();
	
			    while(( line = reader.readLine()) != null ) {
			        stringBuilder.append( line);
			    }
			    reader.close();
			    String data = stringBuilder.toString();
			    daysBeforeExpire = Integer.parseInt(data);
			}
		} catch( Exception e) {
			Log.e("NotificationService", "load exception", e);
		}
		
		return daysBeforeExpire;
	}
	
	
	public List<ExpiringItem> getExpiringItems() {
		String containerName = context.getResources().getString(R.string.app_name);
		String inPhrase = context.getResources().getString(R.string.in_phrase);
		
		List<ExpiringItem> list = MyStashSession.getInstance().getThingService().getExpiringItems( containerName, inPhrase, getDaysBeforeExpire());
		
		return list;
	}

	
	public void SendExpiringNotifications() {
		NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		
		List<ExpiringItem> list = getExpiringItems();
		
		Context context = getApplicationContext();
		Intent notificationIntent = null;
		PendingIntent contentIntent = null;
		
		int count = 0;
		for( ExpiringItem item : list) {
			count++;
			
			if( count > MAX_NOTIFICATIONS) {
				String msg = context.getResources().getString(R.string.notification_too_many_expiring_items, item.getDateType(), item.getDateDifference());
				String subMsg = context.getResources().getString(R.string.expiring_items_count, list.size());
				
				notificationIntent = new Intent(context, SplashScreenActivity.class);
				notificationIntent.setData((Uri.parse("mystash://"+SystemClock.elapsedRealtime()))); // make unique
				notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				Notification tooManyExpiringItems = new Notification.Builder(getApplicationContext())
		        .setContentTitle(msg)
		        .setContentText(subMsg)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setWhen(System.currentTimeMillis())
		        .setContentIntent(contentIntent)
		        .setAutoCancel(true)
		        .build();
				
				Random rand = new Random();
			    int randomNum = rand.nextInt((100000 - list.size()) + 1) + list.size();
				nm.notify(randomNum, tooManyExpiringItems);
				break;
			}
			
			
			String contentText = context.getResources().getString(R.string.notification_date_reached, item.getDateType(), item.getDateDifference());
			
			notificationIntent = new Intent(context, LandingActivity.class);
			notificationIntent.putExtra("thingId", item.getThingId().toString());
			notificationIntent.setData((Uri.parse("mystash://"+SystemClock.elapsedRealtime()))); // make unique
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			Notification note = new Notification.Builder(getApplicationContext())
	        .setContentTitle(item.getUserMessage())
	        .setContentText(contentText)
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setWhen(System.currentTimeMillis())
	        .setAutoCancel(true)
	        .setContentIntent(contentIntent)
	        .build();
	
			nm.notify(item.getThingId().intValue(), note);
			
			
		}
	}
}
