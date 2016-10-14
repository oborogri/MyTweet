package app.mytweet.models;

/**
 * Created by Grigore on 14/10/2016.
 */

import android.util.Log;

import java.util.ArrayList;

import static app.mytweet.helpers.LogHelpers.info;

public class Portfolio
{
    private PortfolioSerializer   serializer;
    public ArrayList<Tweet> tweets;

    public Portfolio(PortfolioSerializer serializer)
    {
        this.serializer = serializer;
        try
        {
            tweets = serializer.loadTweets();
        }
        catch (Exception e)
        {
            info(this, "Error loading Tweets: " + e.getMessage());
            tweets = new ArrayList<Tweet>();
        }
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

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public Tweet getTweet(Long id)
    {
        Log.i(this.getClass().getSimpleName(), "Long parameter id: "+ id);

        for (Tweet res : tweets)
        {
            if(id.equals(res.id))
            {
                return res;
            }
        }
        info(this, "failed to find Tweet. returning first element array to avoid crash");
        return null;
    }

    public void deleteTweet(Tweet Tweet)
    {
        tweets.remove(Tweet);
        saveTweets();
    }

}