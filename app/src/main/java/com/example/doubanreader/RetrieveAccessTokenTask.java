package com.example.doubanreader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void>{
    public Context context;
    public SharedPreferences prefs;
    private String code;
    public  Data data; 
    public static String access_token;
	public RetrieveAccessTokenTask(Context context, SharedPreferences prefs){
			this.context = context;
			this.prefs = prefs;
			Log.d("RetrieveAccessTokenTask", "----------------1");
		}

	@Override
	protected Void doInBackground(Uri... arg0) {
		// TODO Auto-generated method stub 
		Log.d("RetrieveAccessTokenTask", "----------------2");
		final Uri uri = arg0[0];
		Log.d("RetrieveAccessTokenTask", uri.toString());
		final String authorization_code = uri.getQueryParameter("code");
		Log.d("RetrieveAccessTokenTask", authorization_code);
		HttpResponse httpResponse;
		String result;
        try{
		    HttpPost httpPost = new HttpPost(Constant.ACCESS_TOKEN_URL);
		    List<NameValuePair> params = new ArrayList<NameValuePair>();  
		    params.add(new BasicNameValuePair("client_id", Constant.KEY));
		    params.add(new BasicNameValuePair("client_secret", Constant.SECRET));
		    params.add(new BasicNameValuePair("redirect_uri", "http://www.baidu.com"));
		    params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		    params.add(new BasicNameValuePair("code", authorization_code));
		    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
		    httpResponse = new DefaultHttpClient().execute(httpPost);
			Log.d("RetrieveAccessTokenTask", "----------------3");
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d("RetrieveAccessTokenTask",statusCode+"");
		    if (httpResponse.getStatusLine().getStatusCode() == 200)
            {  
                result = EntityUtils.toString(httpResponse.getEntity());    
                Log.d("RetrieveAccessTokenTask",result);
                parseJSONWithGSON(result);
				Log.d("RetrAccessTT",data.getAccessToken());
            }  

             final Editor edit = prefs.edit();
             edit.putString(access_token, data.getAccessToken());
             edit.commit();
             access_token = prefs.getString(access_token, "");


		// TODO Auto-generated method stub
	}catch(Exception e){
		e.printStackTrace();
		}//finally{
			//if(connection != null){
             //   connection.disconnect();
		  //  }
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		Intent intent = new Intent(context,SearchBook.class);
		context.startActivity(intent);
	}

	private void parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
       data = gson.fromJson(jsonData,Data.class);
    }
}
