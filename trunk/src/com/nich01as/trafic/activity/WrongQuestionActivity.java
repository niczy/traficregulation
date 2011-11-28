/**
 * @(#)WrongQuestionActivity.java, 2011-11-28. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.nich01as.trafic.Question;

/**
 *
 * @author Nicholas
 *
 */
public class WrongQuestionActivity extends ListQuestionActivity {

    @Override
    protected List<Question> getQuestionList() {
        try {
            return mDb.getWrongQuestions();
        } catch (JSONException e) {
            return new ArrayList<Question>(0);
        }
    }
    
    @Override
    protected void onAnswerCorrect(int questionId) {
        try {
            mDb.markCorrect(questionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
