package com.mycompany.myapp6;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class SplashActivity extends Activity
{
	int SPLASH_DISPLAY_LENGTH = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wall);
		
			ActionBar ab = getActionBar();
		ab.hide();
		
		new Handler().postDelayed(new Runnable(){
			@ Override public void run(){/* Create an Intent that will start the Menu-Activity. */
			Intent mainIntent =new Intent(SplashActivity.this,MainActivity.class);
			SplashActivity.this.startActivity(mainIntent);
			SplashActivity.this.finish();
				
			}}, SPLASH_DISPLAY_LENGTH);
		
		}
	
}
