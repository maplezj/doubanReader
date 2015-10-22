package com.example.doubanreader;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by zhaojian26 on 15-10-11.
 */
public class DealBookDetailByJSON {
    private BookDetail bookDetail;
    private String author;
    private String number;
    private String authorInfo;
    private String catalog;
    private String imageViewUrl;
    public BookDetail  parseJSONWithJSONObject(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject subJsonObject = jsonObject.getJSONObject("rating");
            author = jsonObject.getString("author");
            number = subJsonObject.getString("average");
            authorInfo = jsonObject.getString("author_intro");
            catalog = jsonObject.getString("catalog");
            JSONObject subsubJsonObject = jsonObject.getJSONObject("images");
            imageViewUrl = subsubJsonObject.getString("large");
            Log.d("DealBookByJSON",author+number+authorInfo+catalog+imageViewUrl);
            bookDetail = new BookDetail(imageViewUrl, author, number, authorInfo, catalog);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookDetail;
    }
}
