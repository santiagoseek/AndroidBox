package com.example.androidbox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.systemutil.EmulatorCheck;
import com.example.systemutil.RootCheck;
import com.example.systemutil.SystemInfo;
import com.example.systemutil.TestLogFile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SystemUtilActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.systemutil);
        
        Button getBt = (Button)findViewById(R.id.getBt);
        getBt.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){
        		
        		TextView getTv = (TextView)findViewById(R.id.displayTv);
        		
        		String root = "isRoot: " + String.valueOf(RootCheck.isRoot());
        		String emulator = "isEmulator: " + String.valueOf(EmulatorCheck.isQEmuEnvDetected(getApplicationContext()));
        		String baseApplication = "BaseApplication value:" + ((BaseApplication)getApplication()).getValue();
        		SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss:SS");
				String currentTime = format.format(new Date(System
						.currentTimeMillis()));

				String result = root + "\n" + emulator + "\n" + baseApplication
						+ "\n" + currentTime;

				((BaseApplication) getApplication())
						.setValue("SystemUtilActivity update");

 
				StringBuilder sb = new StringBuilder();
				
				
				Map<String,Object>systemInfo = new HashMap<String,Object>();
				systemInfo.putAll(SystemInfo.getSystemInfo(getApplicationContext()));
				systemInfo.put("root", RootCheck.isRoot());
				systemInfo.put("emu", EmulatorCheck.isQEmuEnvDetected(getApplicationContext()));
				
				 Iterator it = systemInfo.entrySet().iterator();
				   while (it.hasNext()) {
				    Map.Entry entry = (Map.Entry) it.next();
				    String key = entry.getKey().toString();
				    String value = entry.getValue().toString();
				    sb.append(key + ":" + value + "\r\n");
				   }
				
				
				getTv.setText(result + "\r\n" + sb.toString());
				
				
				String splitString = "$$$$$";
				TestLogFile.writeToSDCardFile("AAAATest", "testLogFile.txt", currentTime + splitString +"this is test \r\n" + sb.toString(), true);
        	}
        });
    }

}
