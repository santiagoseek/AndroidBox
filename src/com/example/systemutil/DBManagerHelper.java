package com.example.systemutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManagerHelper extends SQLiteOpenHelper {
	
	private static final String LOG_TAG = Constant.TAG + "-DBManageHelper";

	public static final String TABLE_NAME = "androidboxDB";

	private static final String TABLE_CREATE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME+ " ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT, type VARCHAR(16) NOT NULL, "
			+ "priority SMALLINT NOT NULL DEFAULT 5, offer_time LONG NOT NULL, "
			+ "expire_time LONG, version VARCHAR(8), msg_data BLOB)";
	

	public DBManagerHelper(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	// ���ݿ��һ�α�����ʱonCreate�ᱻ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_CREATE_SQL);
	}

	//���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(LOG_TAG, "���ݰ汾�仯����ʼ�����ݿ����");
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new SQLiteException("Can't downgrade database from version "
				+ oldVersion + " to " + newVersion);
	}

}
