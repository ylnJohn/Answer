package com.yln.question;

import android.app.Application;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import net.youmi.android.nm.sp.SpotManager;
import net.youmi.android.nm.sp.SpotRequestListener;

/**
 * Created by linnan.yao on 2017/10/16.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FeedbackAPI.init(this,"24656811","7bb65b9d8de8c23f66c28f0c3e0b3beb");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        SpotManager.getInstance(this).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {

            }

            @Override
            public void onRequestFailed(int i) {

            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SpotManager.getInstance(this).onAppExit();
    }
}