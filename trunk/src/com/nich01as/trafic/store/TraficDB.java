/**
 * @(#)TraficDB.java, 2011-11-28. Copyright 2011 Yodao, Inc. All rights
 *                    reserved. YODAO PROPRIETARY/CONFIDENTIAL. Use is subject
 *                    to license terms.
 */
package com.nich01as.trafic.store;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.nich01as.trafic.Question;

/**
 * @author Nicholas
 */
public class TraficDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "trafic.db";

    private static final int version = 1;

    private interface TraficBaseColumns extends BaseColumns {

        public static final String TABLE_NAME = "question";

        public static final String QUESTION_ID = _ID;

        public static final String CHAPTER = "chapter";

        public static final String DESCRIPTION = "description";

        public static final String ANSWER = "answer";

        public static final String CHOIECES = "choices";

        public static final String IS_CORRECT = "is_correct";

        public static final String IMAGE_URL = "image_url";

        public static final String CREATE_TABLE = "create table " + TABLE_NAME
                + " (" + QUESTION_ID + " integer not null primary key, "
                + CHAPTER + " integer not null, " + DESCRIPTION
                + " text not null, " + CHOIECES + " text not null, " + ANSWER
                + " integer not null, " + IMAGE_URL + " text, " + IS_CORRECT
                + " boolean defalt true);";
    }

    /**
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public TraficDB(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TraficBaseColumns.CREATE_TABLE);
    }
    
    public int getTotalCount() {
        final SQLiteStatement s = getReadableDatabase().compileStatement("select count(*) from "
                + TraficBaseColumns.TABLE_NAME);
        try {
            return (int) s.simpleQueryForLong();
        } finally {
            s.close();
        }
    }
    
    public void markInCorrect(int id) throws JSONException {
        Question question = getQuestiion(id);
        updateQuestion(question, false);
    }
    
    public void markCorrect(int id) throws JSONException {
        Question question = getQuestiion(id);
        updateQuestion(question, true);
    }
    
    private void updateQuestion(Question question, boolean correct) {
        if (question == null) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(TraficBaseColumns.IS_CORRECT, correct);
        insertQuestion(question, cv);
    }
    
    public void insertQuestion(Question question) {
        insertQuestion(question, new ContentValues());
    }

    private void insertQuestion(Question question, ContentValues cv) {
        cv.put(TraficBaseColumns.QUESTION_ID, question.getIndex());
        cv.put(TraficBaseColumns.CHAPTER, question.getChapter());
        cv.put(TraficBaseColumns.DESCRIPTION, question.getDescription());
        cv.put(TraficBaseColumns.ANSWER, question.getAnswerIdx());
        cv.put(TraficBaseColumns.IMAGE_URL, question.getImageUrl());
        cv.put(TraficBaseColumns.CHOIECES,
                new JSONArray(question.getChoices()).toString());
        getWritableDatabase().replace(TraficBaseColumns.TABLE_NAME, null, cv);
    }

    public Question getQuestiion(int id) throws JSONException {
        Cursor cursor = getReadableDatabase().query(
                TraficBaseColumns.TABLE_NAME, null,
                TraficBaseColumns.QUESTION_ID + " = ?", new String[] {
                    Integer.toString(id)
                }, null, null, null);
        try {
            if (cursor.getCount() == 0) {
                return null;
            } else {
                cursor.moveToFirst();
                return getQuestion(cursor);
            }
        } finally {
            cursor.close();
        }
    }

    public List<Question> getQuestionInChapter(int chapter)
            throws JSONException {
        Cursor cursor = getReadableDatabase().query(
                TraficBaseColumns.TABLE_NAME, null,
                TraficBaseColumns.CHAPTER + " = ?", new String[] {
                    Integer.toString(chapter)
                }, null, null, TraficBaseColumns.QUESTION_ID + " asc");
        try {
            return cursor2QuestionList(cursor);
        } finally {
            cursor.close();
        }
    }
    
    public List<Question> getWrongQuestions() throws JSONException {
        Cursor cursor = getReadableDatabase().query(
                TraficBaseColumns.TABLE_NAME, null,
                TraficBaseColumns.IS_CORRECT + " != ?", new String[] {
                    "1"
                }, null, null, TraficBaseColumns.QUESTION_ID + " asc");
        try {
            return cursor2QuestionList(cursor);
        } finally {
            cursor.close();
        }
    }

    private List<Question> cursor2QuestionList(Cursor cursor)
            throws JSONException {
        List<Question> ret = new ArrayList<Question>();
        while (cursor.moveToNext()) {
            ret.add(getQuestion(cursor));
        }
        return ret;
    }

    private Question getQuestion(Cursor cursor) throws JSONException {
        CursorHelper helper = new CursorHelper(cursor);
        Question question = new Question();
        question.setIndex(helper.getInt(TraficBaseColumns.QUESTION_ID));
        question.setAnswerIdx(helper.getInt(TraficBaseColumns.ANSWER));
        question.setDescription(helper.getString(TraficBaseColumns.DESCRIPTION));
        question.setChoices(new JSONArray(helper
                .getString(TraficBaseColumns.CHOIECES)));
        question.setImageUrl(helper.getString(TraficBaseColumns.IMAGE_URL));
        return question;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
