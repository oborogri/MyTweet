package app.mytweet.activities;

import java.util.ArrayList;
import java.util.List;

import app.mytweet.app.MyTweetApp;
import app.mytweet.helpers.IntentHelper;
import app.mytweet.R;
import app.mytweet.models.Portfolio;
import app.mytweet.models.Tweet;
import app.mytweet.settings.SettingsActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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

public class TimelineListFragment extends ListFragment implements OnItemClickListener, AbsListView.MultiChoiceModeListener,
        Callback<Tweet>
{
    private ArrayList<Tweet> tweets;
    private Portfolio portfolio;
    private MyTweetAdapter adapter;
    private ListView listView;
    MyTweetApp app;
    private static final String FILENAME = "this.portfolio";
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
                tweet.sender="homer@simpson.com";//hardcoded - to be changed to app.currentUser

                //add tweet to the server
                createTweet(tweet);

                Toast toast = Toast.makeText(getActivity(), "Create new message!", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(getActivity(), NewTweetActivity.class);
                i.putExtra(NewTweetFragment.EXTRA_TWEET_ID, tweet.id);
                this.startActivityForResult(i, 0);
                return true;

            case R.id.action_refresh:
                retrieveTweets();
                return true;

            case R.id.menu_clear:
                tweets = portfolio.tweets;
                if(tweets.size() == 0 ) {
                    Toast t = Toast.makeText(getActivity(), "There are no messages to delete!", Toast.LENGTH_SHORT);
                    t.show();
                    return true;
                }
                portfolio.deleteTweetsAll(tweets);
                Toast to = Toast.makeText(getActivity(), "All messages deleted!", Toast.LENGTH_SHORT);
                to.show();
                Intent it = new Intent(getActivity(), TimelineListActivity.class);
                startActivity(it);
                return true;

            case R.id.menu_logout:
                Intent in = new Intent(getActivity(), WelcomeActivity.class);
                //clearing all activities
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast t = Toast.makeText(getActivity(), "Logged out!", Toast.LENGTH_SHORT);
                t.show();
                startActivityForResult(in, 0);

                return true;

            case R.id.action_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tweet t = adapter.getItem(position);
        IntentHelper.startActivityWithData(getActivity(), MyTweetPagerActivity.class, "TWEET_ID", t.id);
    }

/* ****************Retrofit create tweet start ************************************************************************/

    public void createTweet(Tweet tweet) {
        Call<Tweet> call = app.tweetService.createTweet(tweet);
        call.enqueue(this);
    }

    //interface methods implementation
    @Override
    public void onResponse(Response<Tweet> response, Retrofit retrofit) {

        Tweet returnedTweet = response.body();
        if (returnedTweet != null) {
            Toast.makeText(getActivity(), "Tweet created successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "Tweet null object returned due to incorrectly configured client", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getActivity(), "Failed to create tweet due to unknown network issue", Toast.LENGTH_SHORT).show();

    }
    /****************** Retrofit create tweet end **********************************************************************************/

    /* ************ Retrofit: Delete Tweet start*************************************** */

    public void deleteTweet(Long id) {
        DeleteRemoteTweet delTweet = new DeleteRemoteTweet();
        Call<String> call = app.tweetService.deleteTweet(id);
        call.enqueue(delTweet);
    }
    class DeleteRemoteTweet implements Callback<String>
    {

        @Override
        public void onResponse(Response<String> response, Retrofit retrofit) {
            Toast.makeText(getActivity(), "Tweet deleted", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(getActivity(), "Failed to delete Tweet due to unknown network issue", Toast.LENGTH_SHORT).show();
        }
    }

    public void retrieveTweets() {
        RetrieveTweets retrieveTweets = new RetrieveTweets();
        Call<List<Tweet>> call = app.tweetService.getTweets();
        call.enqueue(retrieveTweets);
        Toast.makeText(getActivity(), "Retrieving tweet list", Toast.LENGTH_SHORT).show();
    }
/************ Retrieve tweets call back implementation*************************************************/

class RetrieveTweets implements Callback<List<Tweet>>
{
    @Override
    public void onResponse(Response<List<Tweet>> response, Retrofit retrofit) {
        List<Tweet> listTweet = response.body();
        Toast.makeText(getActivity(), "Retrieved " + listTweet.size() + " tweets", Toast.LENGTH_SHORT).show();
        portfolio.refreshTweets(listTweet);
        ((MyTweetAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getActivity(), "Failed to retrieve tweet list", Toast.LENGTH_SHORT).show();
    }
}

/******************************Adapter class*****************************************************************/

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
                Toast toast = Toast.makeText(getActivity(), "Message deleted!", Toast.LENGTH_SHORT);
                toast.show();
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
                Tweet tweet = adapter.getItem(i);
                portfolio.deleteTweet(tweet);
                deleteTweet(tweet.id);
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