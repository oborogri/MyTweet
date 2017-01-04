package app.mytweet.models;

/**
 * Created by Grigore on 14/10/2016.
 */

import android.util.Log;
import java.util.ArrayList;
import static app.mytweet.helpers.LogHelpers.info;
import java.util.List;

import android.content.Context;
import android.util.Log;

import app.mytweet.sqlite.DbHelper;

public class Portfolio
{
    public ArrayList<Tweet> tweets;
    public DbHelper dbHelper;

    public Portfolio(Context context)
    {
        try {
            dbHelper = new DbHelper(context);
            tweets = (ArrayList<Tweet>) dbHelper.selectTweets();
        }
        catch (Exception e) {
            info(this, "Error loading tweets: " + e.getMessage());
            tweets = new ArrayList<Tweet>();
        }
    }

    /**
     * Obtain the entire database of tweets
     *
     * @return All the tweets in the database as an ArrayList
     */
    public ArrayList<Tweet> selectTweets() {
        return (ArrayList<Tweet>) dbHelper.selectTweets();
    }

    /**
     * Add incoming tweet to both local and database storage
     *
     * @param tweet The tweet object to be added to local and database storage.
     */
    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
        dbHelper.addTweet(tweet);
    }

    /**
     * Obtain specified tweet from local list and return.
     *
     * @param id The Long id identifier of the tweet sought.
     * @return The specified tweet if it exists.
     */
    public Tweet getTweet(String id) {
        Log.i(this.getClass().getSimpleName(), "String id id: " + id);

        for (Tweet t : tweets) {
            if (id.equals(t.id)) {
                return t;
            }
        }
        info(this, "failed to find tweet. returning first element array to avoid crash");
        return null;
    }

    /**
     * Delete Tweet object from local and remote storage
     *
     * @param tweet Tweet object for deletion.
     */
    public void deleteTweet(Tweet tweet) {
        dbHelper.deleteTweet(tweet);
        tweets.remove(tweet);
    }

    public void updateTweet(Tweet tweet) {
        dbHelper.updateTweet(tweet);
        updateLocalTweets(tweet);
    }

    /**
     * Clear local and sqlite tweets and refresh with incoming list.
     * @param tweets List tweet objects
     */
    public void refreshTweets(List<Tweet> tweets)
    {
        dbHelper.deleteTweets();
        this.tweets.clear();

        dbHelper.addTweets(tweets);

        for (int i = 0; i < tweets.size(); i += 1) {
            this.tweets.add(tweets.get(i));
        }
    }

    /**
     * Search the list of tweets for argument tweet
     * If found replace it with argument tweet.
     * If not found just add the argument tweet.
     *
     * @param tweet The Tweet object with which the list of Tweets to be updated.
     */
    private void updateLocalTweets(Tweet tweet) {
        for (int i = 0; i < tweets.size(); i += 1) {
            Tweet t = tweets.get(i);
            if (t.id.equals(tweet.id)) {
                tweets.remove(i);
                tweets.add(tweet);
                return;
            }
        }
    }

    public Boolean containsTweet(Tweet tweet) {
         return tweets.contains(tweet);
}

    public void deleteTweetsAll(ArrayList<Tweet> tweets)
    {
        dbHelper.deleteTweets();
        tweets.clear();
    }

}