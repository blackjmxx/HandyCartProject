package com.m2.handycart;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.m2.handycart.service.BluetoothService;



public class MainActivity extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Resources ressources = getResources(); 

		
		
		 TextView t = new TextView(this); 

		    t=(TextView)findViewById(R.id.textview0); 
		    
		    t.setText("Step One: blast egg");

		TabHost tabHost = getTabHost(); 
		
		// Android tab
		Intent intentAndroid = new Intent().setClass(this, NavigationActivity.class);
		TabSpec tabSpecAndroid = tabHost
			.newTabSpec("Android")
			.setIndicator("Navigation")
			.setContent(intentAndroid);

		// Apple tab
		Intent intentApple = new Intent().setClass(this, SearchActivity.class);
		TabSpec tabSpecApple = tabHost
			.newTabSpec("Apple")
			.setIndicator("Recherche de produit")
			.setContent(intentApple);
		
		// Windows tab
		Intent intentWindows = new Intent().setClass(this, WaitScanActivity.class);
		TabSpec tabSpecWindows = tabHost
			.newTabSpec("Windows")
			.setIndicator("Assistance")
			.setContent(intentWindows);

		// add all tabs 
		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecApple);
		tabHost.addTab(tabSpecWindows);

		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
		
		Intent bluetoothService = new Intent().setClass(this, BluetoothService.class);
		Log.d("MainActivity", "Starting");
		startService(bluetoothService);
		Log.d("MainActivity", "Started");
	}


}