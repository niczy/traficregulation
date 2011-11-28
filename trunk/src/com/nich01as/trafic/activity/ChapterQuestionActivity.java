/**
 * @(#)ChapterQuestionActivity.java, 2011-11-28. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

import java.util.List;

import org.json.JSONException;

import android.os.Bundle;

import com.nich01as.trafic.Question;

/**
 *
 * @author Nicholas
 *
 */
public class ChapterQuestionActivity extends ListQuestionActivity {
    
    public static final String EXTREA_CHAPTER = "chapter";
    private int mChapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mChapter = getIntent().getIntExtra(EXTREA_CHAPTER, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<Question> getQuestionList() {
        try {
            return mDb.getQuestionInChapter(mChapter);
        } catch (JSONException e) {
            return null;
        }
    }
}
