package com.yln.question;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by linnan.yao on 2017/10/17.
 */

public class RankActivity extends BaseActivity{

    private TextView mRankTV;
    private ImageView mRankIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRankIV= (ImageView) findViewById(R.id.adward_image);
        mRankTV= (TextView) findViewById(R.id.rank_text);
        SharedPreferences sp=getSharedPreferences(SystemConfig.PREFERENCES, Activity.MODE_PRIVATE);
        int rank=sp.getInt(SystemConfig.SHARE_SCORE,0);
        mRankTV.setText(rank+"");
        int level=sp.getInt(SystemConfig.SHARE_LEVEL,0);
        switch (level){
            case 1:
                mRankIV.setImageResource(R.drawable.icon_rank_1);
                break;
            case 2:
                mRankIV.setImageResource(R.drawable.icon_rank_2);
                break;
            case 3:
                mRankIV.setImageResource(R.drawable.icon_rank_3);
                break;
        }
    }
}
