package com.example.androidbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class MultiProcessService extends Service {

	String tag = "MultiProcess";
	//private BaseApplication baseApp1;
	
    @Override
    public void onCreate() {
        Log.w(tag,"MultiProcessService is oncreate");
        SharedPreferences sPreferences = getSharedPreferences("boxtestSetting", Build.VERSION.SDK_INT > 9 ? 4 : Context.MODE_PRIVATE);
        String value = sPreferences.getString("testkey1", "-1");
        Log.w(tag, "In MultiProcessService onCreate, read value=: " + value);
        
		SharedPreferences.Editor editor = sPreferences.edit(); 
		editor.putString("testkey1", String.valueOf(Long.parseLong(value) + 11L));  
		editor.commit(); 
		Log.w(tag, "In MultiProcessService onCreate, read value + 11 =: " + sPreferences.getString("testkey1", "-1"));
    }
    
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	Log.w(tag, "In MultiProcessService onStartCommand");
    	Log.w(tag, ((BaseApplication)getBaseContext().getApplicationContext()).getValue());
    	((BaseApplication)getBaseContext().getApplicationContext()).setValue("update in MultiProcess");
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
    	Log.w(tag,"MultiProcess OnDestory");
    }
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
