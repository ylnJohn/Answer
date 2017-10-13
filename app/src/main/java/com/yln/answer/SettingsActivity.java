package com.yln.answer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by linnan.yao on 2017/10/13.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mBackIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mBackIV= (ImageView) findViewById(R.id.back_button);
        mBackIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                finish();
                break;
        }
    }
}
