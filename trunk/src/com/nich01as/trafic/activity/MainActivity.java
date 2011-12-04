/**
 * @(#)MainActivity.java, 2011-11-23. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.nich01as.trafic.QuestionManager;
import com.nich01as.trafic.R;
import com.nich01as.trafic.TraficApp;
import com.nich01as.trafic.store.TraficDB;

/**
 *
 * @author Nicholas
 *
 */
public class MainActivity extends Activity implements OnClickListener {
    
    private static final int CREATING_DB_DIALOG = 1;
    private ProgressDialog mProgreassDialog;
    private static AsyncTask<Void, Integer, Boolean> mCreaeDbTask;
    final TraficApp app = TraficApp.getInstance();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.all_question).setOnClickListener(this);
        findViewById(R.id.chapter).setOnClickListener(this);
        findViewById(R.id.wrong_question).setOnClickListener(this);
        findViewById(R.id.random).setOnClickListener(this);
        
        if (!app.isDbCreated()) {
            showDialog(CREATING_DB_DIALOG);
            if (mCreaeDbTask != null) {
                mCreaeDbTask.cancel(true);
            }
            mCreaeDbTask = new AsyncTask<Void, Integer, Boolean>() {

                @Override
                protected Boolean doInBackground(Void... params) {
                    QuestionManager questionManager = QuestionManager
                            .getInstance(app);
                    TraficDB db = app.getTraficDb();
                    for (int i = 0; i < questionManager.getTotalCount(); i++) {
                        if (isCancelled()) {
                            return false;
                        }
                        db.insertQuestion(questionManager.getQuestion(i));
                        publishProgress(i+1);
                    }
                    return true;
                }

                @Override
                protected void onProgressUpdate(Integer... progress) {
                    Log.d("Trafic", "Progress " + progress[0].intValue());
                    if (mProgreassDialog != null) {
                        mProgreassDialog.setProgress(progress[0]);
                    }
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        Log.d("Trafic", "data base succeed.");
                        app.setDbCreated();
                        removeDialog(CREATING_DB_DIALOG);
                        Toast.makeText(MainActivity.this, "数据初始化成功",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute(new Void[] {});
        }
    }
    
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        mProgreassDialog = new ProgressDialog(this);
        mProgreassDialog.setTitle("正在初始化数据");
        mProgreassDialog.setIndeterminate(false);
        mProgreassDialog.setMax(QuestionManager
                .getInstance(app).getTotalCount());
        mProgreassDialog.setProgress(0);
        mProgreassDialog.setIcon(android.R.drawable.ic_dialog_info);
        mProgreassDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                MainActivity.this.finish();
            }
        });
        mProgreassDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return mProgreassDialog;
    }
    
    @Override
    public void onResume() {
        super.onResume();
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
