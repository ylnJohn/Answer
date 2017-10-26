package com.yln.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;

/**
 * Created by linnan.yao on 2017/10/13.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mBackIV;
    private RelativeLayout mFeedbackLayout,mAboutLayout,mRankLayout,mRecommendLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mBackIV= (ImageView) findViewById(R.id.back_button);
        mBackIV.setOnClickListener(this);
        mFeedbackLayout= (RelativeLayout) findViewById(R.id.settings_feedback);
        mFeedbackLayout.setOnClickListener(this);
        mAboutLayout= (RelativeLayout) findViewById(R.id.settings_about);
        mAboutLayout.setOnClickListener(this);
        mRankLayout= (RelativeLayout) findViewById(R.id.settings_rank);
        mRankLayout.setOnClickListener(this);
        mRecommendLayout= (RelativeLayout) findViewById(R.id.settings_recommend);
        mRecommendLayout.setOnClickListener(this);
        FeedbackAPI.setBackIcon(R.drawable.icon_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                finish();
                break;
            case R.id.settings_feedback:
                FeedbackAPI.openFeedbackActivity();
                break;
            case R.id.settings_recommend:
//                OffersManager.getInstance(getApplicationContext()).showOffersWall();
                break;
            case R.id.settings_rank:
                Intent intent=new Intent(this,RankActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_about:
                Intent i=new Intent(this,AboutActivity.class);
                startActivity(i);
                break;
        }
    }
}
