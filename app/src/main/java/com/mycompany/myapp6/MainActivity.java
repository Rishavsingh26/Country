package com.mycompany.myapp6;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.content.*;
import java.util.*;
import java.io.*;
import android.content.res.*;

public class MainActivity extends Activity 
{
	ListView lv;
	ArrayList<String> l;
	ArrayAdapter ar;
	BufferedReader br;
	String k;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		lv = (ListView)findViewById(R.id.lv);
		
		l = new ArrayList<String>();
		AssetManager am= getAssets();
		try
		{
			br = new BufferedReader(new InputStreamReader(am.open("Country.html")));
			 k = br.readLine();
			while(k!=null)
			{
				l.add(k);
				k = br.readLine();
			}
		}
		catch (IOException e)
		{
			
		}
		
		Collections.sort(l);
		ar = new ArrayAdapter(this, R.layout.custom, R.id.mv, l);
		
		lv.setAdapter(ar);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent,View v,int position,long id){

					Intent i = new Intent(getApplicationContext(),SecondActivity.class);
					i.putExtra("name",parent.getItemAtPosition(position).toString());
					startActivity(i);
					//Toast.makeText(getApplicationContext(),"Opening  "+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
				}
			});
	}

	
	
	}