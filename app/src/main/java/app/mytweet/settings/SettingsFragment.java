package app.mytweet.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import app.mytweet.R;

public class SettingsFragment extends PreferenceFragment
{
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
    }
}