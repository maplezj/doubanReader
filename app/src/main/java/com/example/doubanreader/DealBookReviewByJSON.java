package com.example.doubanreader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojian26 on 15/10/31.
 */
public class DealBookReviewByJSON {
    private int i;
    private String reviewStar;
    public List<BookReviewData> bookReviewDataList = new ArrayList<BookReviewData>();
    public List  parseJSONWithJSONObject(String jsonData){
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject1.getJSONArray("reviews");
            for( i = 0; i<jsonArray.length();i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                //经测试发现json数组中有时候没有rating项，故对其进行try catch处理
                try {
                    JSONObject rating = jsonObject2.getJSONObject("rating");
                    reviewStar = rating.getString("value");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reviewStar.length()==0){
                        reviewStar = "0";
                    }
                }
                JSONObject author = jsonObject2.getJSONObject("author");
                String reviewAuthor = author.getString("name");
                String reviewSupport = jsonObject2.getString("votes");
                String reviewComment = jsonObject2.getString("comments");
                String reviewTime = jsonObject2.getString("published");
                String reviewContent = jsonObject2.getString("summary");
                Log.d("DealBookReviewByJSON", "reviewAuthor|" + reviewAuthor + "reviewComment|" + reviewComment + "reviewStar|" + reviewStar + "reviewSupport|" + reviewSupport + "reviewTime|" + reviewTime);
                bookReviewDataList.add(new BookReviewData(reviewStar, reviewAuthor, reviewTime, reviewSupport, reviewComment, reviewContent));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookReviewDataList;
    }
}
