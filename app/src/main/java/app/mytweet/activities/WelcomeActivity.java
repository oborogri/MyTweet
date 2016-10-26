package app.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static app.mytweet.helpers.IntentHelper.navigateUp;

import app.mytweet.R;

public class WelcomeActivity extends AppCompatActivity {

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
        Toast toast = Toast.makeText(this, "Signup pressed", Toast.LENGTH_SHORT);
        toast.show();

        startActivity (new Intent(this, SignupActivity.class));
    }

    public void loginPressed (View view)
    {

        Toast toast = Toast.makeText(this, "Login pressed", Toast.LENGTH_SHORT);
        toast.show();

        startActivity (new Intent(this, LoginActivity.class));
    }
}
