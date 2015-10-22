package com.example.doubanreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhaojian26 on 15-10-11.
 */
public class ShowBookInfoUtils extends AsyncTask<Void, Void, BookDetail> {
    String url ;
    String data;
    private BookDetail bookDetail;
    public ShowBookInfoUtils(String url){
        this.url = url;
    }
    @Override
    protected BookDetail doInBackground(Void... params) {
        Log.d("ShowBookInfoUtils","-----------succeed");
        HttpURLConnection connection = null;
        try{
            URL bookUrl = new URL(url);
            connection = (HttpURLConnection)bookUrl.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            data = response.toString();
            Log.d("ShowBookInfoUtils", response.toString());
            bookDetail = new DealBookDetailByJSON().parseJSONWithJSONObject(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return bookDetail;
    }

    @Override
    protected void onPostExecute(BookDetail bookDetail) {
        super.onPostExecute(bookDetail);
        Intent intent = new Intent(SearchBookUtils.context, ShowBookInfo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookData", bookDetail);
        intent.putExtras(bundle);
        SearchBookUtils.context.startActivity(intent);
    }
}
