package com.example.doubanreader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by zhaojian26 on 15-10-11.
 */
public class ShowBookInfo extends Activity {
    private ImageView bookImage ;
    private TextView author;
    private TextView number;
    private Button comment;
    private EditText commentEdit;
    private TextView authorInfo;
    private TextView catalog;
    private Button back;
    private ListView reviewsList;

    //用于点击EditText以外时隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if(isShouldHidenInput(v,ev)){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null){
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if(getWindow().superDispatchTouchEvent(ev)){
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHidenInput(View v, MotionEvent ev){
        if(v != null && v instanceof EditText){
            int[] leftTop = {0,0};
            //获取输入框当前location位置
            v.getLocationInWindow(leftTop);//获取输入框v在视图中的绝对坐标
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right
                    && ev.getY() > top && ev.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            }else{
            return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_book);
        BookDetail bookDetail = (BookDetail)getIntent().getSerializableExtra("bookData");
        bookImage = (ImageView)findViewById(R.id.book_image);
        author = (TextView)findViewById(R.id.author);
        number = (TextView)findViewById(R.id.number);
        comment = (Button)findViewById(R.id.comment);
        commentEdit = (EditText)findViewById(R.id.comment_edit);
        authorInfo = (TextView)findViewById(R.id.author_info);
        catalog = (TextView)findViewById(R.id.catalog);
        back = (Button)findViewById(R.id.back_button);
        author.setText(bookDetail.getAuthor());
        number.setText(bookDetail.getNumber());
        authorInfo.setText(bookDetail.getAuthorInfo());
        catalog.setText(bookDetail.getCatalog());

        reviewsList = (ListView)findViewById(R.id.book_reviews);



        ImageLoader.getInstance().destroy();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        final String imageViewUrl = bookDetail.getImageViewUrl();
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
        ImageLoader.getInstance().displayImage(imageViewUrl, bookImage, options);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentEdit.getText().length() < 150) {
                    Toast.makeText(ShowBookInfo.this, "评论长度必须大于15个字。", Toast.LENGTH_LONG).show();
                } else {

                }

            }
        });

        catalog.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if (flag) {
                    flag = false;
                    catalog.setEllipsize(null);
                    catalog.setSingleLine(flag);
                } else {
                    flag = true;
                    catalog.setEllipsize(TextUtils.TruncateAt.END);
                }
            }
        });
    }
}
