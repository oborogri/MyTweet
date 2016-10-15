package app.mytweet.app;

import app.mytweet.models.Portfolio;
import app.mytweet.models.PortfolioSerializer;

import android.app.Application;
import static app.mytweet.helpers.LogHelpers.info;

public class MyTweetApp extends Application
{
    private static final String FILENAME = "portfolio.json";
    public Portfolio portfolio;
    protected static MyTweetApp app;

    @Override
    public void onCreate()
    {
        super.onCreate();
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(serializer);

        app=this;

        info(this, "MyTweet app launched");
    }
    public static MyTweetApp getApp() { return app; }

}
