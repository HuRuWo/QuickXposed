package com.huruwo.hposed.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.huruwo.hposed.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView  save_btn;

    private EditText ip_tv , port_tv;

    private SharedPreferences ipport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina_test);
        ipport = getSharedPreferences("ipport",
                Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editorStart = ipport.edit();
        editorStart.putBoolean("isStart", false);
        initView();
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save_btn:
                String host = ip_tv.getText().toString();
                Integer port = Integer.valueOf(port_tv.getText().toString());
                SharedPreferences.Editor editor = ipport.edit();
                editor.putString("host", host);
                editor.putString("port", ""+port);
                boolean commit = editor.commit();
                if(commit) {
                    ToastUtils.showLong("修改成功", Toast.LENGTH_LONG);
                }
                break;
        }
    }

    private void initView() {


        ip_tv = (EditText) this.findViewById(R.id.ip_tv);
        port_tv = (EditText) this.findViewById(R.id.port_tv);

        save_btn = (TextView) this.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        ip_tv.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        port_tv.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        String host = ipport.getString("host","0.0.0.0");
        String port = ipport.getString("port","9228");
        ip_tv.setText(host);
        port_tv.setText(port);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editorStart = ipport.edit();
        editorStart.putBoolean("isStart", false);
        editorStart.commit();
    }

}
