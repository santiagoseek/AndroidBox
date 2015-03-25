package com.example.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.androidbox.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class UIListViewActivity extends Activity {

	SQLiteDatabase mDb;
	SQLiteDatabaseDao dao;
	// �洢���ݵ������б�
	ArrayList<HashMap<String, Object>> listData;
	// ������
	SimpleAdapter listItemAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uilistview);
		dao = new SQLiteDatabaseDao();

		ListView list = (ListView) findViewById(R.id.uilistView1);
		listItemAdapter = new SimpleAdapter(UIListViewActivity.this,
				listData,// ����Դ
				R.layout.uilistviewitem,// ListItem��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "id","username", "birthday" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] {  R.id.uid, R.id.username, R.id.birthday });
		list.setAdapter(listItemAdapter);
		list.setOnCreateContextMenuListener(listviewLongPress);
	}

	// �򵥵����ݿ������

	class SQLiteDatabaseDao {

		public SQLiteDatabaseDao() {
			mDb = openOrCreateDatabase("users.db",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);
			// ��ʼ��������
			createTable(mDb, "student");
			// ��ʼ����������
			insert(mDb, "student");
			// ��ʼ����ȡ�������ݱ�����
			getAllData("student");
		}

		// ����һ�����ݿ�
		public void createTable(SQLiteDatabase mDb, String table) {
			try {
				mDb.execSQL("create table if not exists "
						+ table
						+ " (id integer primary key autoincrement, "
						+ "username text not null, birthday text not null,image text);");
			} catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "���ݱ���ʧ��",
						Toast.LENGTH_LONG).show();
			}
		}

		// ��������
		public void insert(SQLiteDatabase mDb, String table) {

			// ��ʼ������3������
			ContentValues values = new ContentValues();
			values.put("username", "LiMei");
			values.put("birthday", "Birthday:6-18");
			//values.put("image", R.drawable.o);
			mDb.insert(table, null, values);

			values.put("username", "LinQiao");
			values.put("birthday", "Birthday:8-22");
			//values.put("image", R.drawable.t);
			mDb.insert(table, null, values);

			values.put("username", "WiLee");
			values.put("birthday", "Birthday:9-12");
			//values.put("image", R.drawable.f);
			mDb.insert(table, null, values);

		}

		// ��ѯ��������
		public void getAllData(String table) {
			Cursor c = mDb.rawQuery("select * from " + table, null);
			int columnsSize = c.getColumnCount();
			listData = new ArrayList<HashMap<String, Object>>();
			// ��ȡ�������
			while (c.moveToNext()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < columnsSize; i++) {
					map.put("id", c.getString(0));
					map.put("username", c.getString(1));
					map.put("birthday", c.getString(2));
					//map.put("image", c.getString(3));
				}
				listData.add(map);
			}
		}

		// ɾ��һ������
		public boolean delete(SQLiteDatabase mDb, String table, int id) {
			String whereClause = "id=?";
			String[] whereArgs = new String[] { String.valueOf(id) };
			try {
				mDb.delete(table, whereClause, whereArgs);
			} catch (SQLException e) {
				Toast.makeText(getApplicationContext(), "ɾ�����ݿ�ʧ��",
						Toast.LENGTH_LONG).show();
				return false;
			}
			return true;
		}
	}

	// �����¼���Ӧ
	OnCreateContextMenuListener listviewLongPress = new OnCreateContextMenuListener() {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			new AlertDialog.Builder(UIListViewActivity.this)
					/* �������ڵ�����ͷ���� */
					.setTitle("ɾ����ǰ����")
					/* ���õ������ڵ�ͼʽ */
					.setIcon(android.R.drawable.ic_dialog_info)
					/* ���õ������ڵ���Ϣ */
					.setMessage("ȷ��ɾ����ǰ��¼")
					.setPositiveButton("��",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									// ��ȡλ������
									int mListPos = info.position;
									// ��ȡ��ӦHashMap��������
									HashMap<String, Object> map = listData
											.get(mListPos);
									// ��ȡid
									int id = Integer.valueOf((map.get("id")
											.toString()));
									// ��ȡ�������ֵ��,���Զ����ݽ�����صĲ���,�����������
									if (dao.delete(mDb, "student", id)) {
										// �Ƴ�listData������
										listData.remove(mListPos);
										listItemAdapter.notifyDataSetChanged();
									}
								}
							})
					.setNegativeButton("��",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									// ʲôҲû��

								}
							}).show();
		}
	};

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		mDb.close();
	}
}
