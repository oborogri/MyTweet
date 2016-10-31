package app.mytweet.activities;

import app.mytweet.R;
import app.mytweet.app.MyTweetApp;
import app.mytweet.helpers.ContactHelper;
import app.mytweet.models.Portfolio;
import app.mytweet.models.Tweet;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static app.mytweet.helpers.ContactHelper.sendEmail;
import static app.mytweet.helpers.IntentHelper.navigateUp;
import static app.mytweet.helpers.IntentHelper.selectContact;


public class TweetFragment extends Fragment implements TextWatcher,
        OnClickListener
{
    public static   final String  EXTRA_TWEET_ID = "mytweet.TWEET_ID";

    private static  final int     REQUEST_CONTACT = 1;

    private EditText textTweet;
    private TextView count;
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

        Long tweetId = (Long)getArguments().getSerializable(EXTRA_TWEET_ID);

        app = MyTweetApp.getApp();
        portfolio = app.portfolio;
        tweet = portfolio.getTweet(tweetId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        super.onCreateView(inflater,  parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_mytweet, parent, false);

        MyTweetPagerActivity mytweetActivity = (MyTweetPagerActivity)getActivity();
        mytweetActivity.actionBar.setDisplayHomeAsUpEnabled(true);

        addListeners(v);
        updateControls(tweet);

        return v;
    }

    private void addListeners(View v)
    {
        textTweet    = (EditText) v.findViewById(R.id.text_tweet);
        sendTweet    = (Button) v.findViewById(R.id.send_tweet);
        contactTweet = (Button) v.findViewById(R.id.contact_tweet);
        emailTweet   = (Button) v.findViewById(R.id.email_tweet);
        dateView     = (TextView) v.findViewById(R.id.tweet_date);
        count        = (TextView) v.findViewById(R.id.chars_count);

        textTweet   .addTextChangedListener(this);
        sendTweet   .setOnClickListener(this);
        contactTweet.setOnClickListener(this);
        emailTweet  .setOnClickListener(this);

        dateView .setText(tweet.getDateString());

        if(tweet.text != null) {
            textTweet.setEnabled(false);
            sendTweet.setEnabled(false);
        }

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
            case android.R.id.home:
                if(tweet.text.matches("")) {
                    portfolio.deleteTweet(tweet);
                }
                navigateUp(getActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable c) {
        Log.i(this.getClass().getSimpleName(), "Message: " + c.toString());
        tweet.text = c.toString();
        count.setText(String.valueOf(tweet.max_count - c.length()));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.contact_tweet:
                //selectContact(getActivity(), REQUEST_CONTACT);
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
                break;

            case R.id.email_tweet:
                if(tweet.text.matches("")) {
                    portfolio.deleteTweet(tweet);
                    Toast toast = Toast.makeText(getActivity(), "Message body can't be blanc!", Toast.LENGTH_SHORT);
                    toast.show();
                    navigateUp(getActivity());
                    break;
                }
                if(emailAddress == null) emailAddress = ""; // guard against null pointer
                sendEmail(getActivity(), emailAddress,
                        getString(R.string.email_tweet_subject), tweet.getText());
                break;

            case R.id.send_tweet:
                if (tweet.text.matches("")) {
                    portfolio.deleteTweet(tweet);
                    Toast toast = Toast.makeText(getActivity(), "Message body can't be blanc!", Toast.LENGTH_SHORT);
                    toast.show();
                    navigateUp(getActivity());
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Message sent!", Toast.LENGTH_SHORT);
                    toast.show();
                    navigateUp(getActivity());
                    break;
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CONTACT:
                checkContactsReadPermission();
                String name = ContactHelper.getContact(getActivity(), data);
                emailAddress = ContactHelper.getEmail(getActivity(), data);
                contactTweet.setText(name + " : " + emailAddress);
                tweet.receiver = name;
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted. Do the
                    // contacts-related task you need to do.
                }
                else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void checkContactsReadPermission() {
        // Here, this is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we checkContactsReadPermissione show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
            else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_CONTACT);
                // REQUEST_CONTACT is an app-defined int constant.
                // The callback method, onRequestPermissionsResult, gets the result of the request.
            }
        }
    }

}