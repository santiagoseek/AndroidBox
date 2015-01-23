package com.example.ui;

import com.example.androidbox.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class SomeDialog extends Activity {
	
	private Context context;
	private int dialogValue;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.somedialog);
        context = this;
        
        Button baseAlert = (Button)findViewById(R.id.baseAlert);
        baseAlert.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v){
        		Dialog alertDialog = new AlertDialog.Builder(context)
        		.setTitle("Title")
        		.setMessage("message")
        		.setIcon(R.drawable.ic_launcher)
        		.create();
        		alertDialog.show();
        	}
        });
        
        Button buttonAlert = (Button)findViewById(R.id.buttonAlert);
        buttonAlert.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Dialog alertDialog = new AlertDialog.Builder(context).setTitle("Title").setMessage("message").setIcon(R.drawable.ic_launcher)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								
							}
						})
						.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								
							}
						}).create();
						alertDialog.show();
			}
        	
        });
        
        Button listAlert = (Button)findViewById(R.id.listAlert);
        listAlert.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String[] arrayFruit = new String[] {"Apple","Oriage","branana","pear"};
				Dialog alertDialog = new AlertDialog.Builder(context)
				.setTitle("which fruit do you like?")
				.setIcon(R.drawable.ic_launcher)
				.setItems(arrayFruit, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(context, arrayFruit[arg1], Toast.LENGTH_LONG).show();
					}
				})
				.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				})
				.create();
				alertDialog.show();
			}
        	
        });
        
        Button radioAlert = (Button)findViewById(R.id.radioAlert);
        radioAlert.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialogValue = 0;
				final String[] arrayFruit = new String[] {"Apple","Oriage","branana","pear"};
				Dialog alertDialog = new AlertDialog.Builder(context)
				.setTitle("which fruit do you like?")
				.setIcon(R.drawable.ic_launcher)
				.setSingleChoiceItems(arrayFruit, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						dialogValue = arg1;
					}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(context, arrayFruit[dialogValue], Toast.LENGTH_LONG).show();
					}
				})
				.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				}).create();
				alertDialog.show();
			}
        });
        
        Button checkAlert = (Button)findViewById(R.id.checkAlert);
        checkAlert.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String[] arrayFruit = new String[] {"Apple","Oriage","branana","pear"};
				final boolean[] arrayFruitSelected = new boolean[] {true,true,false,false};
				Dialog alertDialog = new AlertDialog.Builder(context)
				.setTitle("which fruit do you like?")
				.setIcon(R.drawable.ic_launcher)
				.setMultiChoiceItems(arrayFruit, arrayFruitSelected, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
						// TODO Auto-generated method stub
						arrayFruitSelected[arg1] = arg2;
					}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						StringBuilder stringBuilder = new StringBuilder();
						for(int i = 0; i<arrayFruitSelected.length;i++)
						{
							if(arrayFruitSelected[i] == true)
							{
								stringBuilder.append(arrayFruit[i] + ",");
							}
						}
						Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_LONG).show();
					}
				})
				.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				})
				.create();
				alertDialog.show();
			}
        	
        });
        
        Button loginAlert = (Button)findViewById(R.id.loginAlert);
        loginAlert.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				View loginView = layoutInflater.inflate(R.layout.login, null);
				Dialog alertDialog = new AlertDialog.Builder(context)
				.setTitle("LogIn")
				.setIcon(R.drawable.ic_launcher)
				.setView(loginView)
				.setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				})
				.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				}).create();
				alertDialog.show();
			}
        	
        });
    }

}
