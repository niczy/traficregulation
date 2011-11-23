/**
 * @(#)Questions.java, 2011-11-23. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 *
 * @author Nicholas
 *
 */
public class QuestionManager {
    
    private List<Question> questions = new ArrayList<Question>(750);
    
    private static QuestionManager mInstance;
    
    public static QuestionManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new QuestionManager();
            try {
                InputStream is = context.getAssets().open("question.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                mInstance.load(reader);
            } catch (IOException e) {
                Log.e("Trafic", "failed to load", e);
            }
        }
        return mInstance;
    }
    
    public int getTotalCount() {
        return questions.size();
    }
    
    public void load(BufferedReader reader) {
        int lineCount = 0;
        long startTime = System.currentTimeMillis();
        String line = null;
        do {
            try {
                line = reader.readLine();
                if (line != null) {
                    questions.add(new Question(line));
                }
                lineCount++;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }    
        } while (line != null);
        Log.d("Trafic", (System.currentTimeMillis() - startTime) * 1.0 / 1000 + " " + lineCount);
    }
    
    public Question getQuestion(int idx) {
        if (idx >= 0 && idx < questions.size()) {
            return questions.get(idx);
        }
        return null;
    }

}
