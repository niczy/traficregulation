/**
 * @(#)CursotHelper.java, 2011-7-7. 
 * 
 * Copyright 2011 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nich01as.trafic.store;

import android.database.Cursor;

/**
 *
 * @author Nicholas
 *
 */
public class CursorHelper {
    
    private Cursor mCursor = null;
    
    public CursorHelper(Cursor cursor) {
        this.mCursor = cursor;
    }
    
    public String getString(String columnName) {
        return mCursor.getString(mCursor.getColumnIndexOrThrow(columnName));
    }
    
    public int getInt(String columnName) {
        return mCursor.getInt(mCursor.getColumnIndexOrThrow(columnName));
    }
    
    public long getLong(String columnName) {
        return mCursor.getLong(mCursor.getColumnIndexOrThrow(columnName));
    }
    
    public boolean getBoolean(String columnName) {
        return mCursor.getInt(mCursor.getColumnIndexOrThrow(columnName)) != 0;
    }
    
    public boolean moveToNext() {
        return mCursor.moveToNext();
    }
}
