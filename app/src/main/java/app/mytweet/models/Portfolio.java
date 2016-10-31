package app.mytweet.models;

/**
 * Created by Grigore on 14/10/2016.
 */

import android.util.Log;
import java.util.ArrayList;
import static app.mytweet.helpers.LogHelpers.info;

public class Portfolio
{
    public ArrayList<Tweet> tweets;
    private PortfolioSerializer   serializer;

    public Portfolio() {
       tweets = new ArrayList<Tweet>();
       //this.generateTestData();
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public Boolean containsTweet(Tweet tweet) {
         return tweets.contains(tweet);
}


    public Tweet getTweet(Long id) {
        Log.i(this.getClass().getSimpleName(), "Long parameter id: " + id);

        for (Tweet t : tweets) {
            if (id.equals(t.id)) {
                return t;
            }
        }
        return null;
    }

    public boolean saveTweets()
    {
        try
        {
            serializer.saveTweets(tweets);
            info(this, "Tweets saved to file");
            return true;
        }
        catch (Exception e)
        {
            info(this, "Error saving tweets: " + e.getMessage());
            return false;
        }
    }

    public void deleteTweet(Tweet tweet)
    {
        tweets.remove(tweet);
        saveTweets();
    }

    public void deleteTweetsAll(ArrayList<Tweet> tweets)
    {
        tweets.clear();
    }

    /*private void generateTestData() {
        for (int i = 0; i < 10; i += 1) {
            Tweet t = new Tweet();
            t.text = "Tweet: " + i;
            tweets.add(t);
        }
    }*/
}