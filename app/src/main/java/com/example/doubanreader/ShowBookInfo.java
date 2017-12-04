package com.example.doubanreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by zhaojian26 on 15-10-11.
 */
public class ShowBookInfo extends Activity implements View.OnClickListener{
    private ImageView bookImage ;
    private TextView author;
    private TextView number;
    private Button comment;
    private EditText commentEdit;
    private TextView authorInfo;
    private TextView catalog;
    private Button back;
    private ListView reviewsList;
    private List bookReviewDataList = ShowBookInfoUtils.bookReviewDataList;
    private Button showMore;
    public static int reviewNumber = 20;
    String data;
    public final static int FINISH = 1;
    BookReviewAdapter adapter;
    public  ProgressDialog showBookReviewDialog;

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

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FINISH:
                    //此处由于我们复写了listview的绘制方法，因此每次刷新时由于其尺寸发生变化，需重新绘制(故我猜测list.setAdapter（）源码里应该对list进行了绘制，Listview充当viewgroup，adapter里的数据充当view，不知对否)
                    adapter = new BookReviewAdapter(ShowBookInfo.this, R.layout.book_review_item,bookReviewDataList);
                    reviewsList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    showBookReviewDialog.dismiss();
                    break;
                default:
            }
        }
    };

    @Override
    public void onClick(View v) {
        showBookReviewDialog = new ProgressDialog(this);
        showBookReviewDialog.setCancelable(true);
        showBookReviewDialog.setMessage("加载中，请稍候...");
        showBookReviewDialog.show();
        //点击加载更多后重新请求数据，并且数据量每次加10条，从而实现加载更多功能
        reviewNumber=reviewNumber+10;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL bookReviewUrl = new URL(Constant.BOOK_REVIEW_URL+ShowBookInfoUtils.id+"/reviews?count="+reviewNumber);
                    connection = (HttpURLConnection)bookReviewUrl.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    data = response.toString();
                    bookReviewDataList = new DealBookReviewByJSON().parseJSONWithJSONObject(data);
                    Log.d("ShowBookInfo", data);
                    Message message = new Message();
                    message.what = FINISH;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.show_book);
        SearchBookUtils.showBookDialog.dismiss();
        BookDetail bookDetail = (BookDetail)getIntent().getSerializableExtra("bookDetail");

        bookImage = (ImageView)findViewById(R.id.book_image);
        author = (TextView)findViewById(R.id.author);
        number = (TextView)findViewById(R.id.number);
        comment = (Button)findViewById(R.id.comment);
        commentEdit = (EditText)findViewById(R.id.comment_edit);
        authorInfo = (TextView)findViewById(R.id.author_info);
        catalog = (TextView)findViewById(R.id.catalog);
        back = (Button)findViewById(R.id.back_button);
        showMore = (Button)findViewById(R.id.show_more);

        author.setText(bookDetail.getAuthor());
        number.setText(bookDetail.getNumber());
        authorInfo.setText(bookDetail.getAuthorInfo());
        catalog.setText(bookDetail.getCatalog());

        reviewsList = (ListViewForScrollView)findViewById(R.id.book_reviews);
          adapter = new BookReviewAdapter(ShowBookInfo.this, R.layout.book_review_item,bookReviewDataList);
        reviewsList.setAdapter(adapter);


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

        showMore.setOnClickListener(this);




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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //退出页面后清除reviewNumber的累加
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            reviewNumber = 20;
            Log.d("ShowBookInfo", reviewNumber+"");
            Log.d("git test", "onKeyDown: test");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
