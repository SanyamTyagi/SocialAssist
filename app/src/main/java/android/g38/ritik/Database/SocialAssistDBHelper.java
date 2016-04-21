package android.g38.ritik.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.g38.ritik.Database.DataBaseContracter;
import android.g38.socialassist.R;
import android.util.Log;


import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ritik on 3/17/2016.
 */
public class SocialAssistDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "socialAssist";
    public static final int DATABASE_VERSION = 1;
    public SocialAssistDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_EVENENTRY = "CREATE TABLE " + DataBaseContracter.EvenEntry.TABLE_NAME + " ( " + DataBaseContracter.EvenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + DataBaseContracter.EvenEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                DataBaseContracter.EvenEntry.COLUMN_ACTION + " TEXT NOT NULL , " + DataBaseContracter.EvenEntry.COLUMN_TRIGGER + " TEXT NOT NULL, " +
                DataBaseContracter.EvenEntry.COLUMN_SHORTDES + " TEXT NOT NULL, " + DataBaseContracter.EvenEntry.COLUMN_EVENT + " TEXT NOT NULL, " +
                "FOREIGN KEY ( " + DataBaseContracter.OddEntry._ID + " ) REFERENCES " + DataBaseContracter.OddEntry.TABLE_NAME + " ( " + DataBaseContracter.OddEntry._ID + " ) );";

        final String CREATE_ODDENTRY = "CREATE TABLE " + DataBaseContracter.OddEntry.TABLE_NAME + " ( " + DataBaseContracter.OddEntry._ID + "INTEGER PRIMARY KEY , " +
                DataBaseContracter.OddEntry.COLUMN_DETAIL +" TEXT NOT NULL ) ; ";

        db.execSQL(CREATE_EVENENTRY);
        db.execSQL(CREATE_ODDENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContracter.OddEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContracter.EvenEntry.TABLE_NAME);
        onCreate(db);
    }

    public static HashMap<String, ArrayList<String>> getListItems(final SQLiteDatabase db){
        final ArrayList<String> valuesTime = new ArrayList<>();
        final ArrayList<String> valuesAction = new ArrayList<>();
        final ArrayList<String> valuesTrigger = new ArrayList<>();
        final ArrayList<String> valuesShortDes = new ArrayList<>();
        final ArrayList<String> valuesEvent = new ArrayList<>();

        final ArrayList<String> valuesDetail = new ArrayList<>();

        HashMap<String, ArrayList<String>> listItemsHashMap = new HashMap<>();
        Thread tTime = new Thread() {
            @Override
            public void run() {
                Cursor cursor = db.query(DataBaseContracter.EvenEntry.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    valuesTime.add(cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_TIME)));
                }
            }
        };

        Thread tAction = new Thread(){
            @Override
            public void run() {
                Cursor cursor = db.query(DataBaseContracter.EvenEntry.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    valuesAction.add(cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_ACTION)));
                }
            }
        };

        Thread tTrigger = new Thread(){
            @Override
            public void run() {
                Cursor cursor = db.query(DataBaseContracter.EvenEntry.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    valuesTrigger.add(cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_TRIGGER)));
                }
            }
        };

        Thread tSrtDes = new Thread(){
            @Override
            public void run() {
                Cursor cursor = db.query(DataBaseContracter.EvenEntry.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    valuesShortDes.add(cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_SHORTDES)));
                }
            }
        };


        Thread tEvent = new Thread(){
            @Override
            public void run() {
                Cursor cursor = db.query(DataBaseContracter.EvenEntry.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    valuesEvent.add(cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_EVENT)));
                }
            }
        };

        Thread tDetail = new Thread(){
            @Override
            public void run() {
                Cursor cursor = db.query(DataBaseContracter.OddEntry.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    valuesDetail.add(cursor.getString(cursor.getColumnIndex(DataBaseContracter.OddEntry.COLUMN_DETAIL)));
                }
            }
        };
        tAction.start();
        tDetail.start();
        tEvent.start();
        tSrtDes.start();
        tTime.start();
        tTrigger.start();
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(tAction.isAlive() || tDetail.isAlive() || tEvent.isAlive() || tSrtDes.isAlive() || tTime.isAlive() || tTrigger.isAlive()){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_TIME, valuesTime);
        listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_ACTION, valuesAction);
        listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_TRIGGER, valuesTrigger);
        listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_SHORTDES, valuesShortDes);
        listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_EVENT, valuesEvent);

        listItemsHashMap.put(DataBaseContracter.OddEntry.COLUMN_DETAIL, valuesDetail);
        return listItemsHashMap;
    }

    public static void fakeInsert(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(DataBaseContracter.EvenEntry.COLUMN_EVENT,String.valueOf(R.drawable.ic_battery_channel));
        values.put(DataBaseContracter.EvenEntry.COLUMN_TRIGGER,String.valueOf(R.drawable.ic_facebook_channel));
        values.put(DataBaseContracter.EvenEntry.COLUMN_ACTION, String.valueOf(R.drawable.ic_twitter_channel));
        values.put(DataBaseContracter.EvenEntry.COLUMN_SHORTDES,"If new status message on facebook, twitt the same");
        values.put(DataBaseContracter.EvenEntry.COLUMN_TIME,"23:04 pm");
        db.insert(DataBaseContracter.EvenEntry.TABLE_NAME,null,values);
        values.clear();

        values.put(DataBaseContracter.OddEntry.COLUMN_DETAIL,"This is what you have posted!!!");

        db.insert(DataBaseContracter.OddEntry.TABLE_NAME, null, values);
    }
}


/*
            listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_TIME, cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_TIME)));
            listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_ACTION, cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_ACTION)));
            listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_TRIGGER, cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_TRIGGER)));
            listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_SHORTDES, cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_SHORTDES)));
            listItemsHashMap.put(DataBaseContracter.EvenEntry.COLUMN_DAY, cursor.getString(cursor.getColumnIndex(DataBaseContracter.EvenEntry.COLUMN_DAY)));
 */