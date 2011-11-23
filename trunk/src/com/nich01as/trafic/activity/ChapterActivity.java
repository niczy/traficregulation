/**
 * @(#)ChapterActivity.java, 2011-11-23. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.nich01as.trafic.R;

/**
 *
 * @author Nicholas
 *
 */
public class ChapterActivity extends ListActivity {
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_activity);
        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.chapter_item, getResources().getStringArray(R.array.categories));
        setListAdapter(adapter);
    }

}
