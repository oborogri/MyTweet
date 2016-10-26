package app.mytweet.app;

import app.mytweet.models.Portfolio;
import app.mytweet.models.User;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import static app.mytweet.helpers.LogHelpers.info;

public class MyTweetApp extends Application
{
    public Portfolio portfolio;
    protected static MyTweetApp app;

    public List<User> users        = new ArrayList<User>();

    @Override
    public void onCreate()
    {
        super.onCreate();
        portfolio = new Portfolio();

        app=this;

        info(this, "MyTweet app launched");
    }

    public static MyTweetApp getApp() { return app; }

    public void newUser(User user)
    {
        users.add(user);
    }

    public boolean validUser (String email, String password)
    {
        for (User user : users)
        {
            if (user.email.equals(email) && user.password.equals(password))
            {
                return true;
            }
        }
        return false;
    }
}
