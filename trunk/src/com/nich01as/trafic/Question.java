/**
 * @(#)Question.java, 2011-11-23. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.text.TextUtils;

/**
 *
 * @author Nicholas
 *
 */
public class Question {
    
    private int mIndex;
    
    private int mChapter;
    
    private String mDescription;
    
    private List<String> mChoices = new ArrayList<String>(4);
    
    private int mAnswer;
    
    private String mImageUrl;
    
    public Question() {};
    
    public Question(String line) {
        if (line == null) {
            throw new IllegalArgumentException("line cannot be null.");
        }
        String[] seg = line.split("\t");
        mIndex = Integer.parseInt(seg[0]);
        mChapter = Integer.parseInt(seg[1]);
        mDescription = seg[2];
        for (int i = 3; i < seg.length - 2; i++) {
            if (!TextUtils.isEmpty(seg[i])) {
                mChoices.add(seg[i]);
            }
        }
        mImageUrl = seg[seg.length-2];
        mAnswer = Integer.parseInt(seg[seg.length-1])-1;
    }
    
    @Override
    public String toString() {
        return "Index = " + mIndex + " Description = " + mDescription;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
    public void setDescription(String description) {
        mDescription = description;
    }
    
    public int getIndex() {
        return mIndex;
    }
    
    public void setIndex(int index) {
        mIndex = index;
    }
    
    public int getChapter() {
        return mChapter;
    }
    
    public void setChapter(int chapter) {
        mChapter = chapter;
    }
    
    public int getAnswerIdx() {
        return mAnswer;
    }
    
    public void setAnswerIdx(int answerIdx) {
        mAnswer = answerIdx;
    }
    
//    public String getAnswer() {
//        return mChoices.get(mAnswer);
//    }
    
    public List<String> getChoices() {
        return Collections.unmodifiableList(mChoices);
    }
    
    public void setChoices(JSONArray choices) throws JSONException {
        mChoices = new ArrayList<String>(choices.length());
        for (int i = 0; i < choices.length(); i++) {
            mChoices.add(choices.getString(i));
        }
    }
    
    public void setChoices(List<String> choices) {
        mChoices = choices;
    }
    
    public String getImageUrl() {
        return mImageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
