package com.huruwo.hposed.app;

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

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;


import static android.view.View.VISIBLE;
import static android.widget.LinearLayout.VERTICAL;


/**
 * @author
 * @date 2019/11/18 0018
 * @action
 **/
public class KSSekiroMainUi {

    private boolean isResit = false;


    public void UI(Activity activity, ClassLoader classLoader) {

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

            }
        });
    }



    private TextView getTopText(Activity activity){
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
