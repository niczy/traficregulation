/**
 * @(#)TraficApp.java, 2011-11-28. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic;

import com.nich01as.trafic.store.TraficDB;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 *
 * @author Nicholas
 *
 */
public class TraficApp extends Application {
    
    private static final String DB_CREATED = "database_created";
    private SharedPreferences mPreference;
    private TraficDB mDb;
    private static TraficApp mInstance;
    
    
    public static TraficApp getInstance() {
        return mInstance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mDb = new TraficDB(this);
        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        if (!mPreference.getBoolean(DB_CREATED, false)) {
            new AsyncTask<Void, Double, Boolean>() {

                @Override
                protected Boolean doInBackground(Void... params) {
                    QuestionManager questionManager = QuestionManager.getInstance(TraficApp.this);
                    TraficDB db = new TraficDB(TraficApp.this);
                    for (int i = 0; i < questionManager.getTotalCount(); i++) {
                        db.insertQuestion(questionManager.getQuestion(i));
                        super.publishProgress(i * 1.0 / questionManager.getTotalCount());
                    }
                    return true;
                }
                
                @Override
                protected void onProgressUpdate(Double... progress) {
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        Log.d("Trafic", "data base succeed.");
                        mPreference.edit().putBoolean(DB_CREATED, true).commit();
                    }
                }
            }.execute(new Void[] {});
        }
    }
    
    public TraficDB getTraficDb() {
        return mDb;
    }
    
}
