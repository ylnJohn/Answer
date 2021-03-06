package com.yln.question;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.yln.question.util.Random;
import com.yln.question.util.Util;

/**
 * Created by linnan.yao on 2017/10/13.
 */

public class StartActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mGameIV,mSettingsIV;
    private Context mContext;
    private AnimatorSet set=new AnimatorSet();
    private Random random=new Random();
//    private PermissionHelper mPermissionHelper;
    private InterstitialAd ad;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mGameIV= (ImageView) findViewById(R.id.start_game);
        mSettingsIV= (ImageView) findViewById(R.id.start_settings);
        mGameIV.setOnClickListener(this);
        mSettingsIV.setOnClickListener(this);
        mContext=this;
        MobileAds.initialize(getApplicationContext(),SystemConfig.AD_ID);
        ad=new InterstitialAd(mContext);
        ad.setAdUnitId(SystemConfig.START_AD_ID);
        ad.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i("yaolinnan","onAdLoaded");
                if(!isDestroyed()) {
                    ad.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.i("yaolinnan","onAdFailedToLoad:"+i);
            }
        });
        ad.loadAd(new AdRequest.Builder().build());
//        mPermissionHelper=new PermissionHelper(this);
//        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
//            @Override
//            public void onAfterApplyAllPermission() {
//                AdManager.getInstance(getApplicationContext()).init(SystemConfig.AD_APPID, SystemConfig.AD_SECRET, true);
//                SpotManager.getInstance(getApplicationContext()).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);
//                SpotManager.getInstance(getApplicationContext()).showSpot(mContext,
//                        new SpotListener() {
//                            @Override
//                            public void onShowSuccess() {
//
//                            }
//
//                            @Override
//                            public void onShowFailed(int i) {
//                                Log.i("yaolinnan","spot error:"+i);
//                            }
//
//                            @Override
//                            public void onSpotClosed() {
//
//                            }
//
//                            @Override
//                            public void onSpotClicked(boolean b) {
//
//                            }
//                        });
//            }
//        });
//        // 获取广告条
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//        OffersManager.getInstance(getApplicationContext()).onAppLaunch();
    }

    private void startAnimator(){
        AnimatorSet start=new AnimatorSet();
        float dgree=random.getRandom(-40,40);
        ObjectAnimator scalex=ObjectAnimator.ofFloat(mGameIV,View.SCALE_X,1.2f);
        ObjectAnimator scaley=ObjectAnimator.ofFloat(mGameIV,View.SCALE_Y,1.2f);
        ObjectAnimator rotate=ObjectAnimator.ofFloat(mGameIV,View.ROTATION,dgree);
        start.playTogether(scalex,scaley,rotate);
        start.setStartDelay(1000);
        start.setDuration(1500);

        AnimatorSet end=new AnimatorSet();
        ObjectAnimator scalex1=ObjectAnimator.ofFloat(mGameIV,View.SCALE_X,1f);
        ObjectAnimator scaley1=ObjectAnimator.ofFloat(mGameIV,View.SCALE_Y,1f);
        ObjectAnimator rotate1=ObjectAnimator.ofFloat(mGameIV,View.ROTATION,0f);
        end.playTogether(scalex1,scaley1,rotate1);
        end.setDuration(1000);

        set.playSequentially(start,end);
        set.setInterpolator(new DecelerateInterpolator());
//        set.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                startAnimator();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
        set.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_game:
                showDialog();
                break;
            case R.id.start_settings:
                Intent i = new Intent(mContext,
                        SettingsActivity.class);
                startActivity(i);
                break;
        }
    }

    private void showDialog(){
        final Dialog dialog=new Dialog(mContext,R.style.dialog_bottom_full);
        View view= LayoutInflater.from(mContext).inflate(R.layout.start_dialog, null);
        view.setFitsSystemWindows(true);
        dialog.setContentView(view);
        Button primary= (Button) view.findViewById(R.id.start_primary);
        primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("check", SystemConfig.PRIMARY);
                startActivity(intent);
            }
        });
        Button middle= (Button) view.findViewById(R.id.start_middle);
        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("check", SystemConfig.MIDDLE);
                startActivity(intent);
            }
        });
        Button high= (Button) view.findViewById(R.id.start_high);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("check", SystemConfig.HIGH);
                startActivity(intent);
            }
        });
        SharedPreferences sp=mContext.getSharedPreferences(SystemConfig.PREFERENCES, Activity.MODE_PRIVATE);
        int level=sp.getInt(SystemConfig.SHARE_LEVEL,SystemConfig.PRIMARY);
        switch (level){
            case SystemConfig.PRIMARY:
                primary.setEnabled(true);
                middle.setEnabled(false);
                high.setEnabled(false);
                break;
            case SystemConfig.MIDDLE:
                primary.setEnabled(true);
                middle.setEnabled(true);
                high.setEnabled(false);
                break;
            case SystemConfig.HIGH:
                primary.setEnabled(true);
                middle.setEnabled(true);
                high.setEnabled(true);
                break;
        }
        Window win = dialog.getWindow();
        win.setGravity(Gravity.CENTER);
        win.setWindowAnimations(android.R.style.Animation_Dialog);
        win.setLayout(Util.dip2px(mContext,250), Util.dip2px(mContext,280));
        dialog.show();
    }

//    @Override
//    public void onBackPressed() {
        // 如果有需要，可以点击后退关闭插播广告。
//        if (SpotManager.getInstance(getApplicationContext()).isSpotShowing()) {
//            SpotManager.getInstance(getApplicationContext()).hideSpot();
//        }else {
//            finish();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        // 插屏广告
//        SpotManager.getInstance(getApplicationContext()).onPause();
        set.removeAllListeners();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 插屏广告
//        SpotManager.getInstance(getApplicationContext()).onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimator();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
        // 插屏广告
//        SpotManager.getInstance(getApplicationContext()).onDestroy();
//        BannerManager.getInstance(getApplicationContext()).onDestroy();
//        OffersManager.getInstance(getApplicationContext()).onAppExit();
        mGameIV.clearAnimation();
        set.end();
        set=null;
        random=null;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
//    }
}
