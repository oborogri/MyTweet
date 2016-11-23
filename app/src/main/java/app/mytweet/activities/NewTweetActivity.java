package app.mytweet.activities;

import app.mytweet.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class NewTweetActivity extends AppCompatActivity
{

    ActionBar actionBar;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null)
        {
            fragment = new NewTweetFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }
}