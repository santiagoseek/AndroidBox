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
	 * 时区Key值
	 */
	public final static String TIME_ZONE = "timezone";
	/**
	 * 系统版本key值
	 */
	public final static String SYSTEM_VERSION = "osver";
	/**
	 * 模块Key值
	 */
	public final static String MODEL = "model";
	/**
	 * 制造商Key值
	 */
	public final static String MANUFACTURER = "mfr";
	/**
	 * SDK版本key值
	 */
	public final static String SDK_VERSION = "sdkver";
	/**
	 * APP包名Key值
	 */
	public final static String APP_PACKAGE_NAME = "pkg";
	/**
	 * APP版本Key值
	 */
 	public final static String APP_VERSION_NAME = "appver";
	/**
	 * 屏幕大小Key值
	 */
	public final static String SCREEN_SIZE = "screen";
	/**
	 * WIFI网卡物理地址Key值
	 */
	public final static String MAC = "mac";
	/**
	 * 运营商Key值
	 */
	public final static String CARRIER = "carrier";
	/**
	 * 网络访问类型Key值
	 */
	public final static String ACCESS = "access";
	/**
	 * 语言Key值
	 */
	public final static String LANG = "lang";

	private static volatile  Map<String, String> builderInfo = null;  //缓存固定的信息，线程安全对象
	private static volatile  Map<String, String> deviceInfo = null;
	private static volatile  Map<String, String> systemInfo = null;
	private static volatile  Map<String, String> appInfo = null;
	private static volatile  DisplayMetrics displayMetrics = null;
	private static volatile  String mac = null;
	private static volatile  String ProvidersName = "";
	
	
	private SystemInfo(){
	}
	/**
	 * 获取系统信息，包括包名、版本名、版本代码、系统名、系统版本、SDK版本、模块、制造商、CPU类型、屏幕分辨率和网卡物理地址。
	 * @param context	上下文
	 * @return	系统信息
	 */
	public static Map<String, String> getSystemInfo(Context context) {
		if (systemInfo == null) {
			Map<String, String> info = new HashMap<String, String>();// 用来存储设备信息
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
	 * 获取APP信息，包括包名、版本名、版本代码
	 * @param context	上下文
	 * @return APP信息
	 */
	public static Map<String, String> getAppInfo(Context context) {
		if (appInfo == null) {
			Map<String, String> info = new HashMap<String, String>();// 用来存储app信息
			try {
				PackageManager pm = context.getPackageManager();// 获得包管理器
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
						PackageManager.GET_ACTIVITIES);// 得到该应用的信息，即主Activity
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
	 * 获取builder信息，包括系统名、系统版本、SDK版本、模块、制造商、CPU类型
	 * @param context	上下文
	 * @return builder信息
	 */
	public static Map<String, String> getBuilderInfo(Context context) {
		if (builderInfo == null) {
			HashMap<String, String> info = new HashMap<String, String>();// 用来存储app信息
			info.put(SYSTEM_VERSION, Build.VERSION.RELEASE);
			info.put(SDK_VERSION, "" + Build.VERSION.SDK_INT);
			info.put(MODEL, Build.MODEL);
			info.put(MANUFACTURER, Build.MANUFACTURER);
			builderInfo = info;
		}
		return builderInfo;
	}
	/**
	 * 获取设备信息，包括屏幕分辨率和网卡物理地址
	 * @param context	上下文
	 * @return 设备信息
	 */
	public static Map<String, String> getDeviceInfo(Context context) {
		if (deviceInfo == null) {
			Map<String, String> info = new HashMap<String, String>();// 用来存储设备信息
			DisplayMetrics dm = getDisplayMetrics(context);
			info.put(SCREEN_SIZE, new String(dm.widthPixels + "*" + dm.heightPixels));
			info.put(MAC, getMacAddress(context));
			deviceInfo = info;
		}
		return deviceInfo;
	}
	/**
	 * 获取屏幕分辨率
	 * @param context	上下文
	 * @return 屏幕分辨率
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
	 * 获取WIFI网卡物理地址
	 * @param context	上下文
	 * @return WIFI网卡物理地址
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
     * Role:Telecom service providers获取手机服务商信息 <BR> 
     * 需要加入权限<uses-permission 
     * android:name="android.permission.READ_PHONE_STATE"/> 
     * IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。<BR> 
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
					pn = "中国移动";
				} else if (IMSI.startsWith("46001")) {
					pn = "中国联通";
				} else if (IMSI.startsWith("46003")) {
					pn = "中国电信";
				}
			}
			ProvidersName = pn;
		}
		return ProvidersName;
	}
    
    /**
     * 获得当前的网络类型
     * @param context 上下文
     * @return 当前的网络类型
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
     * 获得系统语言
     * @param context 上下文
     * @return 系统语言
     */
	private static String getLang(Context context) {
		return Locale.getDefault().toString();
	}
}
