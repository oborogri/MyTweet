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

import static app.mytweet.helpers.ContactHelper.sendEmail;
import static app.mytweet.helpers.IntentHelper.navigateUp;
import static app.mytweet.helpers.IntentHelper.selectContact;


public class TweetFragment extends Fragment implements TextWatcher,
        OnClickListener
{
    public static   final String  EXTRA_TWEET_ID = "mytweet.TWEET_ID";

    private static  final int     REQUEST_CONTACT = 1;

    private EditText textTweet;
    private Button sendTweet;
    private Button contactTweet;
    private Button emailTweet;
    private TextView dateView;

    private Tweet   tweet;
    private Portfolio   portfolio;

    String emailAddress = "";

    MyTweetApp app;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Long resId = (Long) getActivity().getIntent().getSerializableExtra(EXTRA_TWEET_ID);

        app = MyTweetApp.getApp();
        portfolio = app.portfolio;
        tweet = portfolio.getTweet(resId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        super.onCreateView(inflater,  parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_mytweet, parent, false);

        MyTweetActivity residenceActivity = (MyTweetActivity)getActivity();
        residenceActivity.actionBar.setDisplayHomeAsUpEnabled(true);

        addListeners(v);
        updateControls(tweet);

        return v;
    }

    private void addListeners(View v)
    {
        textTweet    = (EditText) v.findViewById(R.id.text_tweet);
        sendTweet    = (Button) v.findViewById(R.id.send_tweet);
        contactTweet = (Button) v.findViewById(R.id.contact_tweet);
        emailTweet   = (Button) v.findViewById(R.id.send_tweet);
        dateView     = (TextView) v.findViewById(R.id.tweet_date);

        textTweet   .addTextChangedListener(this);
        sendTweet   .setOnClickListener(this);
        contactTweet.setOnClickListener(this);
        emailTweet  .setOnClickListener(this);

    }

    public void updateControls(Tweet tweet)
    {
        textTweet.setText(tweet.text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home: navigateUp(getActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        portfolio.saveTweets();
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        else
        if (requestCode == REQUEST_CONTACT)
        {
            String name = ContactHelper.getContact(getActivity(), data);
            emailAddress = ContactHelper.getEmail(getActivity(), data);
            senderButton.setText(name + " : " + emailAddress);
            tweet.tenant = name;
        }
    }*/

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {}

    @Override
    public void afterTextChanged(Editable c)
    {
        Log.i(this.getClass().getSimpleName(), "Message: " + c.toString());
        tweet.text = c.toString();
    }

   /* @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        tweet.rented = isChecked;
    }*/

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.contact_tweet :
                selectContact(getActivity(), REQUEST_CONTACT);
                break;

            case R.id.email_tweet :
                sendEmail(getActivity(), emailAddress,
                        getString(R.string.email_tweet_subject), tweet.getDateString());
                break;

        }
    }

   /* @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        Date date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
        tweet.date = date.getTime();
        dateButton.setText(tweet.getDateString());
    }*/
}