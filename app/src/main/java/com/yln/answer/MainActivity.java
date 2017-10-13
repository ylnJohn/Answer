package com.yln.answer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yln.answer.util.ToastUtil;

import java.lang.reflect.Field;
import java.text.Format;
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
    private int check=-1,length=0,choice=0,score=0;
    private String[] questions,tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                questions = getResources().getStringArray(R.array.primary);
                length = questions.length;
                isSelected = new boolean[length];
                mTitleTV.setText(R.string.question_primary);
                break;
            case SystemConfig.MIDDLE:
                questions = getResources().getStringArray(R.array.middle);
                length = questions.length;
                isSelected = new boolean[length];
                mTitleTV.setText(R.string.question_middle);
                break;
            case SystemConfig.HIGH:
                questions = getResources().getStringArray(R.array.high);
                length = questions.length;
                isSelected = new boolean[length];
                mTitleTV.setText(R.string.question_high);
                break;
        }
        refresh();
    }


    private void refresh() {
        if (choice >= length) {
//            if (check == SystemConfig.PRIMARY) {
//                designation = getResources().getString(
//                        R.string.designation_normal);
//                award = 100;
//            } else if (check == SystemConfig.MIDDLE) {
//                designation = getResources().getString(
//                        R.string.designation_middle);
//                award = 200;
//            } else if (check == SystemConfig.HIGH) {
//                designation = getResources().getString(
//                        R.string.designation_high);
//                award = 300;
//            }
//            Builder builder = new AlertDialog.Builder(GameActivity.this);
//            builder.setTitle("成功通关");
//            builder.setIcon(R.drawable.icon_win);
//            builder.setMessage("恭喜你通关成功，成为" + designation + "，奖励积分：" + award);
//            builder.setOnKeyListener(keylistener);
//            builder.setCancelable(false);
//            builder.setPositiveButton("确定",
//                    new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int arg1) {
//                            PointsManager.getInstance(GameActivity.this)
//                                    .awardPoints(award);
//                            FileUtil.saveDes(GameActivity.this, designation);
//                            updateGrade();
//                            finish();
//                        }
//                    });
//            builder.create().show();
//            PlayUtil.playWin(player, GameActivity.this);
//            return;
        }
        mItemsGroup.clearCheck();
        mScoreTV.setText(score+"");
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
                    ToastUtil.showToast(view.getContext(), str);
//                    Builder builder = new AlertDialog.Builder(view.getContext());
//                    builder.setTitle("回答错误");
//                    builder.setIcon(R.drawable.icon_lose);
//                    builder.setOnKeyListener(keylistener);
//                    builder.setCancelable(false);
//                    builder.setMessage("对不起回答错误，正确答案是:" + s);
//                    builder.setPositiveButton("确定",
//                            new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int arg1) {
//                                    dialog.dismiss();
//                                    updateGrade();
//                                    GameActivity.this.finish();
//                                }
//                            });
//                    builder.create().show();
//                    PlayUtil.playLoss(player, view.getContext());
                }
                break;
        }
    }
}
