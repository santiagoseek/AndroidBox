package com.example.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.androidbox.BaseApplication;
import com.example.systemutil.DBStorageService;
import com.example.systemutil.Message;
import com.example.ui.CommonControl;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MultiProcessServicePush extends Service {
	String tag = "MultiProcessServicePush";
	Map<String, Object> mapObj;
	Map<String, String> mapStr;
	
	private MessageThread messageThread = null;
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;
	private Notification messageNotification = null;
	private NotificationManager messageNotificationManager = null;
	private int messageNotificationID = 1000;

	@Override
	public void onCreate() {
		Log.w(tag, "MultiProcessServicePush is oncreate");

		mapObj = new HashMap<String, Object>();
		mapObj.put("a key", "a value MultiProcessServicePush");
		mapObj.put("b key", "b value MultiProcessServicePush");
		mapStr = new HashMap<String, String>();
		mapStr.put("ak", "av MultiProcessServicePush");
		mapStr.put("bk", "bv MultiProcessServicePush");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.w(tag, "In MultiProcessServicePush onStartCommand");
		Log.w(tag, ((BaseApplication) getBaseContext().getApplicationContext()).getValue());

		Log.w(tag, "flags:" + Integer.toString(flags));
		Log.w(tag, "startId:" + Integer.toString(startId));

/*		for (int i = 0; i < 100; i++) {
	    	DBStorageService.getInstance().save(makeMessageSaveToDB(i));
		}
		stopSelf(startId);*/
		
		messageNotification = new Notification();
		messageNotification.icon = R.drawable.sym_def_app_icon;
		messageNotification.tickerText = "New Message";
		messageNotification.defaults = Notification.DEFAULT_SOUND;
		messageNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		messageIntent = new Intent(this, CommonControl.class);
		messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);
		
		messageThread = new MessageThread();
		messageThread.isRunning = true;
		messageThread.start();
		super.onStartCommand(intent, flags, startId);
		
		return START_STICKY;
	}

	public List<Message> makeMessageSaveToDB(int i) {
		List<Message> queueMsgs = new ArrayList<Message>(100);
		Message msg = new Message("MultiProcessServicePush" + i, "1");
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
		messageThread.isRunning = false;
		Log.w(tag, "MultiProcessServicePush OnDestory");
		super.onDestroy();
	}
	

	/**
	 * 
	 * @return 返回服务器要推送的消息，否则如果为空的话，不推送  
	 */
	public String getServerMessage() {
		// TODO Auto-generated method stub
		return "Send Succeed";
	}
	
	
	class MessageThread extends Thread{
		public boolean isRunning = true;
		public void run(){
			while(isRunning){
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String serverMessage = getServerMessage();
				if(serverMessage != null && !"".equals(serverMessage)){
					messageNotification.setLatestEventInfo(MultiProcessServicePush.this, "New Message", "This is Testing Message!" + serverMessage , messagePendingIntent);
					messageNotificationManager.notify(messageNotificationID,messageNotification);
					messageNotificationID++;
				}
			}
		}
	}
}

