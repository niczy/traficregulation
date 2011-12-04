/**
 * @(#)ChapterActivity.java, 2011-11-23. Copyright 2011 Yodao, Inc. All rights
 *                           reserved. YODAO PROPRIETARY/CONFIDENTIAL. Use is
 *                           subject to license terms.
 */
package com.nich01as.trafic.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nich01as.trafic.R;

/**
 * @author Nicholas
 */
public class ChapterActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_activity);
        ListAdapter adapter = new ChapterAdapter (this,
                R.layout.chapter_item, getResources().getStringArray(
                        R.array.categories));
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, ChapterQuestionActivity.class);
        intent.putExtra(ChapterQuestionActivity.EXTREA_CHAPTER, position+1);
        startActivity(intent);
    }
    
    private class ChapterAdapter extends ArrayAdapter<String> {
        
        private int mResource;
        /**
         * @param context
         * @param resource
         * @param textViewResourceId
         * @param objects
         */
        public ChapterAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            mResource = resource;
        }
        
        public View getView(int position, View convertView, ViewGroup parent) {
            return createViewFromResource(position, convertView, parent);
        }

        private View createViewFromResource(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
            } else {
                view = convertView;
            }
            ((TextView) view.findViewById(R.id.number)).setText(Integer.toString(position+1));
            ((TextView) view.findViewById(R.id.chapter_title)).setText(getItem(position));
            return view;
        }
    }
}
