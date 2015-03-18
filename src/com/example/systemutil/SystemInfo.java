package com.example.systemutil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class SystemInfo {
	/**
	 * ʱ��Keyֵ
	 */
	public final static String TIME_ZONE = "timezone";
	/**
	 * ϵͳ�汾keyֵ
	 */
	public final static String SYSTEM_VERSION = "osver";
	/**
	 * ģ��Keyֵ
	 */
	public final static String MODEL = "model";
	/**
	 * ������Keyֵ
	 */
	public final static String MANUFACTURER = "mfr";
	/**
	 * SDK�汾keyֵ
	 */
	public final static String SDK_VERSION = "sdkver";
	/**
	 * APP����Keyֵ
	 */
	public final static String APP_PACKAGE_NAME = "pkg";
	/**
	 * APP�汾Keyֵ
	 */
 	public final static String APP_VERSION_NAME = "appver";
	/**
	 * ��Ļ��СKeyֵ
	 */
	public final static String SCREEN_SIZE = "screen";
	/**
	 * WIFI���������ַKeyֵ
	 */
	public final static String MAC = "mac";
	/**
	 * ��Ӫ��Keyֵ
	 */
	public final static String CARRIER = "carrier";
	/**
	 * �����������Keyֵ
	 */
	public final static String ACCESS = "access";
	/**
	 * ����Keyֵ
	 */
	public final static String LANG = "lang";

	private static volatile  Map<String, String> builderInfo = null;  //����̶�����Ϣ���̰߳�ȫ����
	private static volatile  Map<String, String> deviceInfo = null;
	private static volatile  Map<String, String> systemInfo = null;
	private static volatile  Map<String, String> appInfo = null;
	private static volatile  DisplayMetrics displayMetrics = null;
	private static volatile  String mac = null;
	private static volatile  String ProvidersName = "";
	
	
	private SystemInfo(){
	}
	/**
	 * ��ȡϵͳ��Ϣ�������������汾�����汾���롢ϵͳ����ϵͳ�汾��SDK�汾��ģ�顢�����̡�CPU���͡���Ļ�ֱ��ʺ����������ַ��
	 * @param context	������
	 * @return	ϵͳ��Ϣ
	 */
	public static Map<String, String> getSystemInfo(Context context) {
		if (systemInfo == null) {
			Map<String, String> info = new HashMap<String, String>();// �����洢�豸��Ϣ
			info.put(TIME_ZONE, TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT));
			info.putAll(getAppInfo(context));
			info.putAll(getBuilderInfo(context));
			info.putAll(getDeviceInfo(context));
			info.put(CARRIER, getProvidersName(context));
			info.put(ACCESS, getNetType(context));
			info.put(LANG, getLang(context));
			systemInfo = info;
		}
		return systemInfo;
	}	
	/**
	 * ��ȡAPP��Ϣ�������������汾�����汾����
	 * @param context	������
	 * @return APP��Ϣ
	 */
	public static Map<String, String> getAppInfo(Context context) {
		if (appInfo == null) {
			Map<String, String> info = new HashMap<String, String>();// �����洢app��Ϣ
			try {
				PackageManager pm = context.getPackageManager();// ��ð�������
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
						PackageManager.GET_ACTIVITIES);// �õ���Ӧ�õ���Ϣ������Activity
				if (pi != null) {
					String versionName = pi.versionName == null ? "null"
							: pi.versionName;
					info.put(APP_PACKAGE_NAME, pi.packageName);
					info.put(APP_VERSION_NAME, versionName);
					appInfo = info;
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		return appInfo;
	}
	/**
	 * ��ȡbuilder��Ϣ������ϵͳ����ϵͳ�汾��SDK�汾��ģ�顢�����̡�CPU����
	 * @param context	������
	 * @return builder��Ϣ
	 */
	public static Map<String, String> getBuilderInfo(Context context) {
		if (builderInfo == null) {
			HashMap<String, String> info = new HashMap<String, String>();// �����洢app��Ϣ
			info.put(SYSTEM_VERSION, Build.VERSION.RELEASE);
			info.put(SDK_VERSION, "" + Build.VERSION.SDK_INT);
			info.put(MODEL, Build.MODEL);
			info.put(MANUFACTURER, Build.MANUFACTURER);
			builderInfo = info;
		}
		return builderInfo;
	}
	/**
	 * ��ȡ�豸��Ϣ��������Ļ�ֱ��ʺ����������ַ
	 * @param context	������
	 * @return �豸��Ϣ
	 */
	public static Map<String, String> getDeviceInfo(Context context) {
		if (deviceInfo == null) {
			Map<String, String> info = new HashMap<String, String>();// �����洢�豸��Ϣ
			DisplayMetrics dm = getDisplayMetrics(context);
			info.put(SCREEN_SIZE, new String(dm.widthPixels + "*" + dm.heightPixels));
			info.put(MAC, getMacAddress(context));
			deviceInfo = info;
		}
		return deviceInfo;
	}
	/**
	 * ��ȡ��Ļ�ֱ���
	 * @param context	������
	 * @return ��Ļ�ֱ���
	 */
	public static DisplayMetrics getDisplayMetrics(Context context){
		if (displayMetrics == null) {
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			displayMetrics = dm;
		}
		return displayMetrics;
	}
	/**
	 * ��ȡWIFI���������ַ
	 * @param context	������
	 * @return WIFI���������ַ
	 */
	public static String getMacAddress(Context context) {
		if (mac == null) {
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			mac = info.getMacAddress();
		}
		return mac;
	}
	
    /** 
     * Role:Telecom service providers��ȡ�ֻ���������Ϣ <BR> 
     * ��Ҫ����Ȩ��<uses-permission 
     * android:name="android.permission.READ_PHONE_STATE"/> 
     * IMSI��ǰ��3λ460�ǹ��ң������ź���2λ00 02���й��ƶ���01���й���ͨ��03���й����š�<BR> 
     * Date:2012-3-12 <BR> 
     */  
	public static String getProvidersName(Context context) {
		if (ProvidersName == "") {
			String pn = "";
			String IMSI = ((TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE))
					.getSubscriberId();
			if (IMSI != null) {
				if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
					pn = "�й��ƶ�";
				} else if (IMSI.startsWith("46001")) {
					pn = "�й���ͨ";
				} else if (IMSI.startsWith("46003")) {
					pn = "�й�����";
				}
			}
			ProvidersName = pn;
		}
		return ProvidersName;
	}
    
    /**
     * ��õ�ǰ����������
     * @param context ������
     * @return ��ǰ����������
     */
    public static String getNetType(Context context){
    	String netType = null;
    	int type = NetworkUtil.getNetworkType(context);
    	if(type == Constant.NET_TYPE_WIFI){
    		netType = "wifi";
    	} else if(type == Constant.NET_TYPE_3G){
    		netType = "3G";
    	} else if(type == Constant.NET_TYPE_2G){
    		netType = "2G";
    	} else if(type == Constant.NET_TYPE_4G){
    		netType = "4G";
    	} else if(type == Constant.NET_TYPE_OTHER){
    		netType = "other";
    	} else if(type == Constant.NET_TYPE_NONE){
    		netType = "none";
    	}
    	return netType;
    }
    /**
     * ���ϵͳ����
     * @param context ������
     * @return ϵͳ����
     */
	private static String getLang(Context context) {
		return Locale.getDefault().toString();
	}
}
