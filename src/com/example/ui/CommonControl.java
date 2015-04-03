package com.example.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class CommonControl extends Activity {
	private ToggleButton toggleButton ;
	private Context context;
	
	private ListViewAdapter listViewAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		
		LinearLayout linearlayout = new LinearLayout(this);
		linearlayout.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		
		toggleButton = new ToggleButton(this); 
		toggleButton.setBackgroundColor(Color.GRAY);
		toggleButton.setText("Turn OFF");
		toggleButton.setTextOn("Turn ON");
		toggleButton.setTextOff("Turn OFF");
		
		//toggleButton.setLayoutParams(params);
		
		//linearlayout.addView(toggleButton);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				//toggleButton.setChecked(isChecked);
				if(isChecked)
				{
					toggleButton.setBackgroundColor(Color.RED);
				}
				else{
					//toggleButton.setBackgroundColor(Color.GRAY);
				}
				Toast.makeText(context, String.valueOf(isChecked) , Toast.LENGTH_LONG).show();
			}});
		Button testButton = new Button(this);
		testButton.setText("Clear");
		
		//testButton.setLayoutParams(params);
		
		EditText searchText = new EditText(this);
		searchText.setImeOptions(EditorInfo.IME_ACTION_DONE);;
		searchText.setHint("Search");
		
		params.weight = 1.0f;
		searchText.setLayoutParams(params);
		
		searchText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				listViewAdapter.getFilter().filter(arg0);
			}});
		
		
		LinearLayout innerlinearlayout = new LinearLayout(this);
		innerlinearlayout.setOrientation(LinearLayout.HORIZONTAL);
		
		//prevent the EditText get the default focus.
		innerlinearlayout.setFocusable(true);
		innerlinearlayout.setFocusableInTouchMode(true);
		
		innerlinearlayout.addView(toggleButton);
		innerlinearlayout.addView(searchText);
		innerlinearlayout.addView(testButton);
		linearlayout.addView(innerlinearlayout);
		
/*		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		relativeLayout.addView(toggleButton);
		relativeLayout.addView(testButton);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toggleButton.getLayoutParams();
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		toggleButton.setLayoutParams(params);
		RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) testButton.getLayoutParams();
		params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		testButton.setLayoutParams(params1);
		
		linearlayout.addView(relativeLayout);*/
		
		linearlayout.addView(new Button(this));
		
		linearlayout.addView(addListView());
		
		setContentView(linearlayout);
	}
	
	public ListView addListView(){
		ListView lv = new ListView(this);
		lv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		listViewAdapter = new ListViewAdapter();
		lv.setAdapter(listViewAdapter);
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(context, listViewAdapter.getItem(arg2).toString(), Toast.LENGTH_LONG).show();
			}});
		return lv;
	}
	
	class ListViewAdapter extends BaseAdapter implements Filterable{

		private ArrayList<String> mOriginalItems;
		private ArrayList<String> items;
		public ListViewAdapter(){
			items = new ArrayList<String>();
			for(int i=0;i<60;i++)
			{
				items.add("this itmes itme " + i);
			}
			items.add("testingforFilter");
			items.add("onlyforFilter");
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return items.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textView = new TextView(context);
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT); 
			textView.setTextSize(10);
			textView.setText(items.get(position));
			return textView;
		}
		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub
			Filter filter  = new Filter(){

				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					// TODO Auto-generated method stub
					FilterResults results = new FilterResults();
					
					List<String> FilteredArrList = new ArrayList<String>();
					
					if(mOriginalItems == null){
						mOriginalItems = new ArrayList<String>(items);
					}
					
					if(constraint == null || constraint.length() == 0){
						results.count = mOriginalItems.size();
						results.values = mOriginalItems;
					} else {
						constraint = constraint.toString().toLowerCase();
						for(int i =0;i<mOriginalItems.size();i++){
							String data = mOriginalItems.get(i);
							if(data.toLowerCase().contains(constraint)){
								FilteredArrList.add(data);
							}
						}
						results.count = FilteredArrList.size();
						results.values = FilteredArrList;
					}
					return results;
				}

				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {
					// TODO Auto-generated method stub
					items = (ArrayList<String>) results.values;
					notifyDataSetChanged();
				}};
			return filter;
		}
		
	}
}
