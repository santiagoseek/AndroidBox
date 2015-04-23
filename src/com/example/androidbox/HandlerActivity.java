package com.example.androidbox;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HandlerActivity extends Activity {
	Handler handler = new Handler();
	
	private TextView displayTextview = null;
	private ImageView imageView;
	private Button downloadButton;
	
	private ProgressDialog progressDialog;
	private String downLoadPath = "http://pic.baomihua.com/photos/201110/m_6_634545730007187500_16585344.jpg";
	
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
		imageView = (ImageView) findViewById(R.id.imageView);
		downloadButton = (Button) findViewById(R.id.bt_download);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Current Task");
		progressDialog.setMessage("DownLoading...");
		
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
		
		downloadButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progressDialog.show();
				new Thread(new downHandlerThread()).start();
			}
			
		});
		}
	
	Handler downHandler = new Handler(){
		public void handleMessage(Message msg){
			byte[] data = (byte[]) msg.obj;
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			progressDialog.dismiss();
			imageView.setImageBitmap(bitmap);
		};
	};
	
	public class downHandlerThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(downLoadPath);
			HttpResponse httpResponse;
			try {
				httpResponse = httpclient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					byte[] data = EntityUtils.toByteArray(entity);

					Message message = downHandler.obtainMessage();
					message.obj = data;
					downHandler.sendMessage(message);
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}


