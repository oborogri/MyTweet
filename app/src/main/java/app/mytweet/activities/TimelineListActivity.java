package app.mytweet.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import app.mytweet.R;
import app.mytweet.app.MyTweetApp;
import app.mytweet.models.Portfolio;
import app.mytweet.models.Tweet;

public class TimelineListActivity extends AppCompatActivity {

    private ListView listView;
    private Portfolio portfolio;
    private TweetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timelinelist);

        listView = (ListView) findViewById(R.id.timelineList);

        MyTweetApp app = (MyTweetApp) getApplication();
        portfolio = app.portfolio;

        adapter = new TweetAdapter(this, portfolio.tweets);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_new_tweet:
                startActivity (new Intent(this, MyTweetActivity.class));
                return true;

            case R.id.menu_logout:
                startActivity (new Intent(this, WelcomeActivity.class));
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}

class TweetAdapter extends ArrayAdapter<Tweet>
{
    private Context context;

    public TweetAdapter(Context context, ArrayList<Tweet> tweets)
    {
        super(context, 0, tweets);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.timelinelist_item, null);
        }
        Tweet t = getItem(position);

        TextView text = (TextView) convertView.findViewById(R.id.timelinelist_item_text);
        text.setText(t.text);

        TextView dateTextView = (TextView) convertView.findViewById(R.id.timelinelist_item_dateTextView);
        dateTextView.setText(t.getDateString());

        return convertView;
    }
}
