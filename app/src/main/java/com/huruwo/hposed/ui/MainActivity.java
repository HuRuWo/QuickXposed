package com.huruwo.hposed.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huruwo.hposed.R;


public class MainActivity extends AppCompatActivity {

    private TextView save_btn;
    private EditText ip_tv, port_tv;
    private SharedPreferences ipport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, SettingActivity.class));
                MainActivity.this.onTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN,
                        100.0f,
                        100.0f,
                        0));


                MainActivity.this.onTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_MOVE,
                        150.0f,
                        150.0f,
                        0));

                MainActivity.this.onTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis() ,
                        MotionEvent.ACTION_UP,
                        200.0f,
                        200.0f,
                        0));
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e("opop","事件类型:"+event.getAction()+"x:"+event.getRawX()+"y:"+event.getRawY());


        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editorStart = ipport.edit();
        editorStart.putBoolean("isStart", false);
        editorStart.commit();
    }

}
