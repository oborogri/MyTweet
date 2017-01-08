package app.mytweet.app;

import app.mytweet.models.Portfolio;
import app.mytweet.models.User;
import app.mytweet.retrofit.MyTweetServiceProxy;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MyTweetApp extends Application
{
    static final String TAG = "MyTweetApp";
    public Portfolio portfolio;
    protected static MyTweetApp app;

    public List<User> users        = new ArrayList<User>();
    public List<User> currentUsers = new ArrayList<User>();

    public String service_url = "https://my-tweet20073381.herokuapp.com/"; //server on heroku
    public MyTweetServiceProxy tweetService;

    @Override
    public void onCreate()
    {
        super.onCreate();
        portfolio = new Portfolio(getApplicationContext());
        Log.d(TAG, "MyTweet app launched");
        app = this;

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(service_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        tweetService = retrofit.create(MyTweetServiceProxy.class);
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
