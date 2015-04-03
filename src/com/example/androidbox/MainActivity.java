package com.example.androidbox;

import com.example.ui.CommonControl;
import com.example.ui.SomeDialog;
import com.example.ui.UITabActivity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private String tag = "MainActivity";
	private BaseApplication baseApp;
	
	private static final String STATICACTION = "com.example.component.static";
	private static final String DYNAMICACTION = "com.example.component.dynamic";
	private static final String SYSTEMACTION = Intent.ACTION_POWER_CONNECTED ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment())
		// .commit();
		// }

		Button getSystem = (Button) findViewById(R.id.getSystem);
		getSystem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SystemUtilActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		Button accessInternet = (Button) findViewById(R.id.accessInternet);
		accessInternet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, InternetUtilActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		Button multiProcess = (Button) this.findViewById(R.id.multiProcess);
		multiProcess.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.w(tag, "multiprocess on create() in MainActivity.");
				MainActivity.this.startService(new Intent(MainActivity.this,
						MultiProcessService.class));
			}
		});

		Button sharedPreferences = (Button) this.findViewById(R.id.sharedPreferences);
		sharedPreferences.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.w(tag, "SharedPreferences on create() in MainActivity.");

				SharedPreferences sPreferences = getSharedPreferences(
						"boxtestSetting", Build.VERSION.SDK_INT > 9 ? 4
								: Context.MODE_PRIVATE);
				String value = sPreferences.getString("testkey1", "-1");
				Log.w(tag, "In MainActivity, read value=: " + value);
				SharedPreferences.Editor editor = sPreferences.edit();
				editor.putString("testkey1",
						String.valueOf(Long.parseLong(value) + 11L));
				editor.commit();
				Log.w(tag, "In MainActivity, read value + 11 =: "
						+ sPreferences.getString("testkey1", "-1"));
			}
		});
		
		Button testButton = (Button) this.findViewById(R.id.testButton);
		testButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				baseApp = (BaseApplication) getApplication();
				showDialog("this is testing showDialog, " + baseApp.getValue());
				baseApp.setValue("update the value in mainactivity.");
				showDialog("update the value. " + baseApp.getValue());
			}
		});
		
		Button uiDialog = (Button)findViewById(R.id.uiDialog);
		uiDialog.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SomeDialog.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		Button tabUI = (Button)findViewById(R.id.tabUI);
		tabUI.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, UITabActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		Button gridUI = (Button)findViewById(R.id.gridui);
		gridUI.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GridUIActivity.class);
				MainActivity.this.startActivity(intent);
			}			
		});
		
		Button handler = (Button)findViewById(R.id.handler);
		handler.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, HandlerActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		Button controlTest = (Button)findViewById(R.id.controlTest);
		controlTest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CommonControl.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		Button sendStaticBtn = (Button)findViewById(R.id.send_static);
		Button sendDynamicBtn = (Button)findViewById(R.id.send_dynamic);
		Button sendSystemBtn = (Button)findViewById(R.id.send_system);
		sendStaticBtn.setOnClickListener(new BrocastOnClickListener());
		sendDynamicBtn.setOnClickListener(new BrocastOnClickListener());
		sendSystemBtn.setOnClickListener(new BrocastOnClickListener());
		
		Button notification = (Button)findViewById(R.id.notification);
		notification.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showNotification();
			}
		});
	}
	
	class BrocastOnClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0.getId() == R.id.send_static){
				Log.w(tag, "发送自定义静态注册广播消息");
				Intent intent = new Intent();
				intent.setAction(STATICACTION);
				intent.putExtra("msg", "接收静态注册广播成功");
				sendBroadcast(intent);
			}
			else if(arg0.getId() == R.id.send_dynamic){
				Log.w(tag, "发送自定义动态注册广播消息");
				Intent intent = new Intent();
				intent.setAction(DYNAMICACTION);
				intent.putExtra("msg", "接收动态注册广播成功");
				sendBroadcast(intent);
			}
			else if(arg0.getId() == R.id.send_system){
				Log.w(tag, "发送系统动态注册广播消息");
				Intent intent = new Intent();
				intent.setAction(SYSTEMACTION);
				intent.putExtra("msg", "正在充电。。。");
			}
		}
	}
	
	protected void onStart(){
		super.onStart();
		Log.w(tag, "注册广播消息");
		// 注册自定义动态广播消息  
		IntentFilter filter_dynamic = new IntentFilter();
		filter_dynamic.addAction(DYNAMICACTION);
		registerReceiver(dynamicReceiver,filter_dynamic);
		
		// 注册系统动态广播消息  
		// 只能在代码中注册，程序适应系统变化做操作，程序运行状态才能接收到
		IntentFilter filter_system = new IntentFilter();
		filter_system.addAction(SYSTEMACTION);
		filter_system.addAction(Intent.ACTION_SCREEN_ON); //屏幕亮
		filter_system.addAction(Intent.ACTION_SCREEN_OFF); //屏幕灭
		filter_system.addAction(Intent.ACTION_TIME_TICK); //时间变化  每分钟一次
		registerReceiver(systemReceiver,filter_system);
	}

	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.w(tag, "接收自定义动态注册广播消息");
			if(intent.getAction().equals(DYNAMICACTION)){
				String msg = intent.getStringExtra("msg");
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		}
	};
	
	private BroadcastReceiver systemReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.w(tag, "接收系统动态注册广播消息");
			if(intent.getAction().equals(SYSTEMACTION)){
				String msg = intent.getStringExtra("msg");
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
			else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
				Toast.makeText(context, "Intent.ACTION_SCREEN_ON", Toast.LENGTH_LONG).show();
			}
			else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
				Toast.makeText(context, "Intent.ACTION_SCREEN_OFF", Toast.LENGTH_LONG).show();
			}
			else if(intent.getAction().equals(Intent.ACTION_TIME_TICK)){
				Toast.makeText(context, "Intent.ACTION_TIME_TICK", Toast.LENGTH_LONG).show();
			}
		}
		
	};
	
	public void showNotification(){
		NotificationManager mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,"this is testing notification",System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults = notification.DEFAULT_SOUND|notification.DEFAULT_VIBRATE|notification.FLAG_SHOW_LIGHTS;
		
		Intent openIntent = new Intent(this, GridUIActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this.getApplicationContext() , 0, openIntent, 0);
		notification.setLatestEventInfo(MainActivity.this.getApplicationContext(), "this is title.", "this is content text", contentIntent);
		mNotificationManager.notify(0,notification);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	private void showDialog(String str) {
		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("Title").setMessage(str).setIcon(R.drawable.ic_launcher)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		})
		.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}).create();
		alertDialog.show();
	}

	/*
	 * 
	 * long currentTime = System.currentTimeMillis(); SimpleDateFormat formatter
	 * = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒"); Date date = new
	 * Date(currentTime); System.out.println(formatter.format(date));
	 */
}
