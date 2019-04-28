package com.huruwo.xposed.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.huruwo.xposed.R;
import com.huruwo.xposed.bean.XposedBean;

import java.util.Random;


public class MainActivity extends Activity {

    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        XposedBean xposedBean = new XposedBean();
        xposedBean.setData("数据");


    }




}
