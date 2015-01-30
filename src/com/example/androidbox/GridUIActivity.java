package com.example.androidbox;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class GridUIActivity extends Activity {

	private static final String TAG = GridUIActivity.class.getSimpleName();
	
	static int[] menu_icon_ids = {
		R.drawable.icon1,
		R.drawable.icon2,
		R.drawable.icon3,
		R.drawable.icon4,
		R.drawable.icon5,
		R.drawable.icon6,
		R.drawable.icon1,
		R.drawable.icon2,
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridviewui);
		String[] menu_text = getResources().getStringArray(R.array.main_munu_name);
		GridView menu_grid = (GridView) findViewById(R.id.grid);
		
		ArrayList<HashMap<String, Object>> icon_text_list = new ArrayList<>();
		for(int i=0;i<8;i++){
			HashMap<String, Object> map = new HashMap<>();
			map.put("itemIcon", menu_icon_ids[i]);
			map.put("itemtext", menu_text[i]);
			icon_text_list.add(map);
		}
		
		SimpleAdapter girdAdapter = new SimpleAdapter(this, icon_text_list, R.layout.griditemui
				, new String[]{"itemIcon","itemtext"}, new int[]{R.id.main_menu_icon, R.id.main_menu_text});
		
		menu_grid.setAdapter(girdAdapter);
		menu_grid.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					goMCtripPage();
					break;
				case 8:
					goMCtripPage();
					break;
				case 9:
					goMCtripPage();
					break;
				default:
					break;
				}
			}
			
		});
		
	}
	
	public void goMCtripPage(){
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.ctrip.com"));
		startActivity(intent);
	}
	
}
