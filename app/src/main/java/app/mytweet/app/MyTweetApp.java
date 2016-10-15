package app.mytweet.app;

import app.mytweet.models.Portfolio;

import android.app.Application;
import static app.mytweet.helpers.LogHelpers.info;

public class MyTweetApp extends Application
{
    public Portfolio portfolio;
    protected static MyTweetApp app;

    @Override
    public void onCreate()
    {
        super.onCreate();
        portfolio = new Portfolio();

        //app=this;

        info(this, "MyTweet app launched");
    }
   // public static MyTweetApp getApp() { return app; }

}
