/**
 * @(#)AllQuestionActivity.java, 2011-11-28. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

/**
 *
 * @author Nicholas
 *
 */
public class AllQuestionActivity extends QuestionActivity {

    @Override
    protected int getNextQuestionId() {
        return (getCurrentQuestionId()+1)%mDb.getTotalCount();
    }

    @Override
    protected int getPreviousQuestionId() {
        return (getCurrentQuestionId()-1+mDb.getTotalCount())%mDb.getTotalCount();
    }
}
