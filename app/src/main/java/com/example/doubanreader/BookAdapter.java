package com.example.doubanreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by zhaojian26 on 15-10-9.
 */
public class BookAdapter extends ArrayAdapter<BookData>{
    private int resoureId;
    private List bookList;
    public BookAdapter(Context context, int textViewResourceId, List<BookData> objects){
        super(context,textViewResourceId,objects);
        //Log.d("BookAdapter", "-----------------1");
        resoureId = textViewResourceId;
        bookList = objects;
    }

    public void refresh(List list) {
        bookList = list;
        notifyDataSetChanged();

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d("BookAdapter", "-----------------2");
        final BookData bookData = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resoureId, null);
        TextView titleView = (TextView)view.findViewById(R.id.title_id);
        TextView subTitleView = (TextView)view.findViewById(R.id.sub_title_id);
        TextView itemAuthor = (TextView)view.findViewById(R.id.item_author);
        TextView itemNumber = (TextView)view.findViewById(R.id.item_number);
        TextView summary = (TextView)view.findViewById(R.id.item_introduce);
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        titleView.setText(bookData.getTitle());
        subTitleView.setText(bookData.getSubTitle());
        itemAuthor.setText(bookData.getItemAuthor());
        ratingBar.setRating((Float.parseFloat(bookData.getItemNumber()))/2);
        itemNumber.setText(" "+bookData.getItemNumber() + "|"+bookData.getItemPeople() + "人评价");
        summary.setText(bookData.getSummary());

        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(getContext());
        ImageLoader.getInstance().init(configuration);
        final ImageView mImageView = (ImageView)view.findViewById(R.id.image_view_id);
        final String imageUrl = bookData.getImage();

        /*mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BookAdapter","------------Listener succeed");
                new ShowBookInfoUtils(bookData.getUrl()).execute();
            }
        });*/

       // ImageSize mImageSize = new ImageSize(100, 100);
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);


        return view;
    }
}
