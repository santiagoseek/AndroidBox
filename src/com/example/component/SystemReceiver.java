package com.example.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SystemReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		if(arg1.getAction().equals(arg1.ACTION_BATTERY_LOW)){
			Log.e("SystemReceiver", "电量低提示");
			Toast.makeText(arg0, "手机电量底，请充电", Toast.LENGTH_SHORT).show();
		}
	}

}
