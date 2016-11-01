package app.mytweet.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import app.mytweet.R;
import app.mytweet.app.MyTweetApp;
import app.mytweet.models.User;

import static app.mytweet.helpers.IntentHelper.navigateUp;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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

    public void signupPressed(View view) {
        TextView firstName = (TextView) findViewById(R.id.firstName);
        TextView lastName = (TextView) findViewById(R.id.lastName);
        TextView tvemail = (TextView) findViewById(R.id.Email);
        TextView password = (TextView) findViewById(R.id.Password);

            if(isValidEmaillId(tvemail.getText().toString().trim())) {

                User user = new User(firstName.getText().toString(), lastName.getText().toString(), tvemail.getText().toString(), password.getText().toString());

                MyTweetApp app = (MyTweetApp) getApplication();
                app.newUser(user);

                Toast toast = Toast.makeText(this, "Registering new user", Toast.LENGTH_SHORT);
                toast.show();

                startActivity(new Intent(this, LoginActivity.class));

            } else{

           Toast.makeText(getApplicationContext(), "InValid Email Address.", Toast.LENGTH_SHORT).show();
            }
        }

    /**
     * Helper method to check valid email pattern
     * @param email
     * @return
     */
        private boolean isValidEmaillId(String email){

            return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
        }
}