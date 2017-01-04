package app.mytweet.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import app.mytweet.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper
{
    static final String TAG = "DbHelper";
    static final String DATABASE_NAME = "tweets.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_TWEETS = "tableTweets";

    static final String PRIMARY_KEY = "id";
    static final String DATE = "date";
    static final String SENDER = "sender";
    static final String TEXT = "text";

    Context context;

    public DbHelper(Context context) {
        super(context, "/sdcard/" + DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE tableTweets " +
                        "(id text primary key, " +
                        "date text," +
                        "sender text," +
                        "text text)";

        db.execSQL(createTable);
        Log.d(TAG, "DbHelper.onCreated: " + createTable);
    }

    /**
     * @param tweet Reference to Tweet object to be added to database
     */
    public void addTweet(Tweet tweet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRIMARY_KEY, tweet.id);
        values.put(DATE, tweet.date);
        values.put(SENDER, tweet.sender);
        values.put(TEXT, tweet.text);

        // Insert record
        db.insert(TABLE_TWEETS, null, values);
        db.close();
    }

    /**
     * Persist a list of tweets
     *
     * @param tweets The list of Tweet object to be saved to database.
     */
    public void addTweets(List<Tweet> tweets) {
        for (Tweet tweet : tweets) {
            addTweet(tweet);
        }
    }
    public Tweet selectTweet(String tweetId) {
        Tweet tweet;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            tweet = new Tweet();

            cursor = db.rawQuery("SELECT * FROM tableTweets WHERE id = ?", new String[]{tweetId + ""});

            if (cursor.getCount() > 0) {
                int columnIndex = 0;
                cursor.moveToFirst();
                tweet.id = cursor.getString(columnIndex++);
                tweet.date = cursor.getString(columnIndex++);
                tweet.sender = cursor.getString(columnIndex++);
                tweet.text = cursor.getString(columnIndex++);
            }
        }
        finally {
            cursor.close();
        }
        return tweet;
    }

    public void deleteTweet(Tweet tweet) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete("tableTweets", "id" + "=?", new String[]{tweet.id + ""});
        }
        catch (Exception e) {
            Log.d(TAG, "delete tweet failure: " + e.getMessage());
        }
    }
    /**
     * Query database and select entire tableTweets.
     *
     * @return A list of Tweet object records
     */
    public List<Tweet> selectTweets() {
        List<Tweet> tweets = new ArrayList<Tweet>();
        String query = "SELECT * FROM " + "tableTweets";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int columnIndex = 0;
            do {
                Tweet tweet = new Tweet();
                tweet.id = cursor.getString(columnIndex++);
                tweet.date = cursor.getString(columnIndex++);
                tweet.sender = cursor.getString(columnIndex++);
                tweet.text = cursor.getString(columnIndex++);
                columnIndex = 0;

                tweets.add(tweet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tweets;
    }

    /**
     * Delete all records
     */
    public void deleteTweets() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("delete from tableTweets");
        }
        catch (Exception e) {
            Log.d(TAG, "delete tweets failure: " + e.getMessage());
        }
    }
    /**
     * Queries the database for the number of records.
     *
     * @return The number of records in the dataabase.
     */
    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long numberRecords = DatabaseUtils.queryNumEntries(db, TABLE_TWEETS);
        db.close();
        return numberRecords;
    }

    /**
     * Update an existing Tweet record.
     * All fields except record id updated.
     *
     * @param tweet The Tweet record being updated.
     */
    public void updateTweet(Tweet tweet) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(DATE, tweet.date);
            values.put(SENDER, tweet.sender);
            values.put(TEXT, tweet.text);

            db.update("tableTweets", values, "id" + "=?", new String[]{tweet.id + ""});
        }
        catch (Exception e) {
            Log.d(TAG, "update tweets failure: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TWEETS);
        Log.d(TAG, "onUpdated");
        onCreate(db);
    }
}
