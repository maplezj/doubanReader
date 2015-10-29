package com.example.doubanreader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.view.Window;

/**
 * Created by zhaojian26 on 15/10/23.
 */
public class HomePagePicture extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_page);
    }
}
