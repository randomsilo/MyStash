package com.randomsilo.mystash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.randomsilo.mystash.session.MyStashSession;

public class SplashScreenActivity extends Activity {
	private static int SPLASH_TIME_OUT = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		if(MyStashSession.getInstance() == null || MyStashSession.getInstance().getThingService() == null) {
    		MyStashSession.getInstance().Init(this.getApplication());
    	}
		
		new Handler().postDelayed(new Runnable() {
        	
            @Override
            public void run() {
        		Intent i = new Intent(SplashScreenActivity.this, LandingActivity.class);
                startActivity(i);
                finish();	
            }
        }, SPLASH_TIME_OUT);
	}

}
