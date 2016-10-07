package app.mytweet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import app.mytweet.R;
import app.mytweet.Signup;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
