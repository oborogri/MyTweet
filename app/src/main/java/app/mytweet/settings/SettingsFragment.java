package app.mytweet.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import app.mytweet.R;
import app.mytweet.activities.WelcomeActivity;
import app.mytweet.app.MyTweetApp;
import app.mytweet.models.Portfolio;
import app.mytweet.models.Tweet;

import static app.mytweet.activities.TweetFragment.EXTRA_TWEET_ID;
import static app.mytweet.helpers.IntentHelper.navigateUp;
import static app.mytweet.helpers.LogHelpers.info;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private SharedPreferences prefs;
    private Tweet tweet;
    private Portfolio portfolio;
    MyTweetApp app;


    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                navigateUp(getActivity());
                return true;

            case R.id.menu_logout:
                Intent in = new Intent(getActivity(), WelcomeActivity.class);
                //clearing all activities
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast t = Toast.makeText(getActivity(), "Logged out!", Toast.LENGTH_SHORT);
                t.show();
                startActivityForResult(in, 0);

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

        info(getActivity(), "Settings change - key : value = " + key + " : " + prefs.getString(key, ""));

    }
}