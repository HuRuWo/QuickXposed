package com.huruwo.hposed.kshook;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huruwo.hposed.mina.MinaHookService;
import com.huruwo.hposed.utils.LogXUtils;

import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.view.View.VISIBLE;
import static android.widget.LinearLayout.VERTICAL;


/**
 * @author
 * @date 2019/11/18 0018
 * @action
 **/
public class KSSekiroMainUi {

    private boolean isResit = false;

    private static Handler KSHandler;

    private ClassLoader classLoader;

    public KSSekiroMainUi(ClassLoader classLoader) {
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

                //        Map<String, String> a2 = new C37148d(context, i).mo69708a(z, mVar);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                    }
                }).start();



            }
        });

        KSHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };

        final ClassLoader cl = activity.getClassLoader();
        //从客户端配中读取 远程服务socket服务器的ip和端口
        XSharedPreferences ipport = new XSharedPreferences("com.huruwo.hposed", "ipport");
        synchronized (this){
            ipport.makeWorldReadable();
            String host = ipport.getString("host", "0.0.0.0");
            Integer port = Integer.valueOf(ipport.getString("port", "9228"));
            LogXUtils.e(" host 和端口是" + host + ":"+ port);
            Map<String, Class<?>> signMap = new HashMap<>();

            Class<?> kwaiAppObject = cl.loadClass("com.yxcorp.gifshow.KwaiApp");
            Class<?> cqObject = cl.loadClass("com.yxcorp.gifshow.util.cq");
            Class<?> abfObject = cl.loadClass("com.kuaishou.dfp.a.b.f");
            Class<?> messageNanoObject = cl.loadClass("com.google.protobuf.nano.MessageNano");
            Class<?> ksdkInnerObject = cl.loadClass("com.kuaishou.android.security.KSdkInner");
            Class<?> dfpaaaObject = cl.loadClass("com.kuaishou.dfp.a.a.a");


            signMap.put("kwaiAppObject",kwaiAppObject);
            signMap.put("cqObject",cqObject);
            signMap.put("abfObject",abfObject);
            signMap.put("messageNanoObject",messageNanoObject);
            signMap.put("ksdkInnerObject",ksdkInnerObject);
            signMap.put("dfpaaaObject",dfpaaaObject);


            MinaHookService minaHookService = new MinaHookService();
            minaHookService.onCreate(activity,  host,  port, cl , signMap);
            LogXUtils.e("mina hookService signObject 启动成功");
        }
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
