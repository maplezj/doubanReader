package com.example.doubanreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhaojian26 on 15/10/31.
 */
public class BookReviewAdapter extends ArrayAdapter<BookReviewData> {
    private int resoureId;
   public BookReviewAdapter(Context context, int textViewResourceId, List<BookReviewData> objects){
       super(context, textViewResourceId, objects);
       resoureId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookReviewData bookReviewData = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resoureId, null);
        RatingBar reviewStar = (RatingBar)view.findViewById(R.id.review_star);
        TextView reviewAuthor = (TextView)view.findViewById(R.id.review_author);
        TextView reviewTime = (TextView)view.findViewById(R.id.review_time);
        TextView reviewSupport = (TextView)view.findViewById(R.id.review_support);
        TextView reviewComment = (TextView)view.findViewById(R.id.review_comment);
        TextView reviewContent = (TextView)view.findViewById(R.id.review_content);

        reviewStar.setRating(Float.parseFloat(bookReviewData.getReviewStar()));
        reviewTime.setText(bookReviewData.getReviewTime());
        reviewAuthor.setText(bookReviewData.getReviewAuthor());
        reviewSupport.setText(bookReviewData.getReviewSupport());
        reviewComment.setText(bookReviewData.getReviewComment());
        reviewContent.setText(bookReviewData.getReviewContent());

        return view;
    }
}
