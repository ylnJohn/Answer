package com.yln.answer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by linnan.yao on 2017/10/13.
 */

public class StartActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mGameIV,mSettingsIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mGameIV= (ImageView) findViewById(R.id.start_game);
        mSettingsIV= (ImageView) findViewById(R.id.start_settings);
        mGameIV.setOnClickListener(this);
        mSettingsIV.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_game:
                Intent intent = new Intent(StartActivity.this,
                        MainActivity.class);
//                intent.putExtra("check", check);
                startActivity(intent);
                break;
            case R.id.start_settings:
                Intent i = new Intent(StartActivity.this,
                        SettingsActivity.class);
                startActivity(i);
                break;
        }
    }
}
