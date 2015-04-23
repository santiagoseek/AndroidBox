package com.example.androidbox;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.systemutil.Constant;
import com.example.systemutil.DBStorageService;
import com.example.systemutil.FileUtil;
import com.example.systemutil.Message;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class MultiProcessService extends Service {

	String tag = Constant.TAG + "-MultiProcess";
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
    	
    	Log.e(tag, "flags:" + Integer.toString(flags));
    	Log.e(tag, "startId:" + Integer.toString(startId));
    	((BaseApplication)getBaseContext().getApplicationContext()).setValue("update in MultiProcess");
    	
    	String fileName = Environment.getExternalStorageDirectory() + File.separator + "aaaMessageFile";
    	List<Message> queueMsgs = new ArrayList<Message>(100);
    	for(int i = 0;i<100;i++){
    		Message msg = new Message("MultiProcessService" + i,"1");
    		msg.setPriority((short) i);
    		msg.setOfferTime(i);
    		msg.setUbtData(""+i);
    		msg.setExpireTime(i);
    		queueMsgs.add(msg);
    		FileUtil.writeObjectToLocation(fileName, msg, true);
    	}
    	
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
    	Log.i(tag, "In MultiProcessService onStartCommand, before save queueMsgs to DB, the queueMsg's size is: " + queueMsgs.size()+ "time:" + format.format(new Date(System.currentTimeMillis())));
    	//DBStorageService.getInstance().save(queueMsgs);
    	stopSelf(startId);
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
