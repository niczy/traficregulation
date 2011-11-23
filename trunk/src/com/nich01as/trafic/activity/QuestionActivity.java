package com.nich01as.trafic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.nich01as.trafic.Question;
import com.nich01as.trafic.QuestionManager;
import com.nich01as.trafic.R;
import com.nich01as.trafic.R.id;
import com.nich01as.trafic.R.layout;

public class QuestionActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
    
    private static final String TAG = "Trafic";

    private static final String EXTRA_INDEX = "index";
    
    private int mIdx;
    RadioGroup mRadioGroup;
    TextView mIndex;
    TextView mDescription;
    QuestionManager mQuestionManager;
    private int mCorrectAnswerId;
    private boolean mAnswerChecked = false;
    private View mCorrectResultView;
    private View mWrongResultView;
    private Animation mFadeinAnimation;
    private Animation mFadeoutAnimation;
    private SmartImageView mSmartImageView;
   
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mIndex = (TextView) findViewById(R.id.question_id);
        mDescription = (TextView) findViewById(R.id.descripion);
        findViewById(R.id.previous).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.ok).setOnClickListener(this);
        mFadeinAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        mFadeoutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mCorrectResultView = findViewById(R.id.result_correct);
        mWrongResultView = findViewById(R.id.result_wrong);
        mRadioGroup.setOnCheckedChangeListener(this);
        mSmartImageView = (SmartImageView) findViewById(R.id.image);
        mQuestionManager = QuestionManager.getInstance(this);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        mIdx = intent.getIntExtra(EXTRA_INDEX, 0);
        //mIdx = 206;
        Question question = mQuestionManager.getQuestion(mIdx);
        mIndex.setText(Integer.toString(mIdx));
        mDescription.setText(question.getDescription());
        mRadioGroup.removeAllViews();
        if (TextUtils.isEmpty(question.getImageUrl())) {
            mSmartImageView.setVisibility(View.GONE);
        } else {
            mSmartImageView.setVisibility(View.VISIBLE);
            mSmartImageView.setImageUrl(question.getImageUrl());
        }
        for (String option : question.getChoices()) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.radio_item, null);
            radioButton.setText(option);
            mRadioGroup.addView(radioButton);
            if (option.equals(question.getAnswer())) {
                mCorrectAnswerId = radioButton.getId();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                showQuestion((mIdx+1)%mQuestionManager.getTotalCount());
                break;
            case R.id.previous:
                showQuestion((mIdx-1+mQuestionManager.getTotalCount())%mQuestionManager.getTotalCount());
                break;
            case R.id.ok:
                final View animateView = mAnswerChecked ? mCorrectResultView
                        : mWrongResultView;

                animateView.setVisibility(View.VISIBLE);
                mFadeinAnimation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationRepeat(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animateView.startAnimation(mFadeoutAnimation);
                    }
                });
                mFadeoutAnimation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationRepeat(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animateView.setVisibility(View.GONE);
                    }
                });
                animateView.startAnimation(mFadeinAnimation);

                break;
            default:
                break;
        }
        
    }

    private void showQuestion(int idx) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_INDEX, idx);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.d(TAG, "checkedid is " + checkedId + " mCorrectAnswer is " + mCorrectAnswerId);
        if (checkedId == mCorrectAnswerId) {
            mAnswerChecked = true;
        } else {
            mAnswerChecked = false;
        }
    }
}