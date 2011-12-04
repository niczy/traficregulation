/**
 * @(#)RandomQuestionActivity.java, 2011-11-28. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

import java.util.Random;

import org.json.JSONException;

import android.os.Bundle;

/**
 *
 * @author Nicholas
 *
 */
public class RandomQuestionActivity extends QuestionActivity {
    
    private int mTotalCount;
    private Random mRandom;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTotalCount = mDb.getTotalCount();
        mRandom = new Random();
        setCurrentQuestionId(getNextQuestionId());
    }
    
    @Override
    protected int getNextQuestionId() {
        return mRandom.nextInt(mTotalCount);
    }

    @Override
    protected int getPreviousQuestionId() {
        return mRandom.nextInt(mTotalCount);
    }
    
    @Override
    protected void onAnswerCorrect(int questionId) {
        try {
            mDb.markCorrect(questionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see com.nich01as.trafic.activity.QuestionActivity#getTag()
     */
    @Override
    protected String getTag() {
        return "random";
    }
}
