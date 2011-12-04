/**
 * @(#)AllQuestionActivity.java, 2011-11-28. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

import android.os.Bundle;

/**
 *
 * @author Nicholas
 *
 */
public class AllQuestionActivity extends QuestionActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentQuestionId(mApp.getQuestionIdx(getTag()));
    }

    @Override
    protected int getNextQuestionId() {
        int ret = (getCurrentQuestionId()+1)%mDb.getTotalCount();
        mApp.recordQuestionIdx(getTag(), ret);
        return ret;
    }

    @Override
    protected int getPreviousQuestionId() {
        return (getCurrentQuestionId()-1+mDb.getTotalCount())%mDb.getTotalCount();
    }

    @Override
    protected String getTag() {
        return "all";
    }
}
