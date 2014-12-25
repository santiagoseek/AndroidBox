package com.example.internetutil;

import android.content.Context;
import android.widget.Toast;

public class PersonalListener implements OnActionListener {
	
	private Context context;
	
	public  PersonalListener(Context context) {
		this.context = context;
	}

	@Override
	public void onActionSuccess(int actionId, ResponseParam ret) {
		// TODO Auto-generated method stub
		
		Toast.makeText(context, ret.getString("cityid"), Toast.LENGTH_LONG).show();

	}

	@Override
	public void onActionFailed(int actionId, int httpStatus) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActionException(int actionId, String exception) {
		// TODO Auto-generated method stub

	}

}
