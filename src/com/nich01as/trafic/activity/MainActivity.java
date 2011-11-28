/**
 * @(#)MainActivity.java, 2011-11-23. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.nich01as.trafic.R;

/**
 *
 * @author Nicholas
 *
 */
public class MainActivity extends Activity implements OnClickListener {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.all_question).setOnClickListener(this);
        findViewById(R.id.chapter).setOnClickListener(this);
        findViewById(R.id.wrong_question).setOnClickListener(this);
        findViewById(R.id.random).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_question: {
                Intent intent = new Intent(this, AllQuestionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.chapter: {
                Intent intent = new Intent(this, ChapterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.wrong_question: {
                Intent intent = new Intent(this, WrongQuestionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.random: {
                Intent intent = new Intent(this, RandomQuestionActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
