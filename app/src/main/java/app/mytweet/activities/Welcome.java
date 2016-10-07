package app.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import static app.mytweet.helpers.IntentHelper.navigateUp;

import app.mytweet.R;
import app.mytweet.activities.Login;
import app.mytweet.activities.Signup;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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

    public void signupPressed (View view)
    {
        startActivity (new Intent(this, Signup.class));
    }

    public void loginPressed (View view)
    {
        startActivity (new Intent(this, Login.class));
    }
}
