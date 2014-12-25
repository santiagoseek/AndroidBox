package com.example.androidbox;

import com.example.internetutil.GetAction;
import com.example.internetutil.GetParam;
import com.example.internetutil.PersonalListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InternetUtilActivity extends Activity {
	
	Context context = null;
	
	private GetAction action;
	private GetParam outParam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.internetutil);

		context = this;
		Button getBt = (Button) findViewById(R.id.getBt);
		TextView displayTv = (TextView) findViewById(R.id.displayTv);
		
		getBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				action = new GetAction(1, "");
				outParam = new GetParam();
				action.setParam(outParam);
				
				PersonalListener personalListener = new PersonalListener(context);
				action.setOnActionListener(personalListener);
				action.startAction();
				
			}
		});

}
}
