package com.example.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StaticReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		String msg = arg1.getStringExtra("msg");
		Toast.makeText(arg0, msg, Toast.LENGTH_LONG).show();
	}

}
