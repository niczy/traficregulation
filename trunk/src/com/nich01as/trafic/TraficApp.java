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
    }
    
    public boolean isDbCreated() {
        return mPreference.getBoolean(DB_CREATED, false);
    }
    
    public void setDbCreated() {
        mPreference.edit().putBoolean(DB_CREATED, true).commit();
    }
    
    public TraficDB getTraficDb() {
        return mDb;
    }
    
    public void recordQuestionIdx(String tag, int idx) {
        mPreference.edit().putInt(tag, idx).commit();
    }
    
    public int getQuestionIdx(String tag) {
        return mPreference.getInt(tag, 0);
    }
    
    
    
}
