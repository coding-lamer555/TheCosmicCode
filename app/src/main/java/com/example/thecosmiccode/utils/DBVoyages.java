package com.example.thecosmiccode.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thecosmiccode.model.Voyage;

import java.util.ArrayList;

public class DBVoyages {
    private static final String DATABASE_NAME = "voyages.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "table_users";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_VOYAGE_NAME = "voyage_name";
    private static final String COLUMN_OBJECT_NAMES = "object_names";
    private static final String COLUMN_OBJECT_WEIGHTS = "object_weights";
    private static final String COLUMN_OBJECT_COSTS = "object_costs";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_USER_NAME = 1;
    private static final int NUM_COLUMN_VOYAGE_NAME = 2;
    private static final int NUM_COLUMN_OBJECT_NAMES = 3;
    private static final int NUM_COLUMN_OBJECT_WEIGHTS = 4;
    private static final int NUM_COLUMN_OBJECT_COSTS = 5;

    private SQLiteDatabase database;

    public DBVoyages(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public long insert(Voyage voyage) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, voyage.getUser());
        cv.put(COLUMN_VOYAGE_NAME, voyage.getName());
        ObjectsInfoGetter objectsInfoGetter = new ObjectsInfoGetter(voyage);
        cv.put(COLUMN_OBJECT_NAMES, objectsInfoGetter.getObjectNamesString());
        cv.put(COLUMN_OBJECT_WEIGHTS, objectsInfoGetter.getObjectWeightsString());
        cv.put(COLUMN_OBJECT_COSTS, objectsInfoGetter.getObjectCostsString());
        return database.insert(TABLE_NAME, null, cv);
    }

    public int update(Voyage md) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, md.getName());
        cv.put(COLUMN_VOYAGE_NAME, md.getUser());
        ObjectsInfoGetter objectsInfoGetter = new ObjectsInfoGetter(md);
        cv.put(COLUMN_OBJECT_NAMES, objectsInfoGetter.getObjectNamesString());
        cv.put(COLUMN_OBJECT_WEIGHTS, objectsInfoGetter.getObjectWeightsString());
        cv.put(COLUMN_OBJECT_COSTS, objectsInfoGetter.getObjectCostsString());
        return database.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(md.getId())});
    }

    public void delete(long id) {
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        database.delete(TABLE_NAME, null, null);
    }

    public Voyage select(long id) {
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        Voyage voyage = null;
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            String user = cursor.getString(NUM_COLUMN_USER_NAME);
            String name = cursor.getString(NUM_COLUMN_VOYAGE_NAME);
            String objectNames = cursor.getString(NUM_COLUMN_OBJECT_NAMES);
            String objectWeights = cursor.getString(NUM_COLUMN_OBJECT_WEIGHTS);
            String objectCosts = cursor.getString(NUM_COLUMN_OBJECT_COSTS);
            voyage = new Voyage(id, name, user, new ObjectsInfoSetter(objectNames, objectWeights, objectCosts).getObjectsList());
        }
        cursor.close();

        return voyage;
    }

    public ArrayList<Voyage> selectAllByUserName(String userName) {
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_USER_NAME + " = ?", new String[]{userName}, null, null, null);

        ArrayList<Voyage> voyages = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                long id = cursor.getLong(NUM_COLUMN_ID);
                String user = cursor.getString(NUM_COLUMN_USER_NAME);
                String name = cursor.getString(NUM_COLUMN_VOYAGE_NAME);
                String objectNames = cursor.getString(NUM_COLUMN_OBJECT_NAMES);
                String objectWeights = cursor.getString(NUM_COLUMN_OBJECT_WEIGHTS);
                String objectCosts = cursor.getString(NUM_COLUMN_OBJECT_COSTS);
                voyages.add(new Voyage(id, name, user, new ObjectsInfoSetter(objectNames, objectWeights, objectCosts).getObjectsList()));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return voyages;
    }

    public ArrayList<Voyage> selectAll() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Voyage> voyages = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                long id = cursor.getLong(NUM_COLUMN_ID);
                String user = cursor.getString(NUM_COLUMN_USER_NAME);
                String name = cursor.getString(NUM_COLUMN_VOYAGE_NAME);
                String objectNames = cursor.getString(NUM_COLUMN_OBJECT_NAMES);
                String objectWeights = cursor.getString(NUM_COLUMN_OBJECT_WEIGHTS);
                String objectCosts = cursor.getString(NUM_COLUMN_OBJECT_COSTS);
                voyages.add(new Voyage(id, name, user, new ObjectsInfoSetter(objectNames, objectWeights, objectCosts).getObjectsList()));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return voyages;
    }

    public static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_VOYAGE_NAME + " TEXT, " +
                    COLUMN_OBJECT_NAMES + " TEXT, " +
                    COLUMN_OBJECT_WEIGHTS + " TEXT, " +
                    COLUMN_OBJECT_COSTS + " TEXT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
