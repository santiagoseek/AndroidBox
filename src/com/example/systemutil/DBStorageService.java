package com.example.systemutil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DBStorageService {

	
	private static final String LOG_TAG = Constant.TAG + "-DBStorageService";
	
	private static DBManagerHelper dbHelper;
	
	private static SQLiteDatabase sqlitedb; 
	
	private static Context mcontext;
	
	private static File dataBaseFile;
	
	private static final String INSERT_SQL = "INSERT INTO "+DBManagerHelper.TABLE_NAME+" (type,priority,offer_time,expire_time,version,msg_data) VALUES(?,?,?,?,?,?)";
	
	private static final String DISPATCH_SELECT_SQL = "SELECT * FROM "+DBManagerHelper.TABLE_NAME+" ORDER BY priority DESC,id ASC LIMIT ?";
	
	private DBStorageService() {		
		try {
			sqlitedb = dbHelper.getWritableDatabase();
			dataBaseFile = mcontext.getDatabasePath(Constant.DB_NAME);
			initSQLitePragma();
		}catch(Exception ex){
			sqlitedb = null;
			Log.e(LOG_TAG, "构造DBStorageService异常", ex);
		}
	}
	
	private static enum InstanceEnum {
		StorageService(new DBStorageService());		
		private InstanceEnum(DBStorageService instance){
			this.instance = instance;
		}
		private DBStorageService instance;
	}	
	
	public static void initDBManage(Context context){
		if(context == null){
			return;
		}
		synchronized(DBStorageService.class){
			if(dbHelper == null){
				mcontext = context;
				dbHelper = new DBManagerHelper(context, Constant.DB_NAME, Constant.DB_VERSION);
			}
		}
	}
	
	public static DBStorageService getInstance(){
		return InstanceEnum.StorageService.instance;
	}
	
	/**
	 * 批量保存队列信息
	 * @param msgList
	 */
	public int save(List<Message> msgList){
		if(msgList == null || msgList.isEmpty() || !dbisOpen()){
			return 0;
		}	
		Log.e(LOG_TAG, "In the save method, save msgList size is: " + msgList.size());
		synchronized (this) {
			Log.e(LOG_TAG, "should be log this line " + msgList.size());
			try {	
				sqlitedb.beginTransaction();
				for(Message msg : msgList) {
					save(msg);
				}						
				sqlitedb.setTransactionSuccessful();
				Log.d(LOG_TAG, "batch insert queue-msg, rows: "+msgList.size());
				return 1;
			}catch(SQLiteException ex){
				Log.e(LOG_TAG, "保存队列消息异常", ex);
			}catch(Throwable t){
				Log.e(LOG_TAG, t.getMessage(), t);
			}finally {
				try {
					sqlitedb.endTransaction();
				}catch(Throwable t){
					Log.e(LOG_TAG, t.getMessage(), t);
				}
			}
		}
		return 2;
	}
	
	/**
	 * 最多查询limit条记录
	 * @param limit
	 * @return
	 */
	public List<Message> query(int limit){
		Cursor cursor = null;
		List<Message> msgList = new ArrayList<Message>();
		try {
			synchronized (this) {
				cursor = dispatchRawQuery(limit);
			}
			if(cursor != null){
				while(cursor.moveToNext()){
					msgList.add(createQueueMsg(cursor));
				}
				Log.i(LOG_TAG, "query mobile-msg from sqlite, rows: "+msgList.size());
			}				
		}catch(Throwable t){
			Log.e(LOG_TAG, t.getMessage(), t);
		}finally {
			try {
				if(cursor != null){
					cursor.close();
				}
			}catch(Throwable t){
				Log.e(LOG_TAG, t.getMessage(), t);
			}
		}
		return msgList;
	}
	
	/**
	 * 按Id删除记录
	 * @param ids
	 */

	public void remove(List<Integer> ids){
		if(!dbisOpen() || ids == null || ids.size() < 1){
			return;
		}
		try {
			String[] msgIds = formatMsgIds(ids);	
			int result = 0; 
			synchronized (this) {			
				result = sqlitedb.delete(DBManagerHelper.TABLE_NAME, " id IN ("+makePlaceholders(ids)+")", msgIds);
				cleanDBSpace();
			}
			Log.i(LOG_TAG, "ack remove mobile-msg from sqlite, rows: "+result);
		}catch(Throwable t){
			Log.e(LOG_TAG, t.getMessage(), t);
		}
	}
	
	public void removeAll(){
		try{
			int result = 0;
			synchronized(this){
				result = sqlitedb.delete(DBManagerHelper.TABLE_NAME, null, null);
				cleanDBSpace();
			}
			Log.i(LOG_TAG, "remove all data from sqlite, rows: " + result);
		}catch(Throwable t){
			Log.e(LOG_TAG, t.getMessage(), t);
		}
	}
	/**
	 * 删除掉超过超时时间的消息记录
	 */
	public void removeIfTimeout(){
		if(!dbisOpen()){
			return;
		}
		int result = 0; 
		try {
			synchronized (this) {
				result = sqlitedb.delete(DBManagerHelper.TABLE_NAME, " datetime('now') > expire_time", null);
				cleanDBSpace();
			}
			Log.i(LOG_TAG, "removeIfTimeout mobile-msg from sqlite, rows: "+result);
		}catch(Throwable t){
			Log.e(LOG_TAG, t.getMessage(), t);
		}
	}
	
	/**
	 * 
	 */
	public void removeIfLackSpace(short priority){
		if(!dbisOpen()){
			return;
		}
		int result = 0; 
		try {
			synchronized (this) {
				result = sqlitedb.delete(DBManagerHelper.TABLE_NAME, " priority <= ?", new String[]{String.valueOf(priority)});
				cleanDBSpace();
			}
			Log.i(LOG_TAG, "removeIfLackSpace mobile-msg from sqlite, rows: "+result);
		}catch(Throwable t){
			Log.e(LOG_TAG, t.getMessage(), t);
		}
	}
	
	/**
	 * 清理数据库空间
	 */
	public void cleanDBSpace(){
		if(!dbisOpen()){
			return;
		}
		try {
			synchronized (this) {
				sqlitedb.execSQL("VACUUM");
				Log.e(LOG_TAG, "cleanDBSpace successfully");
			}
		}catch(Exception ex){
			Log.e(LOG_TAG, "cleanSpace exception", ex);
		}
	}
	
	public double getDBFileKBytes(){
		final File dbFile = dataBaseFile;
		return (dbFile == null || !dbFile.exists()) ? 0.00 : dbFile.length()/1024.00;
	}
	
	
	public void closeDB(){
		try {
			dbHelper.close();
		}catch(Throwable t){
			Log.e(LOG_TAG, t.getMessage(), t);
		}
	}
	
	private void initSQLitePragma(){
		Cursor cursor = null;
		try {
			cursor = sqlitedb.rawQuery("PRAGMA journal_size_limit=4096", null);
		}catch(Throwable e){
			Log.e(LOG_TAG, "initSQLitePragma Error", e);
		}finally {
			try {
				if(cursor != null){
					cursor.close();
				}
			}catch(Throwable t){
				Log.e(LOG_TAG, t.getMessage(), t);
			}
		}
	}
	
	private String[] formatMsgIds(List<Integer> ids){
		String[] msgIds = new String[ids.size()];
		for(int i = 0,len = ids.size(); i < len; i++){
			msgIds[i] = String.valueOf(ids.get(i));
		}
		return msgIds;
	}
	
	private String makePlaceholders(List<Integer> ids){
		StringBuilder placeHolders = new StringBuilder("?");
		for(int i = 1,len = ids.size(); i < len; i++){
			placeHolders.append(",?");
		}
		return placeHolders.toString();
	}
	
	private Cursor dispatchRawQuery(int limit){	
		if(!dbisOpen()){
			return null;
		}
		return sqlitedb.rawQuery(DISPATCH_SELECT_SQL, new String[]{String.valueOf(limit)});
	}
	
	private void save(Message msg){
		if(!dbisOpen() || msg == null || msg.getType() == null){
			return;
		}		
		Object[] bindArgs = new Object[]{msg.getType(), msg.getPriority(), msg.getOfferTime(),
				msg.getExpireTime(), msg.getVersion(), msg.getUbtData().getBytes()};
		sqlitedb.execSQL(INSERT_SQL, bindArgs);
	}
	
	private Message createQueueMsg(Cursor cursor){
		Message msg = new Message();	
		msg.setId(cursor.getInt(cursor.getColumnIndex("id")));
		msg.setExpireTime(cursor.getLong(cursor.getColumnIndex("expire_time")));
		msg.setOfferTime(cursor.getLong(cursor.getColumnIndex("offer_time")));
		msg.setPriority(cursor.getShort(cursor.getColumnIndex("priority")));
		msg.setType(cursor.getString(cursor.getColumnIndex("type")));
		msg.setVersion(cursor.getString(cursor.getColumnIndex("version")));
		//msg.setUbtData(createUbtData(cursor.getString(cursor.getColumnIndex("msg_data"))));
		msg.setUbtData(cursor.getBlob(cursor.getColumnIndex("msg_data")).toString());
		return msg;
	}
	
	private boolean dbisOpen(){
		return (sqlitedb != null && sqlitedb.isOpen());
	}
/*	
	private UBTData parseObject(String jsonString){
		return JSON.parseObject(jsonString, UBTData.class);
//		JSONObject jsonObj = null;
//		try {
//			jsonObj = new JSONObject(jsonString);
//			JSONObject jsonHead = jsonObj.getJSONObject("header");		
//			Header header = new Header(jsonHead.getString("type"), jsonHead.getString("version"));		
//			JSONArray jsonCommon = jsonHead.getJSONArray("common");
//			
//			List<Object> commonData = header.getCommon();
//			for(int i = 0,length = jsonCommon.length(); i < length; i++){
//				commonData.add(jsonCommon.get(i));
//			}
//			JSONArray jsonBody = jsonObj.getJSONArray("body");
//			Body body = new Body();
//			List<Object> bodyData = body.getData();
//			for(int i = 0, length = jsonBody.length(); i < length; i++){
//				bodyData.add(jsonBody.get(i));
//			}	
//			UBTData ubtData = new UBTData();
//			ubtData.setBody(body);
//			ubtData.setHeader(header);
//			return ubtData;
//			
//		} catch (JSONException e) {
//			Log.e(LOG_TAG, "parseObject:"+e.getMessage());
//			return new UBTData();
//		}
	}
	
	private String toJsonString(UBTData ubtData){
		if(ubtData == null || ubtData.getHeader() == null || ubtData.getBody() == null){
			return null;
		}
		return JSON.toJSONString(ubtData,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullNumberAsZero);
//		JSONObject ubtJson = new JSONObject();
//		if(ubtData == null || ubtData.getHeader() == null || ubtData.getBody() == null){
//			return null;
//		}
//		try {
//			JSONObject header = new JSONObject();	
//			header.put("type", ubtData.getHeader().getType() == null ? "" : ubtData.getHeader().getType());
//			header.put("version", ubtData.getHeader().getVersion() == null ? "" : ubtData.getHeader().getVersion());
//			header.put("common", JsonUtil.put2JSONArray(ubtData.getHeader().getCommon()));	
//			
//			ubtJson.put("header", header);
//			ubtJson.put("body", JsonUtil.put2JSONArray(ubtData.getBody().getData()));
//			return ubtJson.toString();
//		} catch (JSONException e) {
//			Log.e(LOG_TAG, "toJsonString:"+e.getMessage());
//			return null;
//		}		
	}*/

}
