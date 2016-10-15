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

    public Portfolio() {
        tweets = new ArrayList<Tweet>();
        this.generateTestData();
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
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

    private void generateTestData() {
        for (int i = 0; i < 10; i += 1) {
            Tweet t = new Tweet();
            t.text = "Tweet: " + i;
            tweets.add(t);
        }
    }
}