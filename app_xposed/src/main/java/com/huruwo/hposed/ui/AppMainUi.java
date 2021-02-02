package com.huruwo.hposed.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huruwo.hposed.utils.LogXUtils;
import com.virjar.sekiro.api.SekiroClient;
import com.virjar.sekiro.api.SekiroRequest;
import com.virjar.sekiro.api.SekiroRequestHandler;
import com.virjar.sekiro.api.SekiroResponse;

import de.robv.android.xposed.XSharedPreferences;

import static android.widget.LinearLayout.VERTICAL;


/**
 * @author
 * @date 2019/11/18 0018
 * @action
 **/
public class AppMainUi {

    private boolean isResit = false;

    private static Handler handler;

    private ClassLoader classLoader;

    public AppMainUi(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void UI(final Activity activity) throws ClassNotFoundException {

        FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView().getRootView();
        frameLayout.setBackgroundColor(Color.RED);
        LinearLayout linearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams ll_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setLayoutParams(ll_layoutParams);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(4, 10, 4, 4);

        TextView topText = getTopText(activity);
        linearLayout.addView(topText);
        frameLayout.addView(linearLayout);

        topText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        XSharedPreferences ipport = new XSharedPreferences("com.huruwo.hposed", "ipport");
                        synchronized (this) {
                            ipport.makeWorldReadable();
                            String host = ipport.getString("host", "0.0.0.0");
                            Integer port = Integer.valueOf(ipport.getString("port", "5600"));
                            String client = ipport.getString("clientid", Settings.Secure.getString(activity.getContentResolver(), "android_id"));
                            SekiroClient sekiroClient = SekiroClient.start(host, port, client);
                            sekiroClient.registerHandler("test", new SekiroRequestHandler() {
                                @Override
                                public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
                                    sekiroResponse.success("测试测试");
                                }
                            });
                            LogXUtils.e(" host 和端口是" + host + ":" + port);
                        }


                    }
                }).start();


            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };


    }

    private TextView getTopText(Activity activity) {
        TextView imageView = new TextView(activity);
        imageView.setBackgroundColor(Color.WHITE);
        imageView.setGravity(Gravity.CENTER);
        imageView.setText("显示数据界面");
        LinearLayout.LayoutParams im_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        im_layoutParams.setMargins(4, 80, 4, 4);
        imageView.setLayoutParams(im_layoutParams);
        return imageView;
    }

}
