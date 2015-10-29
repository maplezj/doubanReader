package com.example.doubanreader;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojian26 on 15-10-2.
 */
public class SearchBookUtils extends AsyncTask<Void, Void, List > {
    public static Context context;
    public String content_search;
    public ListView listView;
    public static BookAdapter adapter ;
    List bookList = null;
    public SearchBookUtils(Context context, String content_search, ListView listView){
        this.context = context;
        this.content_search = content_search;
        this.listView = listView;
    }

    @Override
    protected List doInBackground(Void... params) {
        HttpURLConnection connection = null;
        try{
            URL url = new URL(Constant.BOOK_SEARCH_URL+content_search+"&fields=id,title,url,image,subtitle,rating,author,summary");
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
            bookList = (new DellWithJSON()).parseJSONWithJSONObject(response.toString());



        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(connection != null){
                connection.disconnect();
            }
        }

        return bookList;
    }

    @Override
    protected void onPostExecute(final List bookList) {
       // super.onPostExecute(bookList);
       // Log.d("SearchBookUtils",bookList.toString());
        //Log.d("SearchBookUtils","-----------------1");
        adapter = new BookAdapter(context, R.layout.book_item, bookList);
        RefreshableView.status = 1;
        //Log.d("SearchBookUtils","-----------------2");
        listView.setAdapter(adapter);
        //为listView设置监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookData bookData = (BookData)bookList.get(position);
                new ShowBookInfoUtils(bookData.getUrl()).execute();
            }
        });
        //Log.d("SearchBookUtils", "-----------------3");
    }
}
