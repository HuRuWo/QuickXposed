package com.huruwo.hposed.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huruwo.hposed.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences ipport;

    private TextView save_btn;

    private EditText ip_tv, port_tv, ed_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina_test);
        ipport = getSharedPreferences("ipport",
                Context.MODE_WORLD_READABLE);
        initView();
    }


    private void initView() {

        ip_tv = (EditText) findViewById(R.id.ed_host);
        port_tv = (EditText) findViewById(R.id.ed_port);
        ed_client = (EditText) findViewById(R.id.ed_client);

        save_btn = (TextView) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(this);

        ip_tv.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        port_tv.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        String host = ipport.getString("host", "0.0.0.0");
        String port = ipport.getString("port", "9228");
        String client = ipport.getString("clientid", Settings.Secure.getString(getContentResolver(), "android_id"));
        ip_tv.setText(host);
        port_tv.setText(port);
        ed_client.setText(client);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                Log.i("douyin", "输入了ip和port");
                String host = ip_tv.getText().toString();
                Integer port = Integer.valueOf(port_tv.getText().toString());
                String client = ed_client.getText().toString();
                SharedPreferences.Editor editor = ipport.edit();
                editor.putString("host", host);
                editor.putString("port", "" + port);
                editor.putString("clientid", client);
                boolean commit = editor.commit();
                if (commit) {
                    Toast.makeText(SettingActivity.this.getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




}
