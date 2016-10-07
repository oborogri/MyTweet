package app.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import app.mytweet.R;

import static app.mytweet.helpers.IntentHelper.navigateUp;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        startActivity (new Intent(this, Timeline.class));
    }
}
