package app.mytweet.tweets;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import app.mytweet.models.Tweet;

/**
 * Created by Grigore on 07/10/2016.
 */

public class MyTweetApp extends Application {

    public List<Tweet> tweets    = new ArrayList<Tweet>();
}
