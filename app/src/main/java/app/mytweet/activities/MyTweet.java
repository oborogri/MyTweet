package app.mytweet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import app.mytweet.R;
import app.mytweet.models.Tweet;

import static app.mytweet.R.id.tweet_date;
import static app.mytweet.helpers.IntentHelper.navigateUp;

public class MyTweet extends AppCompatActivity implements TextWatcher {

    private Tweet tweet;
    private TextView dateView;
    private Long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView dateView = (TextView) findViewById(tweet_date);
        date = new Date().getTime();
        dateView.setText(dateString(date));
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
    public void afterTextChanged(Editable editable) {
        //setText(editable.toString());
    }

    private String dateString(Long date) {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }
}



