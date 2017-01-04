package app.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import app.mytweet.R;
import app.mytweet.app.MyTweetApp;

import static app.mytweet.helpers.IntentHelper.navigateUp;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:  navigateUp(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signinPressed (View view)
    {
        MyTweetApp app = (MyTweetApp) getApplication();

        TextView email     = (TextView)  findViewById(R.id.loginEmail);
        TextView password  = (TextView)  findViewById(R.id.loginPassword);

       /* if (app.validUser(email.getText().toString(), password.getText().toString()))
        {*/
            Toast toast = Toast.makeText(this, "Welcome to MyTweet", Toast.LENGTH_SHORT);
            toast.show();

            startActivity (new Intent(this, TimelineListActivity.class));
        /*}/*
        else
        {*/
           /* startActivity (new Intent(this, LoginActivity.class));
            Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
            toast.show();*/
    }
}
