package app.mytweet.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import app.mytweet.helpers.ContactHelper;
import app.mytweet.R;
import app.mytweet.app.MyTweetApp;
import app.mytweet.models.Portfolio;
import app.mytweet.models.Tweet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import static android.support.v7.appcompat.R.id.text;
import static app.mytweet.R.id.chars_tweet;
import static app.mytweet.R.id.tweet_date;
import static app.mytweet.helpers.ContactHelper.sendEmail;
import static app.mytweet.helpers.IntentHelper.navigateUp;
import static app.mytweet.helpers.IntentHelper.selectContact;

public class MyTweetActivity extends AppCompatActivity implements TextWatcher, OnClickListener {

    public static   final String  EXTRA_TWEET_ID = "mytweet.TWEET_ID";

    private static  final int     REQUEST_CONTACT = 1;

    private EditText textTweet;
    private Button sendTweet;
    private Button contactTweet;
    private Button emailTweet;
    private TextView dateView;

    private Tweet tweet;
    private Portfolio portfolio;

    String emailAddress = "";

    MyTweetApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tweet = new Tweet();
        Long date = tweet.date;
        dateView     = (TextView) findViewById(R.id.tweet_date);
        dateView.setText(dateString(date));

        TextView chars = (TextView) findViewById(chars_tweet);
        chars.setText("140");

        addListeners(this);
        updateControls(tweet);

        /*Long tweetId = (Long) this.getIntent().getSerializableExtra(EXTRA_TWEET_ID);

        app = MyTweetApp.getApp();
        portfolio = app.portfolio;
        tweet = portfolio.getTweet(tweetId);*/
    }

    private void addListeners(Activity a)
    {
        textTweet    = (EditText) a.findViewById(R.id.text_tweet);
        sendTweet    = (Button) a.findViewById(R.id.send_tweet);
        contactTweet = (Button) a.findViewById(R.id.contact_tweet);
        emailTweet   = (Button) a.findViewById(R.id.send_tweet);
        dateView     = (TextView) a.findViewById(R.id.tweet_date);

        textTweet   .addTextChangedListener(this);
        sendTweet   .setOnClickListener(this);
        contactTweet.setOnClickListener(this);
        emailTweet  .setOnClickListener(this);

    }

    public void updateControls(Tweet tweet)
    {
        textTweet.setText(tweet.text);
        dateView.setText(tweet.getDateString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateUp(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable t) {
        Log.i(this.getClass().getSimpleName(), "message " + t.toString());
        tweet.text = t.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_tweet:
                portfolio.addTweet(tweet);
                startActivity (new Intent(this, TimelineActivity.class));
                break;
        }
    }

    private String dateString(Long date) {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }
}



