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

import android.text.TextUtils;

/**
 *
 * @author Nicholas
 *
 */
public class Question {
    
    private int mIndex;
    
    private int mCategory;
    
    private String mDescription;
    
    private List<String> mChoices = new ArrayList<String>(4);
    
    private int mAnswer;
    
    private String mImageUrl;
    
    public Question(String line) {
        if (line == null) {
            throw new IllegalArgumentException("line cannot be null.");
        }
        String[] seg = line.split("\t");
        mIndex = Integer.parseInt(seg[0]);
        mCategory = Integer.parseInt(seg[1]);
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
    
    public int getIndex() {
        return mIndex;
    }
    
    public int getCategory() {
        return mCategory;
    }
    
    public int getAnswerIdx() {
        return mAnswer;
    }
    
    public String getAnswer() {
        return mChoices.get(mAnswer);
    }
    
    public List<String> getChoices() {
        return Collections.unmodifiableList(mChoices);
    }
    
    public String getImageUrl() {
        return mImageUrl;
    }
}
