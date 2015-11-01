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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by zhaojian26 on 15-10-11.
 */
public class ShowBookInfoUtils extends AsyncTask<Void, Void, BookDetail> {
    String url ;
    String data;
    public static String id;
    String reviewData;
    private BookDetail bookDetail;
    public static List bookReviewDataList;
    public ShowBookInfoUtils(String url, String id){
        this.url = url;
        this.id = id;
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

            URL bookReviewUrl = new URL(Constant.BOOK_REVIEW_URL+id+"/reviews?count=20");
            connection = (HttpURLConnection)bookReviewUrl.openConnection();
            connection.setRequestMethod("GET");
            InputStream reviewIn = connection.getInputStream();
            BufferedReader reviewReader = new BufferedReader(new InputStreamReader(reviewIn));
            StringBuilder reviewResponse = new StringBuilder();
            String reviewLine;
            while ((reviewLine = reviewReader.readLine()) != null){
                reviewResponse.append(reviewLine);
            }

            reviewData = reviewResponse.toString();
            data = response.toString();
            Log.d("ShowBookInfoUtils", data);
            Log.d("ShowBookInfoUtils", reviewData);
            bookDetail = new DealBookDetailByJSON().parseJSONWithJSONObject(data);
            bookReviewDataList = new DealBookReviewByJSON().parseJSONWithJSONObject(reviewData);
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
        bundle.putSerializable("bookDetail", bookDetail);
        intent.putExtras(bundle);
        SearchBookUtils.showBookDialog.dismiss();
        SearchBookUtils.context.startActivity(intent);
    }
}
