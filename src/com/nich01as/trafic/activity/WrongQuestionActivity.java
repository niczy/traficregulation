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

import android.view.View;

import com.nich01as.trafic.Question;
import com.nich01as.trafic.R;

/**
 *
 * @author Nicholas
 *
 */
public class WrongQuestionActivity extends ListQuestionActivity {

    @Override
    protected List<Question> getQuestionList() {
        List<Question> ret;
        try {
            ret = mDb.getWrongQuestions();
        } catch (JSONException e) {
            ret = new ArrayList<Question>(0);
        }
        if (ret.size() == 0) {
            findViewById(R.id.empty).setVisibility(View.VISIBLE);
        }
        return ret;
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
        return "wrong";
    }
}
