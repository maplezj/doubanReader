package com.example.doubanreader;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

public class OauthRequestTokenTask extends AsyncTask<Void, Void, Void>{
	public Context context;
	public OauthRequestTokenTask(Context context){
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		 HttpURLConnection connection = null;
         try{
             URL url = new URL(Constant.AUTHURL+Constant.KEY+"&redirect_uri="+Constant.REDIRECTLRL);
             connection = (HttpURLConnection)url.openConnection();
             connection.setRequestMethod("GET");
             Uri uri = Uri.parse(url.toString());
             Intent intent = new Intent(Intent.ACTION_VIEW,uri).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
             context.startActivity(intent);
		// TODO Auto-generated method stub
		 }catch(Exception e){
		e.printStackTrace();
		 }//finally{
			//if(connection != null){
             //   connection.disconnect();
		  //  }
		return null;
	}

}
