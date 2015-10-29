package com.example.doubanreader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

public class PrepareRequestTokenActivity extends Activity{
	TextView login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();//ͨ��getIntent�������Intent����
		setContentView(R.layout.prepare_request_token_activity);
		login = (TextView)findViewById(R.id.login);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		Log.d("PrepareReqTokenActivity", uri.toString());
		if(uri != null && uri.getScheme().equals("myapp")){
			new RetrieveAccessTokenTask(this, prefs).execute(uri);
			Log.d("RetrieveAccessTokenTask", "----------------");
			finish();
		}
		
	}

	//@Override
	//protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		//super.onNewIntent(intent);
	//	setContentView(R.layout.prepare_request_token_activity);
	//	login = (TextView)findViewById(R.id.login);
	//	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
	//	final Uri uri = intent.getData();
	//	Log.d("PrepareRequestTokenActivity", uri.toString());
	//}

	
	

}
