package com.randomsilo.mystash.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.randomsilo.mystash.service.BootService;

public class AutoStartReceiver extends BroadcastReceiver {
	
    public void onReceive(Context arg0, Intent arg1) {
        Intent intent = new Intent(arg0, BootService.class);
        arg0.startService(intent);
        Log.i("AutoStartReceiver", "started");
    }
}
