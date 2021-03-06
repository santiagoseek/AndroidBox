package com.example.systemutil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetworkUtil {
	private NetworkUtil() {
	}

	/**
	 * 判断网络是否连接 
	 * @Title: isNetworkConnected
	 * @Description: TODO
	 * @param context
	 * @return
	 * @return: boolean
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}
	
	public static boolean isDataState(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 
		
		if(tm.getDataState() ==TelephonyManager.DATA_CONNECTED){
			return true;
		}else{
			return false;
		}
	}
	
	public static String getDeviceId(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	

	public static String getNetworkOperatorName(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getNetworkOperatorName();
	
	}
	
	/*
	 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />  
	 */
	public static String getLocalMacAddress(Context context){
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
		
	}

	public String getLocalIPAddress(Context context){
		try{
			for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();en.hasMoreElements();){
				NetworkInterface intf = en.nextElement();
				
				for(Enumeration<InetAddress> enIP = intf.getInetAddresses();enIP.hasMoreElements();){
					InetAddress inetAddress = enIP.nextElement();
					
					if(!inetAddress.isLoopbackAddress()){
						return inetAddress.getHostAddress().toString();
					}
					
				}
				
			}
		}catch(SocketException e){
			Log.e("wifiperference ip address", e.toString());
		}
		
		return null;
	}
	
	public static int getNetworkType(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		int netWorkType = Constant.NET_TYPE_NONE;
		if (networkInfo != null && networkInfo.isAvailable()) {
			int nType = networkInfo.getType();
			if (nType == ConnectivityManager.TYPE_MOBILE) {
				int subType = networkInfo.getSubtype();
				if (subType == TelephonyManager.NETWORK_TYPE_CDMA
						|| subType == TelephonyManager.NETWORK_TYPE_GPRS
						|| subType == TelephonyManager.NETWORK_TYPE_EDGE) {
					// 2G
					netWorkType = Constant.NET_TYPE_2G;
				} else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
						|| subType == TelephonyManager.NETWORK_TYPE_HSDPA
						|| subType == TelephonyManager.NETWORK_TYPE_HSPA
						|| subType == TelephonyManager.NETWORK_TYPE_HSUPA
						|| subType == TelephonyManager.NETWORK_TYPE_EVDO_0
						|| subType == TelephonyManager.NETWORK_TYPE_EVDO_A
						|| subType == TelephonyManager.NETWORK_TYPE_UMTS) {
					// 3G
					netWorkType = Constant.NET_TYPE_3G;
				} else {
					// 其它
					netWorkType = Constant.NET_TYPE_OTHER;
				}
			} else if (nType == ConnectivityManager.TYPE_WIFI) {
				// WIFI
				netWorkType = Constant.NET_TYPE_WIFI;
			}
		} else {
			// 没有网络
			netWorkType = 0;
		}

		return netWorkType;
	}
	
	
	public static String getNetworkName(Context context){
		String name = "NA";
		int flag = getNetworkType(context);
		
		if(flag == Constant.NET_TYPE_2G){
			name = "2G";
		}else if(flag == Constant.NET_TYPE_3G){
			name = "3G";
		}else if(flag == Constant.NET_TYPE_WIFI){
			name = "wifi";
		}
		
		return name;
	}
}