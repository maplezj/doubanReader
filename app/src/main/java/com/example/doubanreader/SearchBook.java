package com.example.doubanreader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zhaojian26 on 15-10-5.
 */
public class SearchBook extends Activity {
    public String content_get;
    public EditText content;
    public Button searchBookButton;
    public ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_book);
        listView = (ListView) findViewById(R.id.list_view);
        searchBookButton = (Button)findViewById(R.id.search_book);
        content = (EditText)findViewById(R.id.content);
        searchBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击搜索按钮后隐藏软键盘
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null){
                    imm.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
                }
                content_get = content.getText().toString();
                new SearchBookUtils(SearchBook.this,content_get,listView).execute();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
