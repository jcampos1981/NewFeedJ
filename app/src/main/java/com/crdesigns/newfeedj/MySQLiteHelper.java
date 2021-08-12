package com.crdesigns.newfeedj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "NewsDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table
        //author, title, description,url,urlToImage,publishedAt;
        String CREATE_TABLE = "CREATE TABLE tbaFavorites ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "author TEXT, "+
                "title TEXT," +
                "description TEXT, "+
                "url TEXT," +
                "urlToImage TEXT, "+
                "publishedAt TEXT)";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE_VIDEOUSERS = "CREATE TABLE tbaVideoUsers ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "url TEXT, url_edge TEXT, watched TEXT)";
        db.execSQL(CREATE_TABLE_VIDEOUSERS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS tbaFavorites");
        // create fresh  table
        this.onCreate(db);
    }
    private static final String TABLE_FAVORITES = "tbaFavorites";
    private static final String KEY_ID = "id";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";
    private static final String KEY_URLTOIMAGE = "urlToImage";
    private static final String KEY_PUBLISHEDAT = "publishedAt";
    //private static final String[] COLUMNS = {KEY_ID,KEY_AUTHOR,KEY_TITLE,KEY_DESCRIPTION,KEY_URL,KEY_URLTOIMAGE,KEY_PUBLISHEDAT};

    private static final String TABLE_VIDEOUSERS = "tbaVideoUsers";
    private static final String KEY_ID_VU = "id";
    private static final String KEY_URL_VU = "url";
    private static final String KEY_URL_EDGE = "url_edge";
    private static final String KEY_WATCHED = "watched";

    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public void addFavorites(FavoritesClass favoritesClass){
        Log.d("addFavorites", favoritesClass.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_AUTHOR, favoritesClass.getAuthor());
        values.put(KEY_TITLE, favoritesClass.getTitle());
        values.put(KEY_DESCRIPTION, favoritesClass.getDescription());
        values.put(KEY_URL, favoritesClass.getUrl());
        values.put(KEY_URLTOIMAGE, favoritesClass.getUrlToImage());
        values.put(KEY_PUBLISHEDAT, favoritesClass.getPublishedAt());
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }
    public void addVideoUsers(VideoUsers videoUsers){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_URL_VU, videoUsers.getUrl());
        values.put(KEY_URL_EDGE, videoUsers.getUrl_edge());
        values.put(KEY_WATCHED, videoUsers.getWatched());
        db.insert(TABLE_VIDEOUSERS, null, values); // key/value -> keys = column names/ values = column values
        db.close();
    }
    public List<String> getFavorites() {
        List<String> labels = new ArrayList<String>();
        // 1. build the query
        String query = "SELECT author, title, description,url,urlToImage,publishedAt FROM " + TABLE_FAVORITES;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0) + "|" + cursor.getString(1) + "|" + cursor.getString(2)+ "|" + cursor.getString(3)+ "|" + cursor.getString(4)+ "|" + cursor.getString(5));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        return labels;
    }

    public List<String> getVideo() {
        List<String> labels = new ArrayList<String>();
        // 1. build the query
        String query = "SELECT url, url_edge FROM " + TABLE_VIDEOUSERS + " WHERE " + KEY_WATCHED +  " = '0'" ;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0) + "|" + cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        return labels;
    }

    public String updateViewed(String idUser, String url) {
        String query = "UPDATE  " + TABLE_VIDEOUSERS + " Set watched = '1' WHERE " + KEY_URL + "= '" + url + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        db.close();
        return "";
    }
}
