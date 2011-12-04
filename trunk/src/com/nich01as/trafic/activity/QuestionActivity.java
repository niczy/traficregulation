package com.nich01as.trafic.activity;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.loopj.android.image.SmartImageView;
import com.nich01as.trafic.Question;
import com.nich01as.trafic.QuestionManager;
import com.nich01as.trafic.R;
import com.nich01as.trafic.TraficApp;
import com.nich01as.trafic.store.TraficDB;

public abstract class QuestionActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
    
    private static final String TAG = "Trafic";

    private static final String EXTRA_INDEX = "index";
    
    private static final String BUNDLE_IDX = "idx";
    
    private int mIdx;
    QuestionManager mQuestionManager;
    private int mCorrectAnswerId;
    private boolean mAnswerChecked = false;
    private View mCorrectResultView;
    private View mWrongResultView;
    private Animation mFadeinAnimation;
    private Animation mFadeoutAnimation;
    private ViewFlipper mQuestionFlipper;
    private View mCurrentQuestionView;
    private View mAnotherQuestionView;
    protected TraficDB mDb;
    private AdView mAdView;
    private LinearLayout mAdLayout;
   
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        
        findViewById(R.id.previous).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.ok).setOnClickListener(this);
        mDb = TraficApp.getInstance().getTraficDb();
        mFadeinAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        mFadeoutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mCorrectResultView = findViewById(R.id.result_correct);
        mWrongResultView = findViewById(R.id.result_wrong);
        
        mQuestionFlipper = (ViewFlipper) findViewById(R.id.question_filpper);
        mCurrentQuestionView = LayoutInflater.from(this).inflate(R.layout.question_layout, null);
        mAnotherQuestionView = LayoutInflater.from(this).inflate(R.layout.question_layout, null);
        mQuestionFlipper.addView(mCurrentQuestionView, 0);
        mQuestionFlipper.addView(mAnotherQuestionView);
        mQuestionFlipper.setDisplayedChild(0);
        mQuestionManager = QuestionManager.getInstance(this);
        mAdView = new AdView(this, AdSize.BANNER, "a14edb312d23d69");
        mAdLayout = (LinearLayout) findViewById(R.id.ad);
        mAdLayout.addView(mAdView);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_IDX, mIdx);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mIdx = savedInstanceState.getInt(BUNDLE_IDX);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        //mIdx = getCurrentQuestionId();
        if (mIdx == -1) {

        } else {
            Question question;
            try {
                question = mDb.getQuestiion(mIdx);
                prepareQuestionView(question, mCurrentQuestionView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AdRequest adRequest = new AdRequest();
        mAdView.loadAd(adRequest);
    }
    
    private void prepareQuestionView(Question question, View view) {
        RadioGroup mRadioGroup;
        TextView mIndex;
        TextView mDescription;
        SmartImageView mSmartImageView;
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        mIndex = (TextView) view.findViewById(R.id.question_id);
        mDescription = (TextView) view.findViewById(R.id.descripion);
        mRadioGroup.setOnCheckedChangeListener(this);
        mSmartImageView = (SmartImageView) view.findViewById(R.id.image);
        mIndex.setText(Integer.toString(question.getIndex()));
        mDescription.setText(question.getDescription());
        mRadioGroup.removeAllViews();
        if (TextUtils.isEmpty(question.getImageUrl())) {
            mSmartImageView.setVisibility(View.GONE);
        } else {
            mSmartImageView.setVisibility(View.VISIBLE);
            mSmartImageView.setImageUrl(question.getImageUrl());
        }
        int ansIdx = 0;
        for (String option : question.getChoices()) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.radio_item, null);
            radioButton.setText(option);
            mRadioGroup.addView(radioButton);
            if (ansIdx == question.getAnswerIdx()) {
                mCorrectAnswerId = radioButton.getId();
            }
            ansIdx++;
        }
    }
    
    private void swap() {
        View tmpView = mCurrentQuestionView;
        mCurrentQuestionView = mAnotherQuestionView;
        mAnotherQuestionView = tmpView;
    }
    
    protected abstract int getNextQuestionId();
    
    protected abstract int getPreviousQuestionId();
    
    private void showNextQuestion() throws JSONException {
        mIdx = getNextQuestionId();
        prepareQuestionView(mDb.getQuestiion(mIdx), mAnotherQuestionView);
        swap();
        //mQuestionFlipper.setInAnimation(this, R.anim.slide_in_right);
        //mQuestionFlipper.setOutAnimation(this, R.anim.slide_out_left);
        mQuestionFlipper.showNext();
        //showQuestion((mIdx+1)%mQuestionManager.getTotalCount());
    }
    
    private void showPreviousQuestion() throws JSONException {
        mIdx = getPreviousQuestionId();
        prepareQuestionView(mDb.getQuestiion(mIdx), mAnotherQuestionView);
        swap();
        //mQuestionFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        //mQuestionFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        mQuestionFlipper.showPrevious();
        //showQuestion((mIdx-1+mQuestionManager.getTotalCount())%mQuestionManager.getTotalCount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                try {
                    showNextQuestion();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                break;
            case R.id.previous:
                try {
                    showPreviousQuestion();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                break;
            case R.id.ok:
                final View animateView = mAnswerChecked ? mCorrectResultView
                        : mWrongResultView;
                if (!mAnswerChecked) {
                    onAnswerIncorrect(mIdx);
                } else {
                    onAnswerCorrect(mIdx);
                }
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
                    public void onAnimationStart(Animation animation) {animateView.setVisibility(View.GONE);}

                    @Override
                    public void onAnimationRepeat(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        
                    }
                });
                animateView.startAnimation(mFadeinAnimation);

                break;
            default:
                break;
        }
        
    }
    
    protected void onAnswerIncorrect(int questionId) {
        try {
            mDb.markInCorrect(questionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    protected void onAnswerCorrect(int questionId) {
    }
    
    protected void setCurrentQuestionId(int id) {
        mIdx = id;
    }
    
    protected final int getCurrentQuestionId() {
        return mIdx;
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