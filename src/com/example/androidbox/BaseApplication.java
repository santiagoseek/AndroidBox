package com.example.androidbox;

import android.app.Application;

public class BaseApplication extends Application {
	private static final String value = "BaseApplicationValue";
	
	private String testValue;
	
	public void onCreate()
	{
		super.onCreate();
		setValue(value);
	}
	
	public void setValue(String value)
	{
		this.testValue = value;
	}
	
	public String getValue()
	{
		return testValue;
	}

}
