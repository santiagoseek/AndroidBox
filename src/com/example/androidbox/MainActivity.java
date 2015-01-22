package com.example.androidbox;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private String tag = "MainActivity";
	private BaseApplication baseApp;

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
				baseApp.setValue("update the value.");
				showDialog("update the value. " + baseApp.getValue());
			}
		});
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
