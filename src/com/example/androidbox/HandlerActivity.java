package com.example.androidbox;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HandlerActivity extends Activity {
	Handler handler = new Handler();
	
	private TextView displayTextview = null;
	
	Runnable update_thread = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			displayTextview.append("\nUpdateThread......");
			handler.postDelayed(update_thread, 1000);
		}};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handlerutil);

		Button start = (Button) findViewById(R.id.start);
		displayTextview = (TextView) findViewById(R.id.text_view);
		
		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handler.post(update_thread);
			}
		});
		
		Button end = (Button) findViewById(R.id.end);
		end.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				handler.removeCallbacks(update_thread);
			}
		});

}
}
