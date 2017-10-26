package com.yln.question;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.umeng.analytics.MobclickAgent;
import com.yln.question.util.PlayUtil;
import com.yln.question.util.ToastUtil;
import com.yln.question.util.Util;

import java.lang.reflect.Field;
import java.util.Random;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTitleTV,mTypeTV,mTimeTV,mScoreTV,mContentTV;
    private RadioGroup mItemsGroup;
    private RadioButton mItemBtn1,mItemBtn2,mItemBtn3,mItemBtn4;
    private Button mSendBtn;
    private ImageView mShareIV;
    private boolean isSelected[];// 防止产生一样的随机数
    private Random random = new Random();
    private MediaPlayer player = null;
    private int check=-1,length=0,choice=0,score=100,time=45;
    private String[] questions,tips;
    private Context mContext;
    private SharedPreferences sp;
    private boolean isPause=false;
    private AdView mAdView;

    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                time--;
                mTimeTV.setText(time+"s");
                if(time<=0){
                    showDialog(false,getResources().getString(R.string.answer_time_error));
                }else {
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        sp=getSharedPreferences(SystemConfig.PREFERENCES,Activity.MODE_PRIVATE);
        mTitleTV= (TextView) findViewById(R.id.question_title);
        mTypeTV= (TextView) findViewById(R.id.question_type);
        mTimeTV= (TextView) findViewById(R.id.question_time);
        mScoreTV= (TextView) findViewById(R.id.question_score);
        mContentTV= (TextView) findViewById(R.id.question_content);
        mItemsGroup= (RadioGroup) findViewById(R.id.question_items_group);
        mItemBtn1= (RadioButton) findViewById(R.id.question_item1);
        mItemBtn2= (RadioButton) findViewById(R.id.question_item2);
        mItemBtn3= (RadioButton) findViewById(R.id.question_item3);
        mItemBtn4= (RadioButton) findViewById(R.id.question_item4);
        mSendBtn= (Button) findViewById(R.id.send_button);
        mShareIV= (ImageView) findViewById(R.id.share_button);
        mSendBtn.setOnClickListener(this);
        mShareIV.setOnClickListener(this);

        check = getIntent().getIntExtra("check", SystemConfig.PRIMARY);
        switch (check) {
            case SystemConfig.PRIMARY:
                score=100;
                questions = getResources().getStringArray(R.array.primary);
                length = questions.length;
                isSelected = new boolean[length];
                mTitleTV.setText(R.string.question_primary);
                break;
            case SystemConfig.MIDDLE:
                score=100+getResources().getStringArray(R.array.primary).length*10;
                questions = getResources().getStringArray(R.array.middle);
                length = questions.length;
                isSelected = new boolean[length];
                mTitleTV.setText(R.string.question_middle);
                break;
            case SystemConfig.HIGH:
                score=100+getResources().getStringArray(R.array.primary).length*10+getResources().getStringArray(R.array.middle).length*20;
                questions = getResources().getStringArray(R.array.high);
                length = questions.length;
                isSelected = new boolean[length];
                mTitleTV.setText(R.string.question_high);
                break;
        }
        refresh();
//        score=sp.getInt(SystemConfig.SHARE_SCORE,100);
        mScoreTV.setText(score+"");
        // 获取广告条
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i("yaolinnan","onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.i("yaolinnan","onAdFailedToLoad:"+i);
            }
        });
        mAdView.loadAd(adRequest);
//        View bannerView = BannerManager.getInstance(getApplicationContext())
//                .getBannerView(this, new BannerViewListener() {
//                    @Override
//                    public void onRequestSuccess() {
//
//                    }
//
//                    @Override
//                    public void onSwitchBanner() {
//
//                    }
//
//                    @Override
//                    public void onRequestFailed() {
//                        Log.i("yaolinnan","banner error");
//                    }
//                });
//
//        // 获取要嵌入广告条的布局
//        LinearLayout bannerLayout = (LinearLayout) findViewById(R.id.ll_banner);
//        // 将广告条加入到布局中
//        bannerLayout.addView(bannerView);
    }


    private void refresh() {
        if (check == SystemConfig.PRIMARY) {
            time=45;
        } else if (check == SystemConfig.MIDDLE) {
            time=50;
        } else if (check == SystemConfig.HIGH) {
            time=60;
        }
        if (choice >= length) {
            //统计通关
            MobclickAgent.onEvent(this,SystemConfig.COMPLATE_EVENT,mTitleTV.getText().toString());
            showDialog(true,getResources().getString(R.string.answer_success));
            PlayUtil.playWin(player, mContext);
            return;
        }
        mItemsGroup.clearCheck();
        int index = 0;
        do {
            index = random.nextInt(length);
        } while (isSelected[index]);
        // 获得目前没随机出现的题目
        isSelected[index] = true;
        String field = questions[index];
        try {
            // 反射机制
            Field f = R.string.class.getField(field);
            String question = getResources()
                    .getString(f.getInt(R.string.class)).trim();
            tips = question.split("-");
        } catch (Exception e) {
            Log.e("guess", e.getMessage());
        }
        choice++;
        if (tips != null && tips.length > 0) {
            mTypeTV.setText(tips[0]);
            mContentTV.setText(choice + "." + tips[1]);
            mItemBtn1.setText(tips[2]);
            mItemBtn2.setText(tips[3]);
            mItemBtn3.setText(tips[4]);
            mItemBtn4.setText(tips[5]);
        }
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_button:
                int radioId = mItemsGroup.getCheckedRadioButtonId();
                View view = MainActivity.this.findViewById(radioId);
                if (view == null) {
                    ToastUtil.showToast(getApplicationContext(), R.string.send_answer_error);
                    break;
                }
                int index = mItemsGroup.indexOfChild(view);
                int answer = Integer.parseInt(tips[6]);
                if (index == answer) {// 答对
                    score += 10 * (check + 1);
//                    ToastUtil.showToast(view.getContext(), "你太聪明了！");
                    mScoreTV.setText(score+"");
                    refresh();
                } else {// 答错
                    RadioButton rb = (RadioButton) mItemsGroup.getChildAt(answer);
                    String s = rb.getText().toString().trim();
                    String str= String.format(getResources().getString(R.string.answer_error),s);
//                    ToastUtil.showToast(view.getContext(), str);
                    showDialog(false,str);
                    PlayUtil.playLoss(player, view.getContext());
                }
                break;
            case R.id.share_button:
                startShare();
                break;
        }
    }

    private void showDialog(boolean success,String msg){
        mHandler.removeCallbacksAndMessages(null);
        final Dialog dialog=new Dialog(mContext,R.style.dialog_bottom_full);
        View view= LayoutInflater.from(mContext).inflate(R.layout.answer_dialog, null);
        view.setFitsSystemWindows(true);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        TextView message= (TextView) view.findViewById(R.id.answer_message);
        RelativeLayout content= (RelativeLayout) view.findViewById(R.id.answer_content);
        ImageView adward= (ImageView) view.findViewById(R.id.adward_image);
        ImageView image= (ImageView) view.findViewById(R.id.answer_icon);
        Button confirm= (Button) view.findViewById(R.id.confirm_button);
        int level=sp.getInt(SystemConfig.SHARE_LEVEL,SystemConfig.PRIMARY);
        int height=220;
        if(success){
            image.setImageResource(R.drawable.icon_success);
            content.setVisibility(View.VISIBLE);
            if(check==SystemConfig.PRIMARY) {
                adward.setImageResource(R.drawable.icon_rank_1);
                if(level<SystemConfig.MIDDLE)
                    sp.edit().putInt(SystemConfig.SHARE_LEVEL,SystemConfig.MIDDLE).commit();
            }
            else if(check==SystemConfig.MIDDLE){
                adward.setImageResource(R.drawable.icon_rank_2);
                if(level<SystemConfig.HIGH)
                    sp.edit().putInt(SystemConfig.SHARE_LEVEL,SystemConfig.HIGH).commit();
            }
            else if(check==SystemConfig.HIGH){
                adward.setImageResource(R.drawable.icon_rank_3);
                msg=getResources().getString(R.string.answer_all_success);
            }
            height=300;
        }else{
            image.setImageResource(R.drawable.icon_failtrue);
            content.setVisibility(View.GONE);
        }
        message.setText(msg);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScore();
                dialog.dismiss();
                finish();
            }
        });
        Window win = dialog.getWindow();
        win.setGravity(Gravity.CENTER);
        win.setWindowAnimations(android.R.style.Animation_Dialog);
        win.setLayout(Util.dip2px(mContext,280), Util.dip2px(mContext,height));
        dialog.show();
    }

    private void saveScore(){
        int s=sp.getInt(SystemConfig.SHARE_SCORE,0);
        if(score>s){
            sp.edit().putInt(SystemConfig.SHARE_SCORE,score).commit();
            ToastUtil.showToast(mContext,R.string.score_save);
        }
    }

    private void startShare(){
        String str=getResources().getString(R.string.share_content);
        String content=String.format(str,tips[1]);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_title));
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.app_name)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        BannerManager.getInstance(getApplicationContext()).onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
        mHandler.removeCallbacksAndMessages(null);
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isPause) {
            mHandler.removeMessages(0);
            isPause = true;
        }
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isPause) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
            isPause=false;
        }
        if (mAdView != null) {
            mAdView.resume();
        }
    }
}
