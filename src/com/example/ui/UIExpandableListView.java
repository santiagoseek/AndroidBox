package com.example.ui;

import com.example.androidbox.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class UIExpandableListView extends Activity {
	private ExpandableListView listView = null;

	private UIExpandableListAdapter adapter = null;// 自定义的适配器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uiexpandablelistview);
		listView = (ExpandableListView) findViewById(R.id.expandableListView1);
		adapter = new UIExpandableListAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
				Toast.makeText(
						UIExpandableListView.this,
						"你点击了:"
								+ adapter
										.getChild(groupPosition, childPosition)
										.toString(), Toast.LENGTH_LONG).show();
				return false;

			}

		});

	}

}
