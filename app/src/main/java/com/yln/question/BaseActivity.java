package com.yln.question;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by linnan.yao on 2017/10/13.
 */

public class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//            getWindow().getDecorView().setBackground(new ColorDrawable(Color.RED));
//            getWindow().getDecorView().setPadding(0, Util.dip2px(getApplicationContext(), 25), 0, 0);
        }
//        PushAgent.getInstance(getApplicationContext()).onAppStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
