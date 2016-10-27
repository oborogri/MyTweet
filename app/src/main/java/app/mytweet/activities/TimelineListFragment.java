package app.mytweet.activities;

import java.util.ArrayList;

import app.mytweet.app.MyTweetApp;
import app.mytweet.helpers.IntentHelper;
import app.mytweet.R;
import app.mytweet.models.Portfolio;
import app.mytweet.models.Tweet;

import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.view.ActionMode;
import android.widget.Toast;

public class TimelineListFragment extends ListFragment implements OnItemClickListener, AbsListView.MultiChoiceModeListener
{
    private ArrayList<Tweet> tweets;
    private Portfolio portfolio;
    private MyTweetAdapter adapter;
    private ListView listView;
    MyTweetApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.app_name);

        app = MyTweetApp.getApp();
        portfolio = app.portfolio;
        tweets = portfolio.tweets;

        adapter = new MyTweetAdapter(getActivity(), tweets);
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        listView = (ListView) v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Tweet t = ((MyTweetAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), MyTweetPagerActivity.class);
        i.putExtra(TweetFragment.EXTRA_TWEET_ID, t.id);
        startActivityForResult(i, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyTweetAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_timeline, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_tweet:
                Tweet tweet = new Tweet();
                portfolio.addTweet(tweet);
                portfolio.saveTweets();
                Intent i = new Intent(getActivity(), MyTweetPagerActivity.class);
                i.putExtra(TweetFragment.EXTRA_TWEET_ID, tweet.id);
                startActivityForResult(i, 0);
                return true;

            case R.id.menu_clear:
                tweets = portfolio.tweets;
                if(tweets.size() == 0 ) {
                    Toast toast = Toast.makeText(getActivity(), "There are no tweets to delete!", Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                }
                portfolio.deleteTweetsAll(tweets);
                Intent it = new Intent(getActivity(), TimelineListActivity.class);
                startActivity(it);
                return true;

            case R.id.menu_logout:
                Intent in = new Intent(getActivity(), WelcomeActivity.class);
                startActivityForResult(in, 0);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tweet t = adapter.getItem(position);
        IntentHelper.startActivityWithData(getActivity(), MyTweetPagerActivity.class, "TWEET_ID", t.id);
    }

    class MyTweetAdapter extends ArrayAdapter<Tweet>
    {
        private Context context;

        public MyTweetAdapter(Context context, ArrayList<Tweet> tweets) {
            super(context, 0, tweets);
            this.context = context;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.timelinelist_item, null);
            }
            Tweet t = getItem(position);

            TextView textTweet = (TextView) convertView.findViewById(R.id.timelinelist_item_text);
            textTweet.setText(t.text);

            TextView dateTextView = (TextView) convertView.findViewById(R.id.timelinelist_item_dateTextView);
            dateTextView.setText(t.getDateString());

            return convertView;
        }
    }
    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.tweet_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu)
    {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_tweet:
                deleteTweet(actionMode);
                return true;
            default:
                return false;
        }

    }

    private void deleteTweet(ActionMode actionMode)
    {
        for (int i = adapter.getCount() - 1; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                portfolio.deleteTweet(adapter.getItem(i));
            }
        }
        actionMode.finish();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
    {
    }

  /* ************ MultiChoiceModeListener methods (end) *********** */

}