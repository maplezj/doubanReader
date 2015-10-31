package com.example.doubanreader;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by zhaojian26 on 15/10/27.
 */
public class RefreshBookItem implements RefreshableView.PullToRefreshListener {
    private RefreshableView refreshableView;
    private Context context;
    private String content;
    public List bookList;
    public RefreshBookItem(RefreshableView refreshableView, Context context, String content){
        this.refreshableView = refreshableView;
        this.context = context;
        this.content = content;
    }
    @Override
    public List onRefresh() {
        HttpURLConnection connection = null;
        try{
            URL url = new URL(Constant.BOOK_SEARCH_URL+content+"&fields=id,title,url,image,subtitle,rating,author,summary");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            //设置请求头信息
            connection.setRequestProperty("Authorization", "Bearer " + RetrieveAccessTokenTask.access_token);
            //Uri uri = Uri.parse(url.toString());
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            Log.d("SearchBookUtils", response.toString());
            bookList = (new DealBookDataByJSON()).parseJSONWithJSONObject(response.toString());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(connection != null){
                connection.disconnect();
            }
        }
        Log.d("RefreshBookItem","-----------------刷新成功");
        refreshableView.finishRefreshing();
        return bookList;
    }
}
