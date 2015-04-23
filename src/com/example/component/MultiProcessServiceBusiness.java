package com.example.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.androidbox.BaseApplication;
import com.example.systemutil.DBStorageService;
import com.example.systemutil.Message;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MultiProcessServiceBusiness extends Service {

	String tag = "MultiProcessServiceBusiness";
	Map<String, Object> mapObj;
	Map<String, String> mapStr;
	
	private boolean isRun;
	private int count;

	@Override
	public void onCreate() {
		Log.w(tag, "MultiProcessServiceBusiness is oncreate");

		isRun = true;
		count = 0;
		
		mapObj = new HashMap<String, Object>();
		mapObj.put("a key", "a value MultiProcessServiceBusiness");
		mapObj.put("b key", "b value MultiProcessServiceBusiness");
		mapStr = new HashMap<String, String>();
		mapStr.put("ak", "av MultiProcessServiceBusiness");
		mapStr.put("bk", "bv MultiProcessServiceBusiness");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.w(tag, "In MultiProcessServiceBusiness onStartCommand");
		Log.w(tag, ((BaseApplication) getBaseContext()
				.getApplicationContext()).getValue());

		Log.w(tag, "flags:" + Integer.toString(flags));
		Log.w(tag, "startId:" + Integer.toString(startId));
			
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isRun){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
					DBStorageService.getInstance().save(makeMessageSaveToDB(count));
				}
			}}).start();
		
		//stopSelf(startId);
		return START_STICKY;
	}

	public List<Message> makeMessageSaveToDB(int i) {
		List<Message> queueMsgs = new ArrayList<Message>(100);
		Message msg = new Message("MultiProcessServiceBusiness" + i, "1");
		msg.setPriority((short) i);
		msg.setOfferTime(i);
		msg.setUbtData("" + i);
		msg.setExpireTime(i);
		queueMsgs.add(msg);
		return queueMsgs;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		isRun = false;
		
		Log.w(tag, "MultiProcessServiceBusiness OnDestory isRun:" + isRun + " Count:" + count);
	}

}
