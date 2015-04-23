package com.example.systemutil;
public class Constant {
	//state常量
	public static final Integer Init = 0;
	public static final Integer Load_Config = 1;
	public static final Integer Check_Net = 2;
	public static final Integer Load_Queue = 3;
	public static final Integer Send_Data = 4;
	public static final Integer Crash_Report = 5;
	
	//网络常量
	public static final Integer NET_TYPE_NONE = 0;
	public static final Integer NET_TYPE_2G = 1;
	public static final Integer NET_TYPE_3G = 2;
	public static final Integer NET_TYPE_4G = 3;
	public static final Integer NET_TYPE_OTHER = 4;
	public static final Integer NET_TYPE_WIFI = 5;
	
	public static final String TAG = "AndroidBox";
	public static final Integer PACK_MAX_SIZE = 2000;
	
	//时间常量
	public static final int SECOND = 1000;
	public static final int MINUTE = 60*SECOND;
	public static final int HOUR = 60*MINUTE;
	public static final int DAY = 24*HOUR;
	public static final int WEEK = 7*DAY;
	
	//配置常量
	public static final String DISPATCH_URL="DISPATCH_URL";
	public static final String DISPATCH_WIFI_PERIOD = "DISPATCH_PERIOD_WIFI";
	public static final String DISPATCH_2G_PERIOD = "DISPATCH_PERIOD_CELLULAR_2G";
	public static final String DISPATCH_3G_PERIOD = "DISPATCH_PERIOD_CELLULAR_3G";
	public static final String CONFIG_WIFI_PERIOD = "CONFIG_UPDATEPERIOD_WIFI";
	public static final String CONFIG_2G_PERIOD = "CONFIG_UPDATEPERIOD_CELLULAR_2G";
	public static final String CONFIG_3G_PERIOD = "CONFIG_UPDATEPERIOD_CELLULAR_3G";
	public static final String DISABLED = "DISABLED";
	public static final String SESSION_EXPIRE= "SESSION_EXPIRY";
	public static final String MESSAGE_TTL = "MESSAGE_TTL";
	public static final String MESSAGE_PRIOR = "MESSAGE_PRIORITY";
	public static final String EVENT_MAX_REPEAT = "EVENT_THRESHOLD_MAXREPEAT";
	public static final String EVENT_MAX_PER_SECOND = "EVENT_THRESHOLD_MAXPERSECOND";		
	public static final String CONFIG_UPDATE = "CONFIG_UPDATE";
	
	public static final String QUEUE_MAX_MESSAGES = "LOCALQUEUE_MAXMESSAGES";
	public static final String DB_STORAGE_MAX_SIZE = "LOCALQUEUE_MAXSIZE";
	
	
	public static final String UBT_SID = "UBT_SID";
	public static final String UBT_PVID = "UBT_PVID";
	public static final String UBT_LAST_ACTIONTIME="UBT_LAST_ACTIONTIME";
	public static final String UBT_FlAG="UBT_FLAG";
	public static final String UBT_UUID = "UBT_UUID";
	public static final String UBT_MSQ_SEQ_NUM = "UBT_MSG_SEQ_NUM";
	
	public static final String TYPE_ACTION = "m_action";
	public static final String TYPE_METRIC="m_metric";
	public static final String TYPE_PAGEVIEW="m_pv";
	public static final String TYPE_TRACE="m_trace";
	public static final String PAGEVIEW_VERSION = "2";
	public static final String ACTION_VERSION = "3";
	public static final String METRIC_VERSION = "3";
	public static final String TRACE_VERSION = "3";

	//MobileAP对外提供配置数据版本、修订号配置
	public static final String CONFIG_VER = "1.0";
	public static final String CONFIG_REV_KEY = "rev";
	public static final String CONFIG_VER_KEY = "ver";
	
	public static final String SDK_OS = "Android";
	
	// /data/data/ctrip.android.view/databases/UBT.db /data/data/com.example.ubt/UBT.db
	public static final String DB_NAME = "AndroidBox.db";
	public static final int DB_VERSION = 1;
	public static final String MSG_SEQ_NUM = "msg_sequence_num_file";
}
