package com.example.doubanreader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojian26 on 15-10-9.
 */
public class DealBookDataByJSON {
    private int i;
    public List<BookData> bookDataList = new ArrayList<BookData>();

    public List parseJSONWithJSONObject(String jsonData){
        try {
            JSONObject jsonObjectMain = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObjectMain.getJSONArray("books");
            for(i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject rating = jsonObject.getJSONObject("rating");
                String id = jsonObject.getString("id");
                String url = jsonObject.getString("url");
                String title = jsonObject.getString("title");
                String image = jsonObject.getString("image");
                String subTitle = jsonObject.getString("subtitle");
                String itemAuthor = jsonObject.getString("author");
                String itemNumber = rating.getString("average");
                String itemPeople = rating.getString("numRaters");
                String summary = jsonObject.getString("summary");
                //String count = jsonObject.getString("count");
                //String start = jsonObject.getString("start");
                //String total = jsonObject.getString("total");
                //String books = jsonObject.getString("books");
                Log.d("DealBookDataByJSON","id is "+ id);
                Log.d("DealBookDataByJSON","url is "+ url);
                Log.d("DealBookDataByJSON","title is "+ title);
                Log.d("DealBookDataByJSON","image is "+ image);
                Log.d("DealBookDataByJSON","subtitle is "+ subTitle);
                bookDataList.add(new BookData(id, url, title, image, subTitle, itemAuthor, itemNumber, itemPeople, summary));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookDataList;
    }
}
