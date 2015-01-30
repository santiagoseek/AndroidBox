package com.example.androidbox;

import com.example.systemutil.EmulatorCheck;
import com.example.systemutil.RootCheck;

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
        		String result = root + "\n" + emulator + "\n" + baseApplication;	
        		
        		((BaseApplication)getApplication()).setValue("SystemUtilActivity update");
        		getTv.setText(result);
        	}
        });
    }

}
