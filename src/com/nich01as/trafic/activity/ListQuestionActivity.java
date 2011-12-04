/**
 * @(#)ListQuestionActivity.java, 2011-11-28. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

import java.util.List;

import android.os.Bundle;

import com.nich01as.trafic.Question;

/**
 *
 * @author Nicholas
 *
 */
public abstract class ListQuestionActivity extends QuestionActivity {
    
    private static final String BUNDLE_LIST_IDX = "list_idx";
    
    private List<Question> mQuestions;
    private int mListIdx;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestions = getQuestionList();
        mListIdx = mApp.getQuestionIdx(getTag());
        if (mQuestions.size() == 0) {
            setCurrentQuestionId(-1);
        } else {
            if (mListIdx >= mQuestions.size()) {
                mListIdx = 0;
            } 
            setCurrentQuestionId(mQuestions.get(mListIdx).getIndex());
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_LIST_IDX, mListIdx);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mListIdx = savedInstanceState.getInt(BUNDLE_LIST_IDX);
    }
    
    protected abstract List<Question> getQuestionList();
    
    @Override
    protected int getNextQuestionId() {
        mListIdx++;
        if (mListIdx >= mQuestions.size()) {
            mListIdx = 0;
        }
        mApp.recordQuestionIdx(getTag(), mListIdx);
        return mQuestions.get(mListIdx).getIndex();
    }

    @Override
    protected int getPreviousQuestionId() {
        mListIdx--;
        if (mListIdx < 0) {
            mListIdx = mQuestions.size() - 1;
        }
        return mQuestions.get(mListIdx).getIndex();
    }

}
