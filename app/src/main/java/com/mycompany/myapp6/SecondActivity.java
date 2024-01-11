package com.mycompany.myapp6;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import java.io.*;
import android.util.*;
import java.net.*;

public class SecondActivity extends Activity
{
	String s1,lang;
	WebView wv;
	SharedPreferences sp;
	Cache cache;
	ProgressBar pb;
	
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.entry);
		sp = getSharedPreferences("setting",0);
		wv = (WebView) findViewById(R.id.wv);
		pb = (ProgressBar) findViewById(R.id.pb);
		lang = sp.getString("lang","en");
		s1 = getIntent().getExtras().getString("name");
		ActionBar ab = getActionBar();
		ab.setTitle(s1);
		cache = new Cache(this);
		if(cache.exists(s1+".html"))
		wv.loadUrl("file://"+cache.dir+"/"+s1+".html");
		else
		{
		   Fetch f = new Fetch(this,s1,wv,pb);
		   f.execute("https://wikitravel.org/"+lang+"/"+s1);
		}
			

		//wv.loadUrl("https://wikitravel.org/+"+lang+"/"+s1);

	/*
		try
		{
			iv.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(s3)));
			wv.loadUrl("file:///android_asset/"+s2);
		}
		catch (IOException e)
		{
		  Log.e("NotFound",e.toString());
			wv.loadUrl("file:///android_asset/notfound.html");
		}
		
		*/
	}

@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	private void showMessage()
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("About Us ");
        builder.setMessage("Devloped By : Rishav Singh \n App Version : 1.1 \n Company : Creative Glaxy");
        builder.show();
	}
	
	private void lang()
	{
		final CharSequence[] items = { "English", "Hindi"};
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		if(sp.getString("lang","en").equals("en"))
		ad.setTitle("Language : English");
		else
		ad.setTitle("Language : Hindi");
		ad.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(which)
					{
						case 0 : lang = "en"; SharedPreferences.Editor edit = sp.edit();edit.putString("lang","en");edit.commit(); show("English"); break;
						case 1 : lang = "hi"; SharedPreferences.Editor edit2 = sp.edit();edit2.putString("lang","hi");edit2.commit(); show("Hindi"); break;
					}

				}
			});

		ad.show();
	}
	
	private void show(String sh)
	{
		Toast.makeText(getApplicationContext(),sh,Toast.LENGTH_SHORT).show();
	}
	
	 @Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		
		switch(id)
		{
			case R.id.online : wv.loadUrl("https://wikitravel.org/"+lang+"/"+s1);break;//Intent mi1 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://wikitravel.org/en/"+s1));startActivity(mi1); break;
			case R.id.lang : lang(); break;
			case R.id.other : Intent mi2 = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.friended-correspond.000webhostapp.com"));startActivity(mi2); break;
			case R.id.send : showMessage(); break;
			case R.id.exit : finishAffinity(); break;
		}
		
		return super.onOptionsItemSelected(item);
	} 
}
	 
class Fetch extends AsyncTask<String,Void,Void>
{
	
	WebView wv;
	Context c;
	String s1;
	Cache cache;
	ProgressBar pb;
	
	public Fetch(Context c,String s1,WebView wv,ProgressBar pb)
	{
		this.c = c;
		this.s1 = s1;
		this.wv = wv;
		this .pb  = pb;
		cache = new Cache(c);
	}
	
	@Override
	protected Void doInBackground(String[] p1)
	{
		try
		{
			OutputStream os = null;
			InputStream is = new URL(p1[0]).openConnection().getInputStream();
			byte[] buffer = new byte[1024];
			int bufferLength = 0;
			os = new FileOutputStream(new File(cache.cacheDir()+"/"+s1+".html"));//ReadActivity.path+"temp.jpg");
			while((bufferLength = is.read(buffer))>0)
			{
				os.write(buffer,0, bufferLength);
			}
			os.flush();
			os.close();
		}

		catch (Exception e)
		{
			Log.e("CacheFetchErr()",e.toString());

		}
		
		return null;
	}
	
	@Override
	protected void onPreExecute()
	{
		// TODO: Implement this method
		super.onPreExecute();
		pb.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onPostExecute(Void result)
	{
		// TODO: Implement this method
		super.onPostExecute(result);
		pb.setVisibility(View.GONE);
		wv.loadUrl("file://"+cache.dir+"/"+s1+".html");
	}
	
}

class Cache
{
	File dir;
	Context c;
	
	public Cache(Context c)
	{
		this.c = c;
		dir = c.getExternalCacheDir();
		if(!dir.exists())
            dir.mkdirs();
	}
	
	public boolean exists(String s)
	{
		File f = new File(dir,s);
		if(f.exists())
			return true;
		else
			return false;
	}

  public String cacheDir()
	{
		return dir.toString();
	}
	
	
	public void Cache(String s,String d)
	{
		try
		{
			FileOutputStream fo = new FileOutputStream(new File(dir,s));
			fo.write(d.getBytes());
			fo.close();
		}
		catch (Exception e)
		{
			Log.e("SavingCacheErr()",e.toString());
		}
	}
}