package com.example.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.androidbox.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UIExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
    private LayoutInflater father_Inflater=null;
    private LayoutInflater son_Inflater=null;
    
    private ArrayList<String> father_array;//¸¸²ã
    private ArrayList<List<String>> son_array;//×Ó²ã
    
    public UIExpandableListAdapter(Context context){
    	 this.context=context;
         father_Inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         son_Inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         Init_data();
    }

    
	private void Init_data() {
		// TODO Auto-generated method stub
		father_array = new ArrayList<String>();
		son_array = new ArrayList<List<String>>();
		father_array.add("PV");
		father_array.add("Action");
		father_array.add("Trace");
		father_array.add("Metrice");
		List<String> pv = new ArrayList<String>();
		pv.add("pv1");
		pv.add("pv2");
		pv.add("pv3");
		pv.add("pv4");
		son_array.add(pv);
		List<String> action = new ArrayList<String>();
		action.add("Action1");
		action.add("Action2");
		action.add("Action3");
		action.add("Action4");
		son_array.add(action);
		List<String> trace = new ArrayList<String>();
		trace.add("trace1");
		trace.add("trace2");
		trace.add("trace3");
		trace.add("trace4");
		son_array.add(trace);
		List<String> metric = new ArrayList<String>();
		metric.add("metric1");
		metric.add("metric2");
		metric.add("metric3");
		metric.add("metric4");
		son_array.add(metric);
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return son_array.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		Son_ViewHolder son_ViewHolder = null;
		if(convertView == null){
			convertView = son_Inflater.inflate(R.layout.uielvson, null);
			son_ViewHolder = new Son_ViewHolder();
			son_ViewHolder.son_TextView = (TextView) convertView.findViewById(R.id.son_textview);
			convertView.setTag(son_ViewHolder);
		}
		else{
			son_ViewHolder = (Son_ViewHolder) convertView.getTag();
		}
		son_ViewHolder.son_TextView.setText(son_array.get(groupPosition).get(childPosition));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return son_array.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return father_array.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return father_array.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Father_ViewHolder father_ViewHolder = null;
		if(convertView == null){
			convertView = father_Inflater.inflate(R.layout.uielvfather, null);
			father_ViewHolder = new Father_ViewHolder();
			father_ViewHolder.father_TextView = (TextView) convertView.findViewById(R.id.father_textview);
			father_ViewHolder.image_view=(ImageView)convertView.findViewById(R.id.father_imageview);
			convertView.setTag(father_ViewHolder);
		}
		else{
			father_ViewHolder = (Father_ViewHolder) convertView.getTag();
		}
		father_ViewHolder.father_TextView.setText(father_array.get(groupPosition));
		if(isExpanded){
			father_ViewHolder.image_view.setImageDrawable(context.getResources().getDrawable(R.drawable.imagetodown));
		}
		else{
			father_ViewHolder.image_view.setImageDrawable(context.getResources().getDrawable(R.drawable.imagetoright));
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	
	  public final class Father_ViewHolder
	    {
	        private TextView father_TextView;
	        private ImageView image_view;

	    }
	    public final class Son_ViewHolder
	    {
	        private TextView son_TextView;
	    }


}
